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
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
            StringWriter writer = new StringWriter(1024);
            Map root = new HashMap();
            root.put("items", items);
            Template template = cfg.getTemplate("stocks.ftl.html");
            template.process(root, writer);
            output = writer.toString();
        }
        return output;
    }

    public static void main(String[] args) throws Exception {
        new StocksFreeMarkerBench().run();
    }

}
