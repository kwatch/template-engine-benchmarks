package kwartzite;

import java.util.Map;

abstract public class AbstractTemplate implements Template {

    abstract public void createDocument(StringBuilder buf);

    public void initDocument() {
    }

    public void context(Map<String, Object> values) {
    }

    public String render(Map<String, Object> values) {
        context(values);
        return render();
    }

    public String render() {
        StringBuilder buf = new StringBuilder();
        createDocument(buf);
        return buf.toString();
    }

}