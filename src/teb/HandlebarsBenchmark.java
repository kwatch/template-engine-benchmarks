package teb;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import teb.model.Stock;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Parser;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.ConcurrentMapTemplateCache;
import com.github.jknack.handlebars.cache.TemplateCache;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;

public class HandlebarsBenchmark extends _BenchBase {

    private Handlebars handlebars;

    public HandlebarsBenchmark() {
        this.handlebars = fileHandleBars();
    }

    private Handlebars fileHandleBars() {

        // The handlebars template engine cache checks if the file has changed.
        // This is unfair as half the engines just do simple string expansion.
        // So we do a simple cache that assumes the file never changes.
        // Also notice that we have to cache the template loader as the template
        // loader is actually a bottleneck since it always checks if the file exists.
        File templateDir = new File("templates/");
        boolean flag = getFlag("handlebars.defaultTemplateLoader");
        FileTemplateLoader templateLoader = flag ? new FileTemplateLoader(templateDir) : new FileTemplateLoader(
                templateDir) {
            Map<String, TemplateSource> cache = new ConcurrentHashMap<String, TemplateSource>();

            @Override
            public TemplateSource sourceAt(String uri) throws IOException {
                TemplateSource t = cache.get(uri);
                if (t == null) {
                    t = super.sourceAt(uri);
                    cache.put(uri, t);
                }
                return t;
            }
        };
        templateLoader.setSuffix("");
        if (getFlag("handlebars.defaultCache")) {
            return new Handlebars(templateLoader).with(new ConcurrentMapTemplateCache());
        }
        else {
            return new Handlebars(templateLoader).with(new TemplateCache() {

                Map<String, Template> cache = new ConcurrentHashMap<String, Template>();

                @Override
                public void clear() {
                }

                @Override
                public void evict(TemplateSource template) {
                }

                @Override
                public Template get(TemplateSource template, Parser parser) throws IOException {
                    Template t = cache.get(template.filename());
                    if (t == null) {
                        t = parser.parse(template);
                        cache.put(template.filename(), t);
                    }
                    return t;
                }
            });
        }

    }

    private boolean getFlag(String name) {
        String prop = System.getProperty(name, null);
        boolean flag = prop != null && !prop.equalsIgnoreCase("false");
        return flag;
    }

    @Override
    protected void execute(Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        Map<String, Object> root = initContextData(items);
        render(w0, w1, ntimes, root);
    }

    @Override
    public void execute(OutputStream o0, OutputStream o1, int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new OutputStreamWriter(o0);
        Writer w1 = new OutputStreamWriter(o1);
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        execute(w0, w1, ntimes, items);
    }

    @Override
    protected String execute(int ntimes, List<Stock> items) throws Exception {
        Writer w0 = new StringWriter();
        Writer w1 = new StringWriter();
        if (_BenchBase.bufferMode.get()) {
            w0 = new BufferedWriter(w0);
            w1 = new BufferedWriter(w1);
        }
        execute(w0, w1, ntimes, items);
        return w1.toString();
    }

    private Map<String, Object> initContextData(List<Stock> items) {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("items", items);
        return root;
    }

    private void render(Writer w0, Writer w1, int ntimes, Map<String, Object> model) throws IOException {
        while (--ntimes >= 0) {
            // I'm almost positive most of the engines are using some sort of
            // cache.
            // Particularly the mustache implementation because moving the
            // compile statement
            // out of the loop does not change execution time.
            // So its really sort of a stupid test to test the compile phase as
            // it
            // really is just testing the cache of the templating engine.
            // That being said handlebars file template loader is actually slow
            // with out the above hacks (top of file).
            // This is a legitimate problem handlebars should fix.
            Template template = handlebars.compile("stocks.handlebars.html");
            if (ntimes == 0) {
                template.apply(model, w1);
                w1.close();
            } else {
                template.apply(model, w0);
            }
        }
    }

    public static void main(String[] args) {
        new HandlebarsBenchmark().run();
    }

}
