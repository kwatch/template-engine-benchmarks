/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.StringWriter;
import java.io.IOException;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
//import org.apache.velocity.tools.generic.EscapeTool;


public class StocksVelocityBench extends Bench {

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
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
            /// create context data
            VelocityContext context = new VelocityContext();
            context.put("items", items);
            //EscapeTool esc = new EscapeTool();
            //context.put("esc", esc);
            /// render template with context data
            Template template = _engine.getTemplate("stocks.vm.html", "UTF-8");
            StringWriter writer = new StringWriter(1024);
            template.merge(context, writer);
            output = writer.toString();
        }
        return output;
    }

    public static void main(String[] args) {
        new StocksVelocityBench().run();
    }

}
