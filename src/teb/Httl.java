/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import httl.*;
import teb.model.Stock;
import teb.util.DoNothingOutputStream;

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
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("items", items);
        while (--ntimes >= 0) {
            Template template = engine.getTemplate(templateFile);

            if (ntimes == 0) template.render(params,w1);
            else template.render(params, w0);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("items", items);
        while (--ntimes >= 0) {
            Template template = engine.getTemplate(templateFile);

            if (ntimes == 0) template.render(params,o1);
            else template.render(params, o0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("items", items);
        Writer w0 = new StringWriter();
        Writer w1 = new StringWriter(1024 * 10);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            Template template = engine.getTemplate(templateFile);

            if (ntimes == 0) template.render(params,w1);
            else template.render(params, w0);
        }
        return w1.toString();
    }

    public static void main(String[] args) throws Exception {
        new Httl().run();
    }

}
