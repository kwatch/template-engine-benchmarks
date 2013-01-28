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

    protected void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }

    protected void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        // do nothing
    }
    
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        // do nothing
        return null;
    }


    protected void shutdown() {
    }
    
    private static enum IO {
        os("=============== Benchmark output to OutputStream =============="), // output to OutputStream
        w("=============== Benchmark output to Writer =============="), // output to Writer
        s("=============== Benchmark Return a String =============="); // return a String
        private String msg;
        private IO(String s) {msg = s;}
    }

    @Override
    public void run() {
        int wtimes = Integer.parseInt(System.getProperty("bench.wtimes", "100"));
        int ntimes = Integer.parseInt(System.getProperty("bench.ntimes", "100"));
        String s = System.getProperty("bench.io", "s");
        IO io = IO.valueOf(s);
        try {
            List<Stock> items = Stock.dummyItems();

            StringWriter w0 = new StringWriter();
            StringWriter w1 = new StringWriter();

            ByteArrayOutputStream o0 = new ByteArrayOutputStream(1024 * 10);
            ByteArrayOutputStream o1 = new ByteArrayOutputStream(1024 * 10);


            /// warm up
            switch (io) {
                case os: execute(o0, o0, wtimes, items); break;
                case w: execute(w0, w0, wtimes, items); break;
                default: execute(1, items);
            }
            
            String output = null;

            /// render N times
            long start_t = System.currentTimeMillis();

            switch (io) {
                case os: execute(o0, o1, ntimes, items); break;
                case w: execute(w0, w1, ntimes, items); break;
                default: output = execute(ntimes, items);
            }
            long end_t = System.currentTimeMillis();
            o0.close();
            o1.close();
            w0.close();
            w1.close();

            /// report result
            switch (io) {
                case os: output = o1.toString("utf-8"); break;
                case w: output = w1.toString();
            }
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
