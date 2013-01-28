/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.util.*;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import teb.model.Stock;
//import org.apache.velocity.tools.generic.EscapeTool;


public class Velocity extends _BenchBase {

    static VelocityEngine _engine;
    static {
        _engine = new VelocityEngine();
        //_engine.setProperty("file.resource.loader.path", "templates");
        //_engine.setProperty("file.resource.loader.cache", "true");
        //_engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        try {
            //_engine.init();
            _engine.init("velocity.properties");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        /// create context data
        VelocityContext context = new VelocityContext();
        context.put("items", items);
        while (--ntimes >= 0) {
            //EscapeTool esc = new EscapeTool();
            //context.put("esc", esc);
            /// render template with context data
            Template template = _engine.getTemplate("stocks.vm.html", "UTF-8");

            if (ntimes == 0) template.merge(context, w1);
            else template.merge(context, w0);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        /// create context data
        VelocityContext context = new VelocityContext();
        context.put("items", items);
        Writer w0 = new BufferedWriter(new OutputStreamWriter(o0));
        Writer w1 = new BufferedWriter(new OutputStreamWriter(o1));
        while (--ntimes >= 0) {
            //EscapeTool esc = new EscapeTool();
            //context.put("esc", esc);
            /// render template with context data
            Template template = _engine.getTemplate("stocks.vm.html", "UTF-8");

            if (ntimes == 0) template.merge(context, w1);
            else template.merge(context, w0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        VelocityContext context = new VelocityContext();
        context.put("items", items);
        Writer w0 = new StringWriter(1024 * 10);
        Writer w1 = new StringWriter(1024 * 10);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        while (--ntimes >= 0) {
            //EscapeTool esc = new EscapeTool();
            //context.put("esc", esc);
            /// render template with context data
            Template template = _engine.getTemplate("stocks.vm.html", "UTF-8");

            if (ntimes == 0) template.merge(context, w1);
            else template.merge(context, w0);
        }
        
        return w1.toString();
    }

    public static void main(String[] args) {
        new Velocity().run();
    }

}
