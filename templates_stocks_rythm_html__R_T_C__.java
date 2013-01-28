import teb.model.Stock; //line: 60
import java.util.*;
import java.io.*;

public class templates_stocks_rythm_html__R_T_C__ extends com.greenlaw110.rythm.template.TagBase {

	@Override public String getName() {
		return "templates_stocks_rythm_html__R_T_C__";
	}

	@Override protected void setup() {
		if (items == null) {items=(List<Stock>)_get("items");}
	}

	protected List<Stock> items=null; //line: 61

	@SuppressWarnings("unchecked")
	public void setRenderArgs(Map<String, Object> args) {
		if (null == args) throw new NullPointerException();
		if (args.isEmpty()) return;
		super.setRenderArgs(args);
		if (args.containsKey("items")) this.items=(List<Stock>)args.get("items");
	}

	@SuppressWarnings("unchecked") public void setRenderArgs(Object... args) {
		int _p = 0, l = args.length;
		if (_p < l) { Object v = args[_p++]; boolean isString = ("java.lang.String".equals("java.util.List<Stock>") || "String".equals("java.util.List<Stock>")); items = (List<Stock>)(isString ? (null == v ? "" : v.toString()) : v); }
	}

	@SuppressWarnings("unchecked") @Override public void setRenderArg(String name, Object arg) {
		if ("items".equals(name)) this.items=(List<Stock>)arg;
		super.setRenderArg(name, arg);
	}

	@SuppressWarnings("unchecked") public void setRenderArg(int pos, Object arg) {
		int _p = 0;
		if (_p++ == pos) { Object v = arg; boolean isString = ("java.lang.String".equals("java.util.List<Stock>") || "String".equals("java.util.List<Stock>")); items = (List<Stock>)(isString ? (null == v ? "" : v.toString()) : v); }
		if(0 == pos) setRenderArg("arg", arg);
	}



	@Override public com.greenlaw110.rythm.utils.TextBuilder build(){
		out().ensureCapacity(3102);
p("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n          \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n <head>\n  <title>Stock Prices - Rythm</title>\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n  <meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n  <meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n  <link rel=\"shortcut icon\" href=\"/images/favicon.ico\" />\n  <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" media=\"all\" />\n  <script type=\"text/javascript\" src=\"/js/util.js\"></script>\n  <style type=\"text/css\">\n  /*<![CDATA[*/\n\nbody {\n    color: #333333;\n    line-height: 150%;\n}\n\nthead {\n    font-weight: bold;\n    background-color: #CCCCCC;\n}\n\n.odd {\n    background-color: #FFCCCC;\n}\n\n.even {\n    background-color: #CCCCFF;\n}\n\n.minus {\n    color: #FF0000;\n}\n\n  /*]]>*/\n  </style>\n\n </head>\n\n <body>\n\n  <h1>Stock Prices</h1>\n\n  <table>\n   <thead>\n    <tr>\n     <th>#</th><th>symbol</th><th>name</th><th>price</th><th>change</th><th>ratio</th>\n    </tr>\n   </thead>\n   <tbody>\n   \n"); //line: 118
p("\n"); //line: 118
p("\n"); //line: 118
__ctx.pushEscape(Escape.RAW); //line: 68
p("\n\n"); //line: 118
p("\n"); //line: 118
{
_Itr<Stock> __v0 = new _Itr(items); //line: 83
int item_size = __v0.size(); //line: 83
if (item_size > 0) { //line: 83
int item_index = 0; //line: 83
for(Stock item : __v0) { //line: 83
item_index++; //line: 83
boolean item_isOdd = item_index % 2 == 1; //line: 83
String item_parity = item_isOdd ? "odd" : "even"; //line: 83
boolean item_isFirst = item_index == 1; //line: 83
boolean item_isLast = item_index >= item_size; //line: 83
String item_sep = item_isLast ? "" : ","; //line: 83
com.greenlaw110.rythm.runtime.Each.IBody.LoopUtils item_utils = new com.greenlaw110.rythm.runtime.Each.IBody.LoopUtils(item_isFirst, item_isLast); //line: 83
p("\n    <tr class=\""); //line: 118

try{pe(item_parity);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 84
p("\">\n    "); //line: 118
p("    "); //line: 118
 //line: 86
        String symbol = item.getSymbol();  //line: 87
        double change = item.getChange(); //line: 88
     //line: 89
 //line: 90
p("     <td style=\"text-align: center\">"); //line: 118

try{pe(item_index);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 90
p("</td>\n     <td>\n      <a href=\"/stocks/"); //line: 118

try{pe(symbol);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 92
p("\">"); //line: 118

try{pe(symbol);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 92
p("</a>\n     </td>\n     <td>\n      "); //line: 118
p("      "); //line: 118
p("      "); //line: 118
p("      <a href=\""); //line: 118

try{pe(item.getUrl());} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 98
p("\">"); //line: 118

try{pe((item.getName()));} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 98
p("</a>\n     </td>\n     <td>\n      <strong>"); //line: 118

try{pe(item.getPrice());} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 101
p("</strong>\n     </td>\n     "); //line: 118
if (change < 0.0) { //line: 103
p("\n     <td class=\"minus\">"); //line: 118

try{pe(change);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 104
p("</td>\n     <td class=\"minus\">"); //line: 118

try{pe(item.getRatio());} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 105
p("</td>\n     "); //line: 118
}else { //line: 106
p("\n     <td>"); //line: 118

try{pe(change);} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 107
p("</td>\n     <td>"); //line: 118

try{pe(item.getRatio());} catch (RuntimeException e) {handleTemplateExecutionException(e);}  //line: 108
p("</td>\n     "); //line: 118
} //line: 109
p("\n    </tr>\n"); //line: 118

	}
}
}
 //line: 111
p("\n"); //line: 118
__ctx.popEscape(); //line: 112
p("\n   </tbody>\n  </table>\n\n </body>\n</html>\n"); //line: 118

		return this;
	}

}
