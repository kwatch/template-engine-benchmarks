package kwartzite;

import java.util.Map;
import java.util.HashMap;
import kwartzite.Util;

public class Attrs {

    private HashMap<String, String> _map = new HashMap<String, String>();

    public String get(String name) {
        return _map.get(name);
    }

    public Attrs set(String name, String value) {
        _map.put(name, value);
        return this;
    }

    public Attrs del(String name) {
        _map.remove(name);
        return this;
    }

    public boolean has(String name) {
        return _map.containsKey(name);
    }

    public boolean isEmpty() {
        return _map.isEmpty();
    }

    public Attrs clear() {
        _map.clear();
        return this;
    }

    public Attrs appendTo(StringBuilder buf) {
        for (Map.Entry<String,String> kv: _map.entrySet()) {
            String k = kv.getKey(), v = kv.getValue();
            if (v != null) {
                buf.append(' ').append(k).append("=\"").append(Util.escapeHtml(v.toString())).append('"');
            }
        }
        return this;
    }

}
