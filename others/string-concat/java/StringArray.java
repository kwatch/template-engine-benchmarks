/**
 * $Release: $
 * $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
 * $License: MIT License $
 */

import java.util.ArrayList;
import java.util.Collection;


/**
 *  replacement of StringBuilder
 *
 */
public class StringArray {
    private int      _length   = 0;
    private int      _count    = 0;
    private int      _capacity;
    private String[] _array;

    public int getCount() { return _count; }

    public StringArray put(int index, String val) {
        _array[index] = val;
        return this;
    }

    public StringArray(int capacity) {
        _capacity = capacity;
        _array = new String[capacity];
    }

    public StringArray() {
        this(256);
    }

    public char[] toChars() {
        String[] arr = _array;
        char[] chars = new char[_length];
        int count = _count;
        int pos = 0;
        for (int i = 0; i < count; i++) {
            String s = arr[i];
            int len = s.length();
            s.getChars(0, len, chars, pos);
            pos += len;
        }
        return chars;
    }

    public String toString() {
        return new String(toChars());
    }

    private void _add(String s) {
        //assert s != null;
        _length += s.length();
        int count = _count;
        if (count == _capacity) _expand();
        _array[count] = s;
        _count++;
    }

    private void _expand() {
        int new_capacity = _capacity * 2;
        String[] new_array = new String[new_capacity];
        String[] old_array = _array;
        int n = _count;
        for (int i = 0; i < n; i++) {
            new_array[i] = old_array[i];
        }
        _array = new_array;
        _capacity = new_capacity;
    }

    public StringArray add(String val) {
        if (val != null) {
            _add(val);
            //_length += val.length();
            //_array.add(val);
        }
        return this;
    }

    public StringArray add(Object val) {
        if (val != null) {
            _add(val.toString());
        }
        return this;
    }

    public StringArray add(int val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(short val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(long val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(double val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(float val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(char val) {
        _add(String.valueOf(val));
        return this;
    }

    public StringArray add(boolean val) {
        _add(String.valueOf(val));
        return this;
    }

}