/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.bee.tl.core.io.OutputStreamByteWriter;

import java.util.logging.*;


public class StocksBeetlBench extends Bench {

    GroupTemplate group;
    private String template = "templates/";
    
    public StocksBeetlBench() {
        group = new GroupTemplate(new File(template));
        group.config("<!--:", "-->", "${", "}");
        group.setCharset("UTF-8");
        group.enableOptimize();
        group.enableDirectOutputByte();
        OutputStreamByteWriter.DEFAULT_BYTE_BUFFER_SIZE = 2048;
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
            DoNothingOutputSteam out = new DoNothingOutputSteam();
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            template.getText(out);
            //output = out.toString("utf-8");
        }
        
        return output;
    }

    public static void main(String[] args) {
        new StocksBeetlBench().run();
    }

    public static class DoNothingOutputSteam extends java.io.OutputStream {

        @Override
        public void write(int b) throws IOException {
        }

        public void write(byte[] bs) throws IOException {
        }

        public void write(byte b[], int off, int len) throws IOException{
        }

    }
}
