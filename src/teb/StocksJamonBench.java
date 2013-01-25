/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.List;


public class StocksJamonBench extends Bench {

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            /// create context data
            if (!warmUp && ntimes == 0) new jamon.stocks().render(w1, items);
            else new jamon.stocks().render(w0, items);
        }
    }

    public static void main(String[] args) {
        new StocksJamonBench().run();
    }

}
