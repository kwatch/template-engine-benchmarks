/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.bee.tl.core.io.OutputStreamByteWriter;
import teb.model.Stock;

import java.io.*;
import java.util.List;


public class Beetl extends _BenchBase {

    GroupTemplate group;
    private String template = "templates/";
    
    public Beetl() {
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
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            
            if (ntimes == 0) {
                template.getText(w1);
                w1.close();
            }
            else template.getText(w0);
        }
    }
    
    private static Template t = null;

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            
            if (ntimes == 0) {
                template.getText(o1);
                o1.close();
            }
            else template.getText(o0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new StringWriter(1024 * 10);
        Writer w1 = new StringWriter(1024 * 10);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            Template template = group.getFileTemplate("/stocks.beetl.html");
            template.set("items", items);
            
            if (ntimes == 0) {
                template.getText(w1);
                w1.close();
            }
            else template.getText(w0);
        }
        
        return w1.toString();
    }

    public static void main(String[] args) {
        new Beetl().run();
    }
}
