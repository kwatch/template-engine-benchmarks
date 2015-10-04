/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import org.rythmengine.RythmEngine;
import teb.model.Stock;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;


public class Rythm extends _BenchBase {

    RythmEngine engine;
    private String template = "templates/stocks.rythm.html";
    
    public Rythm() {
        Properties p = new Properties();
        p.put("log.enabled", false);
        p.put("feature.smart_escape.enabled", false);
        p.put("feature.transform.enabled", false);
        //p.put("codegen.dynamic_exp.enabled", true);
        //p.put("built_in.code_type", "false");
        //p.put("built_in.transformer", "false");
        //p.put("engine.file_write", "false");
        //p.put("codegen.compact.enabled", "false");
        //p.put("home.template", "p:/template-engine-benchmarks");
        //p.put("home.tmp", "c:\\tmp");
        //p.put("engine.mode", Rythm.Mode.dev);
        engine = new RythmEngine(p);
    }
    
    protected void shutdown() {
        engine.shutdown();
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        String tmpl = template;
        boolean newAPI = Boolean.parseBoolean(System.getProperty("rythm.new", "true"));
        if (newAPI) {
            while (--ntimes >= 0) {
                if (ntimes == 0) {
                    engine.render(w1, tmpl, items);
                    w1.close();
                } else {
                    engine.render(w0, tmpl, items);
                }
            }
        } else {
            String output;
            while (--ntimes >= 0) {
                output = engine.render(tmpl, items);
                if (ntimes == 0) {
                    w1.write(output);
                    w1.close();
                } else {
                    w0.write(output);
                }
            }
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        String tmpl = template;
        boolean newAPI = Boolean.parseBoolean(System.getProperty("rythm.new", "false"));
        if (newAPI) {
            while (--ntimes >= 0) {
                if (ntimes == 0) {
                    engine.render(o1, tmpl, items);
                    o1.close();
                } else {
                    engine.render(o0, tmpl, items);
                }
            }
        } else {
            String output;
            Writer w0 = new OutputStreamWriter(o0);
            Writer w1 = new OutputStreamWriter(o1);
            if (_BenchBase.bufferMode.get()) {
                w0 = new BufferedWriter(w0);
                w1 = new BufferedWriter(w1);
            }
            while (--ntimes >= 0) {
                output = engine.render(tmpl, items);
                if (ntimes == 0) {
                    w1.write(output);
                    w1.close();
                } else {
                    w0.write(output);
                }
            }
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        String tmpl = template;
        String output = null;
        while (--ntimes >= 0) {
            output = engine.render(tmpl, items);
        }
        return output;
    }


    public static void main(String[] args) {
        //System.setProperty("out", "w");
        //System.setProperty("buf", "true");
        //System.setProperty("wtimes", "0");
        //System.setProperty("ntimes", "1");
        new Rythm().run();
        //String s = com.greenlaw110.rythm.Rythm.render("hool @abc", "sd");
        //System.out.println(s);
    }

}
