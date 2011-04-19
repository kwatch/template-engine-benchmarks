package kwartzite;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Dom {


    abstract public static class Node {

        abstract public void accept(Visitor visitor);

        public String toHtml() {
            StringBuilder buf = new StringBuilder();
            _toHtml(buf);
            return buf.toString();
        }

        abstract void _toHtml(StringBuilder buf);

    }


    public static class Element extends Node {

        private String _tag;
        private Map<String, String> _attrs;
        private List<Node> _children;

        public Element(String tag) {
            _tag = tag;
            _attrs = new HashMap<String, String>();
            _children = new ArrayList<Node>();
        }

        public String tag() {
            return _tag;
        }

        public List<Node> children() {
            return _children;
        }

        public Node child(int index) {
            if (index < 0) index = _children.size() + index;
            return _children.get(index);
        }

        public Element add(Node node) {
            if (node != null) _children.add(node);
            return this;
        }

        public Element addText(String str) {
            if (str != null) add(new Text(str));
            return this;
        }

        public String attr(String name) {
            return _attrs.get(name);
        }

        public Element attr(String name, String value) {
            _attrs.put(name, value);
            return this;
        }

        public Element attr(String name, Object value) {
            _attrs.put(name, value == null ? null : value.toString());
            return this;
        }

        public Map<String, String> attrs() {
            return _attrs;
        }

        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        void _toHtml(StringBuilder buf) {
            if (_tag == null) {
                _appendChildren(buf);
            }
            else {
                buf.append("<").append(Util.escapeHtml(_tag));
                _appendAttrs(buf);
                buf.append(">");
                _appendChildren(buf);
                buf.append("</").append(_tag).append(">");
            }
        }

        void _appendChildren(StringBuilder buf) {
            for (Node node: _children) {
                node._toHtml(buf);
            }
        }

        void _appendAttrs(StringBuilder buf) {
            for (Map.Entry<String, String> kv: _attrs.entrySet()) {
                String k = kv.getKey(), v = kv.getValue();
                if (v != null) {
                    buf.append(' ').append(Util.escapeHtml(k)).append("=\"")
                                   .append(Util.escapeHtml(v)).append('"');
                }
            }
        }

    }


    public static class EmptyElement extends Element {

        public EmptyElement(String tag) {
            super(tag);
        }

        public Node child(int index) {
            throw new IndexOutOfBoundsException("can't get child from empty tag.");
            //return null;
        }

        public List<Node> children() {
            throw new IndexOutOfBoundsException("can't get children of empty tag.");
            //return null;
        }

        public Element add(Node node) {
            throw new IndexOutOfBoundsException("can't add child into empty tag.");
            //return null;
        }

        public Element addText(String str) {
            throw new IndexOutOfBoundsException("can't add child into empty tag.");
            //return null;
        }

        void _toHtml(StringBuilder buf) {
            buf.append("<").append(Util.escapeHtml(tag()));
            _appendAttrs(buf);
            buf.append(" />");
        }

    }


    public static class Text extends Node {

        String _str;

        public Text(String str) {
            _str = str == null ? "" : str;
        }

        public String str() {
            return _str;
        }

        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        public void _toHtml(StringBuilder buf) {
            buf.append(Util.escapeHtml(_str));
        }

    }


    public static Element elem(String tag) {
        return new Element(tag);
    }

    public static Element emptyElem(String tag) {
        return new EmptyElement(tag);
    }


    abstract public static class Visitor {

        public void visit(Node node) {
            node.accept(this);
        }

        public void visit(Element element) { };

        public void visit(Text element) { };

    }


    public static void main(String[] args) {
        Dom.Element e = Dom.elem(null).attr("class", "message").addText("WARNING!");
        e.add(Dom.elem("em").addText("<i>hello</i>").attr("title\"", "A&B"));
        System.out.print(e.toHtml());
    }


}
