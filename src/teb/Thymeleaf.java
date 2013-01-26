/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import teb.model.Stock;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Thymeleaf extends _BenchBase {

    TemplateEngine engine;

    public Thymeleaf() {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        engine.setTemplateResolver(resolver);
    }

    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        IContext ctx = new Context(Locale.getDefault(), params);
        while (--ntimes >= 0) {
            if (!warmUp && ntimes == 0) engine.process("templates/stocks.thymeleaf.html", ctx, w1);
            else engine.process("templates/stocks.thymeleaf.html", ctx, w0);
        }
    }

    public static void main(String[] args) {
        new Thymeleaf().run();
    }

}
