package kwartzite;

import java.io.PrintWriter;

public interface TemplateClassLoader {

    public void setErr(PrintWriter err);
    public void setDebug(boolean debug);
    public void reset();
    public Class<Template> loadFile(String filepath) throws RuntimeException;

}
