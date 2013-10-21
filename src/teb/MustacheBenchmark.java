package teb;

import java.io.*;
import java.util.*;

import teb.model.Stock;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheBenchmark extends _BenchBase {

    static MustacheFactory mustacheFactory;

    public MustacheBenchmark() {
	mustacheFactory = new DefaultMustacheFactory(new File("templates/"));
    }

    @Override
    public void execute(Writer w0, Writer w1, int ntimes, List<Stock> items)
	    throws Exception {
	Map root = initContextData(items);
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

    private Map initContextData(List<Stock> items) {
	Map root = new HashMap();
	root.put("items", items);
	return root;
    }

    private void render(Writer w0, Writer w1, int ntimes, Map root)
	    throws IOException {
	while (--ntimes >= 0) {
	    Mustache template = mustacheFactory.compile("stocks.mustache.html");

	    if (ntimes == 0) {
		template.execute(w1, root);
		w1.close();
	    } else
		template.execute(w0, root);
	}
    }

    public static void main(String[] args) {
	new MustacheBenchmark().run();
    }

}
