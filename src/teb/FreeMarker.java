/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import freemarker.template.*;
import teb.model.Stock;
import teb.util.DoNothingWriter;

public class FreeMarker extends _BenchBase {

    private Configuration cfg;
    public FreeMarker() throws Exception {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("templates/"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());  
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        while (--ntimes >= 0) {
            Template template = cfg.getTemplate("stocks.ftl.html");

            if (ntimes == 0) {
                template.process(root,w1);
                w1.close();
            }
            else template.process(root, w0);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        Writer w0 = new OutputStreamWriter(o0);
        Writer w1 = new OutputStreamWriter(o1);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            Template template = cfg.getTemplate("stocks.ftl.html");

            if (ntimes == 0) {
                template.process(root, w1);
                w1.close();
            }
            else template.process(root, w0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        Writer w0 = new StringWriter();
        Writer w1 = new StringWriter();
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            Template template = cfg.getTemplate("stocks.ftl.html");

            if (ntimes == 0) {
                template.process(root, w1);
                w1.close();
            }
            else template.process(root, w0);
        }

        return w1.toString();
    }

    public static void main(String[] args) throws Exception {
        new FreeMarker().run();
    }

}
