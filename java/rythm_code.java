package templates.rythm.stocks;
import teb.Stock;
public class rythm extends com.greenlaw110.rythm.template.TemplateBase {
private java.util.List<Stock> items=null;
@SuppressWarnings("unchecked") public void setRenderArgs(java.util.Map<String, Object> args) {
if (null != args && args.containsKey("items")) this.items=(java.util.List<Stock>)args.get("items");
}
@SuppressWarnings("unchecked") public void setRenderArgs(Object... args) {
int p = 0, l = args.length;
if (p < l) { Object v = args[p++]; boolean isString = ("java.lang.String".equals("java.util.List<Stock>") || "String".equals("ja
va.util.List<Stock>")); items = (java.util.List<Stock>)(isString ? (null == v ? "" : v.toString()) : v); }
}
@Override public com.greenlaw110.rythm.util.TextBuilder build(){
p("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n          \"htt
p://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n <head>\
n  <title>Stock Prices - JAPID</title>\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n  <meta http-equiv=\"Co
ntent-Style-Type\" content=\"text/css\" />\n  <meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n  <link rel=\"shortcu
t icon\" href=\"/images/favicon.ico\" />\n  <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" media=\"all\" />\n  <script t
ype=\"text/javascript\" src=\"/js/util.js\"></script>\n  <style type=\"text/css\">\n  /*<![CDATA[*/\nbody {\n    color: #333333;\n    line-h
eight: 150%;\n}\nthead {\n    font-weight: bold;\n    background-color: #CCCCCC;\n}\n.odd {\n    background-color: #FFCCCC;\n}\n.even {\n
 background-color: #CCCCFF;\n}\n.minus {\n    color: #FF0000;\n}\n  /*]]>*/\n  </style>\n </head>\n <body>\n  <h1>Stock Prices</h1>\n  <tabl
e>\n   <thead>\n    <tr>\n     <th>#</th><th>symbol</th><th>name</th><th>price</th><th>change</th><th>ratio</th>\n    </tr>\n   </thead>\n
 <tbody>\n");
new com.greenlaw110.rythm.runtime.Each(this).render(items, new com.greenlaw110.rythm.runtime.Each.Body<Stock>(){
    public void render(final Stock item, final int size, final int item_index, final boolean item_isOdd, final String item_parit
y, final boolean item_first, final boolean item_last) {
     [java]
p("<tr class=\"${_isOdd ? \"odd\" : \"even\"}\">\n     <td style=\"text-align: center\">");
p(items.size());
p("</td>\n     <td>\n      <a href=\"/stocks/");
p(item.getSymbol());
p("\">");
p(item.getSymbol());
p("</a>\n     </td>\n     <td>\n      <a href=\"");
p(item.getUrl());
p("\">");
p(item.getName());
p("</a>\n     </td>\n     <td>\n      <strong>");
p(item.getPrice());
p("</strong>\n     </td>\n     ");if (item.getChange() < 0.0) {
p(" \n     <td class=\"minus\">");
p(item.getChange());
p("</td>\n     <td class=\"minus\">");
p(item.getRatio());
p("</td>\n     ");}else
     {
p("<td>");
p(item.getChange());
p("</td>\n     <td>");
p(item.getRatio());
p("</td>\n     ");}
p("\n    </tr>\n");
    }
});
p("\n   </tbody>\n  </table>\n </body>\n</html>\n");
return this;
}
}