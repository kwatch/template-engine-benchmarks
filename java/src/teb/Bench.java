/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.StringWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


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

    abstract public String execute(int ntimes, List<Stock> items) throws Exception;

    @Override
    public void run() {
        try {
            int ntimes = Integer.parseInt(getProp("bench.ntimes"));
            List<Stock> items = Stock.dummyItems();
            /// warm up
            execute(10, items);
            /// render N times
            long start_t = System.currentTimeMillis();
            String output = execute(ntimes, items);
            long end_t = System.currentTimeMillis();
            /// report result
            System.err.println("ntimes: " + ntimes + ", real time: " + (end_t - start_t) + "(msec)");
            System.out.print(output);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StocksVelocityBench bench = new StocksVelocityBench();
        bench.run();
    }

}
