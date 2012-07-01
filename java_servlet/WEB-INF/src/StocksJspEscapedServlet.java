/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StocksJspEscapedServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        List<Stock> items = Stock.dummyItems();
        request.setAttribute("items", items);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/stocks-escaped.jsp");
        dispatcher.forward(request, response);
    }

}
