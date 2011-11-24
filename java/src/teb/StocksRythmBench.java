/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import com.greenlaw110.rythm.*;
import com.greenlaw110.rythm.compiler.*;
import com.greenlaw110.rythm.template.*;


public class StocksRythmBench extends Bench {

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        TemplateProcessor tp = new TemplateProcessor(new File("src"), new File("templates"));
        while (--ntimes >= 0) {
            /// create context data
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("items", items);
            
            ITemplate t = tp.process("stocks.rythm.html");
            t.setRenderArgs(args);
            output = t.render();
        }
        return output;
    }

    public static void main(String[] args) {
        new StocksRythmBench().run();
    }

}
