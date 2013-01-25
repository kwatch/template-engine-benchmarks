/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.util.List;
import java.io.*;

public class StocksStringBuilderBench extends Bench {

    @Override
    public void execute(boolean warmUp, Writer writer, int ntimes, List<Stock> items) throws Exception {
        String output = null;
        while (--ntimes >= 0) {
            if (!warmUp && ntimes == 0) output = render(items);
            else render(items);
        }
        if (!warmUp) {
            writer.write(output);
        }
    }

    public String render(List<Stock> items) {
        StringBuilder sb = new StringBuilder(1024);  // or new StringBuilder(8000)
        sb.append(""
            + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
        int i = 0;
        for (Stock item: items) {
            String klass = ++i % 2 == 1 ? "odd" : "even";
            sb.append(""
            + "    <tr class=\""); sb.append(klass); sb.append("\">\n"
            + "     <td style=\"text-align: center\">"); sb.append(i); sb.append("</td>\n"
            + "     <td>\n"
            + "      <a href=\"/stocks/"); sb.append(item.getSymbol()); sb.append("\">"); sb.append(item.getSymbol()); sb.append("</a>\n"
            + "     </td>\n"
            + "     <td>\n"
            + "      <a href=\""); sb.append(item.getUrl()); sb.append("\">"); sb.append(item.getName()); sb.append("</a>\n"
            + "     </td>\n"
            + "     <td>\n"
            + "      <strong>"); sb.append(item.getPrice()); sb.append("</strong>\n"
            + "     </td>\n");
            if (item.getChange() < 0.0) {
                sb.append(""
            + "     <td class=\"minus\">"); sb.append(item.getChange()); sb.append("</td>\n"
            + "     <td class=\"minus\">"); sb.append(item.getRatio()); sb.append("</td>\n");
            }
            else {
                sb.append(""
            + "     <td>"); sb.append(item.getChange()); sb.append("</td>\n"
            + "     <td>"); sb.append(item.getRatio()); sb.append("</td>\n");
            }
            sb.append("    </tr>\n");
        }
        sb.append(""
        + "   </tbody>\n"
        + "  </table>\n"
        + "\n"
        + " </body>\n"
        + "</html>\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        new StocksStringBuilderBench().run();
    }

}
