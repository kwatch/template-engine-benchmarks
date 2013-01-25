/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.List;


public class StocksJamonBench extends Bench {

    @Override
    public void execute(boolean warmUp, Writer writer, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            /// create context data
            new jamon.stocks().render(writer, items);
        }
    }

    public static void main(String[] args) {
        new StocksJamonBench().run();
    }

}
