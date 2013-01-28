/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import com.greenlaw110.rythm.*;
import teb.model.Stock;


public class Rythm extends _BenchBase {

    RythmEngine engine;
    private String template = "templates/stocks.rythm.html";
    
    public Rythm() {
        Properties p = new Properties();
        p.put("rythm.cache.enabled", false);
        p.put("rythm.logger.disabled", true);
        //p.put("rythm.tmpDir", "c:\\tmp");
        //p.put("rythm.mode", "dev");
        engine = new RythmEngine(p);
    }
    
    protected void shutdown() {
        //engine.shutdown();
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        String output;
        String tmpl = template;
        while (--ntimes >= 0) {
            //output = engine.render(tmpl, items);
            if (!warmUp && ntimes == 0) {
                //w1.write(output);
                engine.render(w1, tmpl, items);
            } else {
                //w0.write(output);
                engine.render(w0, tmpl, items);
            }
        }
    }

    @Override
    public void execute(boolean warmUp, OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        String tmpl = template;
        while (--ntimes >= 0) {
            if (!warmUp && ntimes == 0) {
                engine.render(o1, tmpl, items);
            }
            else {
                engine.render(o0, tmpl, items);
            }
        }
    }

    @Override
    protected boolean useStream() {
        return true;
    }

    public static void main(String[] args) {
        new Rythm().run();
    }

}
