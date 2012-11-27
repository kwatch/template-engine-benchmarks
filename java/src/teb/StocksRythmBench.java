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

    RythmEngine engine;
    
    public StocksRythmBench() {
        Properties p = new Properties();
        p.put("rythm.cache.enabled", false);
        //p.put("rythm.mode", "dev");
        engine = new RythmEngine(p);
    }
    
    protected void shutdown() {
        engine.shutdown();
    }

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        //engine.mode = Rythm.Mode.dev;
        //System.err.println(engine.tmpDir);

        while (--ntimes >= 0) {
            output = engine.render("templates/rythm/stocks.rythm.html", items);
        }
        
        return output;
    }

    public static void main(String[] args) {
        new StocksRythmBench().run();
    }

}
