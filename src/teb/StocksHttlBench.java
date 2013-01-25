/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import httl.*;

public class StocksHttlBench extends Bench {

    private Engine engine;
    private String templateFile = "/stocks.httl.html";
    
    public StocksHttlBench() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("import.packages", "teb,java.util");
        prop.setProperty("filter", "null");
        engine = Engine.getEngine(prop);
    }

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
        try {
            StringWriter writer = new StringWriter(1024);
            Map<String, Object> params = new HashMap();
            params.put("items", items);
            Template template = engine.getTemplate(templateFile);
            template.render(params, writer);
            output = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        }
        return output;
    }

    public static void main(String[] args) throws Exception {
        new StocksHttlBench().run();
    }

}
