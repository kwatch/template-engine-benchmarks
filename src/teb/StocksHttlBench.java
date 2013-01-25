/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import httl.*;
import httl.util.*;

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
    public void execute(boolean warmUp, Writer writer, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap();
        params.put("items", items);
        while (--ntimes >= 0) {
            Template template = engine.getTemplate(templateFile);
            template.render(params, writer);
        }
    }

    public static void main(String[] args) throws Exception {
        new StocksHttlBench().run();
    }

}
