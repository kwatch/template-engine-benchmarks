/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class StocksKwartziteServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        List<Stock> items = Stock.dummyItems();
        //StocksPage page = new StocksPage();
        //page.setItems(items);
        ////page.items = items;
        //String html = page.render();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("items", items);
        kwartzite.Template page = new StocksPage();
        String html = page.render(context);
        response.getWriter().write(html);
    }

}
