/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import teb.model.Stock;

import java.io.*;
import java.util.List;

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
    
    private static enum OutputMode {
        os, // output to OutputStream
        w, // output to Writer
        s; // return a String
    }
    
    static final ThreadLocal<Boolean> bufferMode = new ThreadLocal<Boolean>();

    @Override
    public void run() {
        int wtimes = Integer.parseInt(System.getProperty("wtimes", "100"));
        int ntimes = Integer.parseInt(System.getProperty("ntimes", "100000000"));
        boolean useBuffer = Boolean.parseBoolean(System.getProperty("buf", "false"));
        bufferMode.set(useBuffer);
        String s = System.getProperty("out", "os");
        OutputMode outMode = OutputMode.valueOf(s);
        try {
            List<Stock> items = Stock.dummyItems();

            Writer w = new StringWriter(1024 * 10);
            Writer w0 = new StringWriter(1024 * 10);
            Writer w1 = new StringWriter(1024 * 10);
            Writer w2 = w;
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 10);
            OutputStream o0 = new ByteArrayOutputStream(1024 * 10);
            OutputStream o1 = new ByteArrayOutputStream(1024 * 10);
            OutputStream o2 = baos;
            
            if (useBuffer) {
                w0 = new BufferedWriter(w0);
                w1 = new BufferedWriter(w1);
                w2 = new BufferedWriter(w2);

                o0 = new BufferedOutputStream(o0);
                o1 = new BufferedOutputStream(o1);
                o2 = new BufferedOutputStream(o2);
            }

            /// warm up
            switch (outMode) {
                case os: execute(o0, o1, wtimes, items); break;
                case w: execute(w0, w1, wtimes, items); break;
                default: execute(1, items);
            }
            
            String output = null;

            /// render N times
            long start_t = System.currentTimeMillis();

            switch (outMode) {
                case os: execute(o0, o2, ntimes, items); break;
                case w: execute(w0, w2, ntimes, items); break;
                default: output = execute(ntimes, items);
            }
            long end_t = System.currentTimeMillis();
            /// report result
            switch (outMode) {
                case os: output = baos.toString("utf-8"); break;
                case w: output = w.toString();
            }
            System.err.println("ntimes: " + ntimes + ", real time: " + (end_t - start_t) + "(msec)");
            System.out.print(output);

            o0.close();
            o1.close();
            w0.close();
            w1.close();

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
