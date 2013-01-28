/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import freemarker.template.*;
import teb.model.Stock;

public class FreeMarker extends _BenchBase {

    private Configuration cfg;
    public FreeMarker() throws Exception {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("templates/"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());  
    }

    @Override
    protected boolean useStream() {
        return true;
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        while (--ntimes >= 0) {
            Template template = cfg.getTemplate("stocks.ftl.html");

            if (!warmUp && ntimes == 0) template.process(root,w1);
            else template.process(root, w0);
        }
    }

    @Override
    public void execute(boolean warmUp, OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Map root = new HashMap();
        root.put("items", items);
        Writer w0 = new BufferedWriter(new OutputStreamWriter(o0));
        Writer w1 = new BufferedWriter(new OutputStreamWriter(o1));
        while (--ntimes >= 0) {
            Template template = cfg.getTemplate("stocks.ftl.html");

            if (!warmUp && ntimes == 0) template.process(root, w1);
            else template.process(root, w0);
        }
    }

    public static void main(String[] args) throws Exception {
        new FreeMarker().run();
    }

}
