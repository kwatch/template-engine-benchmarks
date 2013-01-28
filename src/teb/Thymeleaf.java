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

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Thymeleaf extends _BenchBase {

    TemplateEngine engine;

    public Thymeleaf() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "error");
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        engine.setTemplateResolver(resolver);
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        IContext ctx = new Context(Locale.getDefault(), params);
        while (--ntimes >= 0) {
            if (ntimes == 0) engine.process("templates/stocks.thymeleaf.html", ctx, w1);
            else engine.process("templates/stocks.thymeleaf.html", ctx, w0);
        }
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        IContext ctx = new Context(Locale.getDefault(), params);
        Writer w1 = new OutputStreamWriter(o1);
        Writer w0 = new OutputStreamWriter(o0);
        while (--ntimes >= 0) {
            if (ntimes == 0) engine.process("templates/stocks.thymeleaf.html", ctx, w1);
            else engine.process("templates/stocks.thymeleaf.html", ctx, w0);
        }
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        IContext ctx = new Context(Locale.getDefault(), params);
        Writer w1 = new StringWriter(1024 * 10);
        Writer w0 = new StringWriter(1024 * 10);
        while (--ntimes >= 0) {
            if (ntimes == 0) engine.process("templates/stocks.thymeleaf.html", ctx, w1);
            else engine.process("templates/stocks.thymeleaf.html", ctx, w0);
        }

        return w1.toString();
    }

    public static void main(String[] args) {
        new Thymeleaf().run();
    }

}
