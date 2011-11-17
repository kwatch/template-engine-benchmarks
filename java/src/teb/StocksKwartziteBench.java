/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class StocksKwartziteBench extends Bench {

    @Override
    public String execute(int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
            //StocksPage page = new StocksPage();
            //page.setItems(items);
            ////page.items = items;
            //output = page.render();
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("items", items);
            kwartzite.Template page = new StocksPage();
            output = page.render(context);
        }
        return output;
    }

    public static void main(String[] args) {
        new StocksKwartziteBench().run();
    }

}
