package teb;

import java.util.List;
import java.util.Map;
import kwartzite.Elem;

public class StocksPage extends StocksHtml {

    private List<Stock> items;
    //private Stock item;

    public void setItems(List<Stock> items) {
        this.items = items;
    }

    @SuppressWarnings("unchecked")
    public void context(Map<String, Object> values) {
        items = (List<Stock>)values.get("items");
    }

    protected void elemItems(Elem e) {
        int i = 0;
        for (Stock item: items) {
            i++;
            e.attr("class", i % 2 == 1 ? "odd" : "even");
            elemIndex.text(i);
            elemSymbol.text(item.getSymbol());
            elemSymbol.attr("href", "/stocks/" + item.getSymbol());
            elemUrl.text(item.getName());
            elemUrl.attr("href", item.getUrl());
            elemPrice.text(item.getPrice());
            String klass = item.getChange() < 0.0 ? "minus" : null;
            elemChange.text(item.getChange());
            elemChange.attr("class", klass);
            elemRatio.text(item.getRatio());
            elemRatio.attr("class", klass);
            e.elem();
        }
    }

//    protected void elemItems(Elem e) {
//      int i = 0;
//      for (Stock x: items) {
//          item = x;
//          i++;
//          e.attr("class", i % 2 == 1 ? "odd" : "even");
//          setIndex(i);
//          setPrice(item.getPrice());
//          e.elem();
//      }
//    }
//
//    protected void elemSymbol(Elem e) {
//      e.text(item.getSymbol());
//      e.attr("href", "/stocks/" + item.getSymbol());
//      e.elem();
//    }
//
//    protected void elemUrl(Elem e) {
//      e.text(item.getName());
//      e.attr("href", item.getUrl());
//    }
//
//    protected void elemChange(Elem e) {
//      e.attr("class", item.getChange() < 0.0 ? "minus" : null);
//      e.text(item.getChange());
//      e.elem();
//    }
//
//    protected void elemRatio(Elem e) {
//      e.attr("class", item.getChange() < 0.0 ? "minus" : null);
//      e.text(item.getRatio());
//      e.elem();
//    }

    public static void main(String[] args) {
        int max = 100*1000;
        if (args.length > 0) {
            max = Integer.valueOf(args[0]);
        }
        String output = "";
        for (int i = 0; i < max; i++) {
            StocksPage page = new StocksPage();
            page.items = Stock.dummyItems();
            output = page.render();
        }
        System.out.print(output);
    }

}