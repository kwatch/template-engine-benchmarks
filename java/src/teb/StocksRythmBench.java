/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import com.greenlaw110.rythm.*;
import com.greenlaw110.rythm.template.*;
import com.greenlaw110.rythm.internal.compiler.*;


public class StocksRythmBench extends Bench {
    RythmEngine engine = new RythmEngine();

    public StocksRythmBench() {
        engine.mode = Rythm.Mode.prod;
    }

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;

        while (--ntimes >= 0) {
            /// create context data
            //Map<String, Object> args = new HashMap<String, Object>();
            //args.put("items", items);
            
            //ITemplate t = tp.process("rythm/stocks.rythm.html");
            //t.setRenderArgs(items);
            output = engine.render("templates/rythm/stocks.rythm.html", items);
            
            //ITemplate tmpl = tmpl();
            //tmpl.setRenderArgs(items);
            //output = tmpl.render();

        }
        return output;
    }

    public static void main(String[] args) {
        new StocksRythmBench().run();
    }

}
