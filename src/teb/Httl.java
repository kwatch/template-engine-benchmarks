/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import httl.*;
import teb.model.Stock;

public class Httl extends _BenchBase {

    private Engine engine;
    private String templateFile = "/stocks.httl.html";
    
    public Httl() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("import.packages", "teb.model,java.util");
        prop.setProperty("filter", "null");
        prop.setProperty("logger", "null");
        engine = Engine.getEngine(prop);
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("items", items);
        while (--ntimes >= 0) {
            Template template = engine.getTemplate(templateFile);

            if (!warmUp && ntimes == 0) template.render(params,w1);
            else template.render(params, w0);
        }
    }

    public static void main(String[] args) throws Exception {
        new Httl().run();
    }

}
