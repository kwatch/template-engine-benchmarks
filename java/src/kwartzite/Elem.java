package kwartzite;

import kwartzite.Attrs;

public class Elem {

    /** start tag */
    public void stag() { }

    /** content */
    public void cont() { }

    /** end tag */
    public void etag() { }

    /** set content */
    public void text(Object obj) { }

    /** element */
    public void elem() {
        stag();
        cont();
        etag();
    }

    //protected Map<String, String> _attrs = new HashMap<String, String>();
    protected Attrs _attrs = new Attrs();

    /** set attribute name and value */
    public Elem attr(String key, String value) {
        _attrs.set(key, value);
        return this;
    };

    /** set attribute name and value */
    public Elem attr(String key, Object value) {
        _attrs.set(key, value == null ? null : value.toString());
        return this;
    };

    /** get attribute value */
    public String attr(String key) {
        return _attrs.get(key);
    };

    /** set "checked" attribute if cond is true */
    public Elem checked(boolean cond) {
        _attrs.set("checked", cond ? "checked" : null);
        return this;
    }

    /** set "selected" attribute if cond is true */
    public Elem selected(boolean cond) {
        _attrs.set("selected", cond ? "selected" : null);
        return this;
    }

    /** set "disabled" attribute if cond is true */
    public Elem disabled(boolean cond) {
        _attrs.set("disabled", cond ? "disabled" : null);
        return this;
    }

    /*
    public Attrs value(Object value) {
        _attrs.set("value", value);
        return this;
    }
    */

}
