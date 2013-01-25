/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

abstract public class Bench implements Runnable {

    Properties _props;

    Properties getProps() {
        try {
            if (_props == null) {
                _props = new Properties();
                _props.load(new FileInputStream(new File("bench.properties")));
            }
            return _props;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    String getProp(String key) {
        return getProps().getProperty(key);
    }

    protected void execute(boolean warmUp, Writer w, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }
    protected void execute(boolean warmUp, OutputStream out, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }
    protected boolean useStream() {
        return false;
    }
    
    protected void shutdown() {}

    @Override
    public void run() {
        try {
            int ntimes = Integer.parseInt(getProp("bench.ntimes"));
            List<Stock> items = Stock.dummyItems();
            
            UnsafeStringWriter w = new UnsafeStringWriter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            /// warm up
            execute(true, w, 100, items);
            
            /// render N times
            long start_t = System.currentTimeMillis();
            
            if (useStream()) {
                execute(false, out, ntimes, items);
            } else {
                execute(false, w, ntimes, items);
            }
            long end_t = System.currentTimeMillis();

            /// report result
            String output = useStream() ? w.toString() : out.toString("utf-8");
            System.err.println("ntimes: " + ntimes + ", real time: " + (end_t - start_t) + "(msec)");
            System.out.print(output);
            
            shutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        StocksVelocityBench bench = new StocksVelocityBench();
        bench.run();
    }

}
