/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;

import java.util.logging.*;


public class StocksBeetlBench extends Bench {

    GroupTemplate group;
    private String template = "templates/beetl/";
    
    public StocksBeetlBench() {
        group = new GroupTemplate(new File(template));
        group.config("<!--:", "-->", "${", "}");
        group.setCharset("UTF-8");
        group.enableOptimize();
    }
    
    protected void shutdown() {
    }

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        String tmpl = template;
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            output = template.getTextAsString();
        }
        
        return output;
    }

    public static void main(String[] args) {
        new StocksBeetlBench().run();
    }

}
