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
import teb.model.Stock;


public class Beetl extends _BenchBase {

    GroupTemplate group;
    private String template = "templates/";
    
    public Beetl() {
        group = new GroupTemplate(new File(template));
        group.config("<!--:", "-->", "${", "}");
        group.setCharset("UTF-8");
        group.enableOptimize();
        //group.enableDirectOutputByte();
        OutputStreamByteWriter.DEFAULT_BYTE_BUFFER_SIZE = 2048;
    }
    
    protected void shutdown() {
    }

    @Override
    protected boolean useStream() {
        return true;
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            
            if (!warmUp && ntimes == 0) template.getText(w1);
            else template.getText(w0);
        }
    }

    @Override
    public void execute(boolean warmUp, OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            
            if (!warmUp && ntimes == 0) template.getText(o1);
            else template.getText(o0);
        }
    }

    public static void main(String[] args) {
        new Beetl().run();
    }
}
