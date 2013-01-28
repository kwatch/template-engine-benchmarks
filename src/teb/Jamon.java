/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import teb.model.Stock;

import java.io.*;
import java.util.List;


public class Jamon extends _BenchBase {

    @Override
    protected boolean useStream() {
        return true;
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            /// create context data
            if (!warmUp && ntimes == 0) new jamon.stocks().render(w1, items);
            else new jamon.stocks().render(w0, items);
        }
    }

    @Override
    public void execute(boolean warmUp, OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new BufferedWriter(new OutputStreamWriter(o0));
        Writer w1 = new BufferedWriter(new OutputStreamWriter(o1));
        while (--ntimes >= 0) {
            /// create context data
            if (!warmUp && ntimes == 0) new jamon.stocks().render(w1, items);
            else new jamon.stocks().render(w0, items);
        }
    }

    public static void main(String[] args) {
        new Jamon().run();
    }

}
