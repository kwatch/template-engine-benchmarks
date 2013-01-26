/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import net.asfun.jangod.template.TemplateEngine;
import teb.model.Stock;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Jangod extends _BenchBase {

    TemplateEngine engine;
    private String template = "templates/stocks.jangod.html";
    
    public Jangod() {
        engine = new TemplateEngine();
        engine.getConfiguration().setWorkspace(".");
    }
    
    protected void shutdown() {
        //engine.shutdown();
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        String output;
        String tmpl = template;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        while (--ntimes >= 0) {
            output = engine.process(tmpl, params);
            if (!warmUp && ntimes == 0) w1.write(output);
            else w0.write(output);
        }
    }

    public static void main(String[] args) {
        new Jangod().run();
    }

}
