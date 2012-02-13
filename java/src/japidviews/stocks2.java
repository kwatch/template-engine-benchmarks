package japidviews;
import java.util.*;
import java.io.*;
import cn.bran.japid.tags.Each;
import teb.Stock;
import japidviews ._tags.*;
import japidviews ._layouts.*;
//
// NOTE: This file was generated from: japidviews/stocks2.html
// Change to this file will be lost next time the template file is compiled.
//
public class stocks2 extends cn.bran.japid.template.JapidTemplateBaseWithoutPlay
{	public static final String sourceTemplate = "japidviews/stocks2.html";
	public stocks2() {
		super(null);
	}
	public stocks2(StringBuilder out) {
		super(out);
	}
/* based on https://github.com/branaway/Japid/issues/12
 */
public static final String[] argNames = new String[] {/* args of the template*/"items", "name",  };
public static final String[] argTypes = new String[] {/* arg types of the template*/"java.util.List<Stock>", "String",  };
public static final Object[] argDefaults= new Object[] {null,null, };
public static java.lang.reflect.Method renderMethod = getRenderMethod(japidviews.stocks2.class);

{
	setRenderMethod(renderMethod);
	setArgNames(argNames);
	setArgTypes(argTypes);
	setArgDefaults(argDefaults);
	setSourceTemplate(sourceTemplate);

}
////// end of named args stuff

	private java.util.List<Stock> items;
	private String name;
	public String render(java.util.List<Stock> items,String name) {
		this.items = items;
		this.name = name;
		long t = -1;
		try {super.layout();} catch (RuntimeException e) { super.handleException(e);}
		 if (t != -1) System.out.println("[stocks2] rendering time: " + t);
		return getOut().toString();
	}
	@Override protected void doLayout() {

// -- set up the tag objects
final Each _Each0 = new Each(getOut());

// -- end of the tag objects

//------
;// line 1
p("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" + 
"          \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + 
"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" + 
" <head>\n" + 
"  <title>Stock Prices - JAPID</title>\n" + 
"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" + 
"  <meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n" + 
"  <meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n" + 
"  <link rel=\"shortcut icon\" href=\"/images/favicon.ico\" />\n" + 
"  <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" media=\"all\" />\n" + 
"  <script type=\"text/javascript\" src=\"/js/util.js\"></script>\n" + 
"  <style type=\"text/css\">\n" + 
"  /*<![CDATA[*/\n" + 
"\n" + 
"body {\n" + 
"    color: #333333;\n" + 
"    line-height: 150%;\n" + 
"}\n" + 
"\n" + 
"thead {\n" + 
"    font-weight: bold;\n" + 
"    background-color: #CCCCCC;\n" + 
"}\n" + 
"\n" + 
".odd {\n" + 
"    background-color: #FFCCCC;\n" + 
"}\n" + 
"\n" + 
".even {\n" + 
"    background-color: #CCCCFF;\n" + 
"}\n" + 
"\n" + 
".minus {\n" + 
"    color: #FF0000;\n" + 
"}\n" + 
"\n" + 
"  /*]]>*/\n" + 
"  </style>\n" + 
"\n" + 
" </head>\n" + 
"\n" + 
" <body>\n" + 
"\n" + 
"  <h1>Stock Prices</h1>\n" + 
"\n" + 
"  <table>\n" + 
"   <thead>\n" + 
"    <tr>\n" + 
"     <th>#</th><th>symbol</th><th>name</th><th>price</th><th>change</th><th>ratio</th>\n" + 
"    </tr>\n" + 
"   </thead>\n" + 
"   <tbody>\n");// line 2
		_Each0.setOut(getOut()); _Each0.render(items, new Each.DoBody<Stock>(){
public void render(final Stock item, final int _size, final int _index, final boolean _isOdd, final String _parity, final boolean _isFirst, final boolean _isLast) {
// line 56
		p("    <tr class=\"");// line 56
		p(_parity);// line 57
		p("\">\n" + 
"     <td style=\"text-align: center\">");// line 57
		p(_index);// line 58
		p("</td>\n" + 
"     <td>\n" + 
"      <a href=\"/stocks/");// line 58
		p(item.getSymbol());// line 60
		p("\">");// line 60
		p(item.getSymbol());// line 60
		p("</a>\n" + 
"     </td>\n" + 
"     <td>\n" + 
"      <a href=\"");// line 60
		p(item.getUrl());// line 63
		p("\">");// line 63
		p(item.getName());// line 63
		p("</a>\n" + 
"     </td>\n" + 
"     <td>\n" + 
"      <strong>");// line 63
		p(item.getPrice());// line 66
		p("</strong>\n" + 
"     </td>\n" + 
"     ");// line 66
		if (item.getChange() < 0.0) {// line 68
		p("     <td class=\"minus\">");// line 68
		p(item.getChange());// line 69
		p("</td>\n" + 
"     <td class=\"minus\">");// line 69
		p(item.getRatio());// line 70
		p("</td>\n" + 
"     ");// line 70
		} else {// line 71
		p("     <td>");// line 71
		p(item.getChange());// line 72
		p("</td>\n" + 
"     <td>");// line 72
		p(item.getRatio());// line 73
		p("</td>\n" + 
"     ");// line 73
		}// line 74
		p("    </tr>\n");// line 74
		
}

StringBuilder oriBuffer;
@Override
public void setBuffer(StringBuilder sb) {
	oriBuffer = getOut();
	setOut(sb);
}

@Override
public void resetBuffer() {
	setOut(oriBuffer);
}

}
);// line 56
		p("   </tbody>\n" + 
"  </table>\n" + 
"\n" + 
" </body>\n" + 
"</html>\n");// line 76
		
	}

}