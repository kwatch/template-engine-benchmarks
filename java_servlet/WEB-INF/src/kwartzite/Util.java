package kwartzite;

public class Util {

    //public static final Object NULL = new Object();
    public static final String NULL = new String("");

    public static String toStr(Object val) {
        return val == null ? "" : val.toString();
    }

    public static String toStr(String val) {
        return val == null ? "" : val;
    }


    static String[] ESCAPE_HTML = new String['>'+1];
    static {
        ESCAPE_HTML['&']  = "&amp;";
        ESCAPE_HTML['<']  = "&lt;";
        ESCAPE_HTML['>']  = "&gt;";
        ESCAPE_HTML['"']  = "&quot;";
        ESCAPE_HTML['\''] = "&#39;";
    }

    public static String escapeHtml(String s) {
        StringBuilder sb = null;
        String escaped = null;
        if (s == null) return "";
        for (int i = 0, n = s.length(); i < n; i++) {
            char ch = s.charAt(i);
            if (ch <= '>' && (escaped = ESCAPE_HTML[ch]) != null) {
                if (sb == null) {
                    sb = new StringBuilder();
                    sb.append(s.substring(0, i));
                }
                sb.append(escaped);
            }
            else {
                if (sb != null) sb.append(ch);
            }
        }
        return sb == null ? s : sb.toString();
    }

    public static String escapeHtml(char c) {
        String escaped;
        if (c <= '<' && (escaped = ESCAPE_HTML[c]) != null) {
            return escaped;
        }
        return String.valueOf(c);
    }

    public static String escapeHtml(int i) {
        return String.valueOf(i);
    }

    public static String escapeHtml(double f) {
        return String.valueOf(f);
    }

    public static String escapeHtml(boolean b) {
        return b ? "true" : "false";
    }

    public static String escapeHtml(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}