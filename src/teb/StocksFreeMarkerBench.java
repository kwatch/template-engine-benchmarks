/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import freemarker.template.*;

public class StocksFreeMarkerBench extends Bench {

    private Configuration cfg;
    public StocksFreeMarkerBench() throws Exception {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("templates/"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());  
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

    public static void main(String[] args) throws Exception {
        new StocksFreeMarkerBench().run();
    }

}
