/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
//import org.apache.velocity.tools.generic.EscapeTool;


public class StocksVelocityServlet extends HttpServlet {

    private VelocityEngine _engine;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        _engine = new VelocityEngine();
        //String templates_path = getServletContext().getRealPath("WEB-INF/templates");
        //_engine.setProperty("file.resource.loader.path", templates_path);
        //_engine.setProperty("file.resource.loader.cache", "true");
        //_engine.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
        try {
            //_engine.init();
            _engine.init(getServletContext().getRealPath("WEB-INF/velocity.properties"));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        /// create context data
        VelocityContext context = new VelocityContext();
        List<Stock> items = Stock.dummyItems();
        context.put("items", items);
        //EscapeTool esc = new EscapeTool();
        //context.put("esc", esc);
        /// render template with context data
        Template template = null;
        try {
            template = _engine.getTemplate("stocks.vm", "UTF-8");
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //PrintWriter out = response.getWriter();
        //template.merge(context, out);
        //OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
        //template.merge(context, out);
        //out.flush();  // necessary
        StringWriter writer = new StringWriter(1024);
        template.merge(context, writer);
        String html = writer.toString();
        response.getWriter().write(html);
    }

}
