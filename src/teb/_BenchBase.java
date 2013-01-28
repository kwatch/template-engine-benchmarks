/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import teb.model.Stock;

import java.io.*;
import java.util.List;
import java.util.Properties;

abstract public class _BenchBase implements Runnable {

    Properties _props;

    Properties getProps() {
        try {
            if (_props == null) {
                _props = new Properties();
                _props.load(new FileInputStream(new File("bench.properties")));
            }
            return _props;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    String getProp(String key) {
        return getProps().getProperty(key);
    }

    protected void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }

    protected void execute(boolean warmUp, OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }

    protected boolean useStream() {
        return false;
    }

    protected void shutdown() {
    }

    @Override
    public void run() {
        try {
            int ntimes = Integer.parseInt(getProp("bench.ntimes"));
            List<Stock> items = Stock.dummyItems();

            StringWriter w0 = new StringWriter();
            StringWriter w1 = new StringWriter();

            ByteArrayOutputStream o0 = new ByteArrayOutputStream(1024 * 10);
            ByteArrayOutputStream o1 = new ByteArrayOutputStream(1024 * 10);


            /// warm up
            if (useStream()) {
                execute(false, o0, o1, 100, items);
            } else {
                execute(true, w0, w1, 100, items);
            }

            /// render N times
            long start_t = System.currentTimeMillis();

            if (useStream()) {
                execute(false, o0, o1, ntimes, items);
            } else {
                execute(false, w0, w1, ntimes, items);
            }
            long end_t = System.currentTimeMillis();

            /// report result
            String output = useStream() ? o1.toString("utf-8") : w1.toString();
            System.err.println("ntimes: " + ntimes + ", real time: " + (end_t - start_t) + "(msec)");
            System.out.print(output);

            shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Velocity bench = new Velocity();
        bench.run();
    }

}
