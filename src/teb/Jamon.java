/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import teb.model.Stock;
import teb.util.DoNothingWriter;

import java.io.*;
import java.util.List;


public class Jamon extends _BenchBase {

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            /// create context data
            if (ntimes == 0) {
                new jamon.stocks().render(w1, items);
                w1.close();
            }
            else new jamon.stocks().render(w0, items);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new OutputStreamWriter(o0);
        Writer w1 = new OutputStreamWriter(o1);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            /// create context data
            if (ntimes == 0) {
                new jamon.stocks().render(w1, items);
                w1.close();
            }
            else new jamon.stocks().render(w0, items);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new StringWriter(1024 * 10);
        Writer w1 = new StringWriter(1024 * 10);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            /// create context data
            if (ntimes == 0) {
                new jamon.stocks().render(w1, items);
                w1.close();
            } else {
                new jamon.stocks().render(w0, items);
            }
        }
            
        return w1.toString();
    }

    public static void main(String[] args) {
        new Jamon().run();
    }

}
