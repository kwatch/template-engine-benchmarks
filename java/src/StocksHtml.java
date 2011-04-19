// generated from stocks.html by pykwartzite 0.0.0

import java.util.Map;
import java.util.HashMap;

import kwartzite.Template;
import kwartzite.Util;
import kwartzite.Elem;
import kwartzite.Dom;


public class StocksHtml extends Object implements kwartzite.Template {

    private StringBuilder _buf;

    public StocksHtml(int bufsize) {
        _buf = new StringBuilder(bufsize);
    }
    
    public StocksHtml() {
        this(1024);
    }


    public String render(Map<String, Object> values) {
        context(values);
        return render();
    }

    public String render() {
        createDocument();
        return _buf.toString();
    }

    public void context(Map<String, Object> values) {
    }

    public void createDocument() {
            _buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
             + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n"
             + "          \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
             + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
             + " <head>\n"
             + "  <title>Stock Prices</title>\n"
             + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
             + "  <meta http-equiv=\"Content-Style-Type\" content=\"text/css\" />\n"
             + "  <meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\" />\n"
             + "  <link rel=\"shortcut icon\" href=\"/images/favicon.ico\" />\n"
             + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"/css/style.css\" media=\"all\" />\n"
             + "  <script type=\"text/javascript\" src=\"/js/util.js\"></script>\n"
             + "  <style type=\"text/css\">\n"
             + "  /*<![CDATA[*/\n"
             + "\n"
             + "body {\n"
             + "    color: #333333;\n"
             + "    line-height: 150%;\n"
             + "}\n"
             + "\n"
             + "thead {\n"
             + "    font-weight: bold;\n"
             + "    background-color: #CCCCCC;\n"
             + "}\n"
             + "\n"
             + ".odd {\n"
             + "    background-color: #FFCCCC;\n"
             + "}\n"
             + "\n"
             + ".even {\n"
             + "    background-color: #CCCCFF;\n"
             + "}\n"
             + "\n"
             + ".minus {\n"
             + "    color: #FF0000;\n"
             + "}\n"
             + "\n"
             + "  /*]]>*/\n"
             + "  </style>\n"
             + "\n"
             + " </head>\n"
             + "\n"
             + " <body>\n"
             + "\n"
             + "  <h1>Stock Prices</h1>\n"
             + "\n"
             + "  <table>\n"
             + "   <thead>\n"
             + "    <tr>\n"
             + "     <th>#</th><th>symbol</th><th>name</th><th>price</th><th>change</th><th>ratio</th>\n"
             + "    </tr>\n"
             + "   </thead>\n"
             + "   <tbody>\n");
            elemItems();
            _buf.append("   </tbody>\n"
             + "  </table>\n"
             + "\n"
             + " </body>\n"
             + "</html>\n");
    }


    public void echo(String arg)  {
        if (arg == null) return;
        _buf.append(Util.escapeHtml(arg));
    }
    public void echo(Dom.Node arg)  {
        if (arg == null) return;
        _buf.append(arg.toHtml());
    }
    public void echo(Object arg) {
        if (arg == null) return;
        if (arg instanceof Dom.Node) {
            _buf.append(((Dom.Node)arg).toHtml());
        }
        else {
            _buf.append(Util.escapeHtml(arg.toString()));
        }
    }
    public void echo(int arg)     { _buf.append(arg); }
    public void echo(double arg)  { _buf.append(arg); }
    //public void echo(char arg)    { _buf.append(arg); }
    //public void echo(char[] arg)  { _buf.append(arg); }
    public void echo(boolean arg) { _buf.append(arg); }


    ///
    /// element 'items'
    ///

    private Object _textItems = null;

    public Object getItems() {
        return _textItems;
    }

    public void setItems(Object val) {
        _textItems = val;
    }

    protected Elem elemItems = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("    <tr class=\"odd\">\n");
            } else {
                _buf.append("    <tr");
                if (! _attrs.has("class")) _buf.append(" class=\"odd\"");
                _attrs.appendTo(_buf);
                _buf.append(">\n");
            }
        }

        public void cont() {
            if (_textItems != null) {
                echo(_textItems);
                return;
            }
            elemIndex();
            _buf.append("     <td>\n");
            elemSymbol();
            _buf.append("     </td>\n"
             + "     <td>\n");
            elemUrl();
            _buf.append("     </td>\n"
             + "     <td>\n");
            elemPrice();
            _buf.append("     </td>\n");
            elemChange();
            elemRatio();
        }

        public void etag() {
            _buf.append("    </tr>\n");
        }

        public Object text() {
            return _textItems;
        }

        public void text(Object obj) {
            _textItems = obj;
        }

    };

    protected void elemItems(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemItems() {
        elemItems(elemItems);
    }


    ///
    /// element 'index'
    ///

    private Object _textIndex = null;

    public Object getIndex() {
        return _textIndex;
    }

    public void setIndex(Object val) {
        _textIndex = val;
    }

    protected Elem elemIndex = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("     <td style=\"text-align: center\">");
            } else {
                _buf.append("     <td");
                if (! _attrs.has("style")) _buf.append(" style=\"text-align: center\"");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textIndex != null) {
                echo(_textIndex);
                return;
            }
            _buf.append("1");
        }

        public void etag() {
            _buf.append("</td>\n");
        }

        public Object text() {
            return _textIndex;
        }

        public void text(Object obj) {
            _textIndex = obj;
        }

    };

    protected void elemIndex(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemIndex() {
        elemIndex(elemIndex);
    }


    ///
    /// element 'symbol'
    ///

    private Object _textSymbol = null;

    public Object getSymbol() {
        return _textSymbol;
    }

    public void setSymbol(Object val) {
        _textSymbol = val;
    }

    protected Elem elemSymbol = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("      <a href=\"/stocks/#{item.symbol}\">");
            } else {
                _buf.append("      <a");
                if (! _attrs.has("href")) _buf.append(" href=\"/stocks/#{item.symbol}\"");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textSymbol != null) {
                echo(_textSymbol);
                return;
            }
            _buf.append("AAPL");
        }

        public void etag() {
            _buf.append("</a>\n");
        }

        public Object text() {
            return _textSymbol;
        }

        public void text(Object obj) {
            _textSymbol = obj;
        }

    };

    protected void elemSymbol(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemSymbol() {
        elemSymbol(elemSymbol);
    }


    ///
    /// element 'url'
    ///

    private Object _textUrl = null;

    public Object getUrl() {
        return _textUrl;
    }

    public void setUrl(Object val) {
        _textUrl = val;
    }

    protected Elem elemUrl = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("      <a href=\"#{item.url}\">");
            } else {
                _buf.append("      <a");
                if (! _attrs.has("href")) _buf.append(" href=\"#{item.url}\"");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textUrl != null) {
                echo(_textUrl);
                return;
            }
            _buf.append("Apple, Inc.");
        }

        public void etag() {
            _buf.append("</a>\n");
        }

        public Object text() {
            return _textUrl;
        }

        public void text(Object obj) {
            _textUrl = obj;
        }

    };

    protected void elemUrl(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemUrl() {
        elemUrl(elemUrl);
    }


    ///
    /// element 'price'
    ///

    private Object _textPrice = null;

    public Object getPrice() {
        return _textPrice;
    }

    public void setPrice(Object val) {
        _textPrice = val;
    }

    protected Elem elemPrice = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("      <strong>");
            } else {
                _buf.append("      <strong");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textPrice != null) {
                echo(_textPrice);
                return;
            }
            _buf.append("1000.00");
        }

        public void etag() {
            _buf.append("</strong>\n");
        }

        public Object text() {
            return _textPrice;
        }

        public void text(Object obj) {
            _textPrice = obj;
        }

    };

    protected void elemPrice(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemPrice() {
        elemPrice(elemPrice);
    }


    ///
    /// element 'change'
    ///

    private Object _textChange = null;

    public Object getChange() {
        return _textChange;
    }

    public void setChange(Object val) {
        _textChange = val;
    }

    protected Elem elemChange = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("     <td class=\"minus\">");
            } else {
                _buf.append("     <td");
                if (! _attrs.has("class")) _buf.append(" class=\"minus\"");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textChange != null) {
                echo(_textChange);
                return;
            }
            _buf.append("-10.00");
        }

        public void etag() {
            _buf.append("</td>\n");
        }

        public Object text() {
            return _textChange;
        }

        public void text(Object obj) {
            _textChange = obj;
        }

    };

    protected void elemChange(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemChange() {
        elemChange(elemChange);
    }


    ///
    /// element 'ratio'
    ///

    private Object _textRatio = null;

    public Object getRatio() {
        return _textRatio;
    }

    public void setRatio(Object val) {
        _textRatio = val;
    }

    protected Elem elemRatio = new Elem() {

        public void stag() {
            if (_attrs.isEmpty()) {
                _buf.append("     <td class=\"minus\">");
            } else {
                _buf.append("     <td");
                if (! _attrs.has("class")) _buf.append(" class=\"minus\"");
                _attrs.appendTo(_buf);
                _buf.append(">");
            }
        }

        public void cont() {
            if (_textRatio != null) {
                echo(_textRatio);
                return;
            }
            _buf.append("-1.00");
        }

        public void etag() {
            _buf.append("</td>\n");
        }

        public Object text() {
            return _textRatio;
        }

        public void text(Object obj) {
            _textRatio = obj;
        }

    };

    protected void elemRatio(Elem e) {
        e.stag();
        e.cont();
        e.etag();
    }

    protected void elemRatio() {
        elemRatio(elemRatio);
    }


    // for test
    public static void main(String[] args) throws Exception {
        System.out.print(new StocksHtml().render());
    }


}
