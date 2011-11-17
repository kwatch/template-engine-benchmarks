/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.StringWriter;
import java.io.IOException;
import java.util.List;
import cn.bran.japid.template.JapidRenderer;
import cn.bran.japid.compiler.OpMode;


public class StocksJapidBench extends Bench {

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        JapidRenderer.init(OpMode.prod, "templates", 10000);
        while (--ntimes >= 0) {
            /// create context data
            output = JapidRenderer.renderWith("stocks.html", items);
        }
        return output;
    }

    public static void main(String[] args) {
        new StocksJapidBench().run();
    }

}
