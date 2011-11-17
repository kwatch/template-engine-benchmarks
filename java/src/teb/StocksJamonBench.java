/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.StringWriter;
import java.io.IOException;
import java.util.List;


public class StocksJamonBench extends Bench {

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
            /// create context data
            StringWriter writer = new StringWriter(1024);
            new jamon.stocks().render(writer, items);
            output = writer.toString();
        }
        return output;
    }

    public static void main(String[] args) {
        new StocksJamonBench().run();
    }

}
