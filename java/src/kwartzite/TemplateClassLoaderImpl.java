package kwartzite;

import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import com.sun.tools.javac.Main;
import kwartzite.Template;
import kwartzite.TemplateClassLoader;

public class TemplateClassLoaderImpl implements TemplateClassLoader {

    private String _filedir;
    private String _javadir;
    private String _classdir;
    private String _classpath;
    private URL[] _urls;
    private ClassLoader _class_loader;
    private PrintWriter _err = new PrintWriter(System.err);
    private boolean _debug = false;

    public TemplateClassLoaderImpl(String filedir, String javadir, String classdir, String classpath) throws RuntimeException {
        _filedir = filedir;
        _javadir = javadir;
        _classdir = classdir;
        _classpath = classpath;
        reset();
    }

    public void setErr(PrintWriter err) {
        _err = err;
    }

    public void setDebug(boolean debug) {
        _debug = debug;
    }

    public void reset() {
        String path = _classdir;
        String url = path.endsWith(".jar") ? "jar:"  + path + "!/" :
                     path.endsWith("/")    ? "file:" + path        :
                                             "file:" + path + "/";
        try {
            _urls = new URL[] {new URL(url)};
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException("'" + url + "': invalid url.", ex);
        }
        _class_loader = null;
    }

    public String convertName(String filepath) {
        return filepath.replace("/", ".").replace(".html", "Page");
    }

    protected int compile(String filepath) {
        String classpath = _classpath + ":" + _classdir;
        String[] args = new String[] {"-classpath", classpath, "-d", _classdir, filepath};
        int error_code = error_code = com.sun.tools.javac.Main.compile(args, _err);
        return error_code;
    }

    protected void compileFiles(String filepath) {
        String html_java  = _classdir + "/" + filepath.replace(".html", "Html.java");
        //String html_class = _classdir + "/" + filepath.replace(".html", "Html.class");
        compile(html_java);
        String page_java  = _javadir  + "/" + filepath.replace(".html", "Page.java");
        //String page_class = _classdir + "/" + filepath.replace(".html", "Page.class");
        compile(page_java);
    }

    public Class<Template> loadFile(String filepath) throws RuntimeException {
        if (_debug) compileFiles(filepath);
        String classname = convertName(filepath);
        return loadClass(classname);
    }

    @SuppressWarnings("unchecked")
    public Class<Template> loadClass(String classname) throws RuntimeException {
        if (_class_loader == null) {
            _class_loader = new URLClassLoader(_urls);
        }
        try {
            return (Class<Template>)_class_loader.loadClass(classname);
        }
        catch (ClassNotFoundException ex) {
            throw new RuntimeException("'" + classname + "': class not found.", ex);
        }
    }

    public static void main(String[] args) throws Exception {
        java.util.List<String> members = java.util.Arrays.asList(new String[] {"Haruhi", "Mikuru", "Yuki"});
        java.util.Map<String, Object> context = new java.util.HashMap<String, Object>();
        context.put("members", members);
        TemplateClassLoader loader = new TemplateClassLoaderImpl("templates/file", "templates/java", "tmp/classes", ".");
        loader.setDebug(true);
        java.io.BufferedReader sysin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        while (true) {
            System.out.println("-------------------");
            String msg = sysin.readLine();
            loader.reset();
            Class<Template> klass = loader.loadFile("sample/Sample1.html");
            Template t = klass.newInstance();
            String output = t.render(context);
            System.out.print(output);
        }
        //loader = null;
        //System.gc();
    }

}
