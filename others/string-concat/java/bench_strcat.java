/**
 * $Release: $
 * $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
 * $License: Creative Commons Attribution (CC BY) $
 */

//import benchmarker.Benchmarker;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class bench_strcat {

    static String[] members() {
        return new String[] {"Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon"};
    }

    static String toStr(String s) {
        return s == null ? "" : s;
    }

    static String expected = null;
    static {
        String filename = "../expected.out";
        try {
            InputStream stream = new FileInputStream(filename);
            Reader input = new InputStreamReader(stream);
            Reader reader = new BufferedReader(input);
            StringBuilder buf = new StringBuilder();
            int ch;
            while ((ch = reader.read()) >= 0) {
                buf.append((char)ch);
            }
            expected = buf.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static boolean __debug = false;

    static void verify(String out) {
        if (! __debug) return;
        if (out == null) {
            throw new RuntimeException("out != null: failed.");
        }
        if (! out.equals(expected)) {
            System.out.println("*** expected: " + expected);
            System.out.println("*** out:      " + out);
            throw new RuntimeException("expected == out: failed.");
        }
    }

    public static void main(String[] args) {
        int loop = 100*1000;
        int cycle = 3;
        String s;
        s = System.getenv("N");
        if (s != null) loop = Integer.parseInt(s);
        s = System.getenv("C");
        if (s != null) cycle = Integer.parseInt(s);
        s = System.getenv("D");
        if (s != null) __debug = true;
        Benchmarker bm = new Benchmarker(loop, cycle);
        //
        bm.emptyTask(new Benchmarker.Task() {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    while (--loop >= 0) {
                        for (int j = 0; j < 20; j++) {
                            // pass
                        }
                    }
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder(4*1024)") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder(4*1024);
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                    if (out.length() > 4*1024) {
                        System.err.println("*** out.length=" + out.length());
                    }
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder(4*1024) w/o toString()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder(4*1024);
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        //out = buf.toString();
                    }
                    //verify(out);
                    //if (out.length() > 4*1024) {
                    //    System.err.println("*** out.length=" + out.length());
                    //}
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder();
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder() w/o toString()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder();
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        //out = buf.toString();
                    }
                    //verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder(4*1024) with toStr()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder(4*1024);
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(toStr(s1)).append("</td>\n" +
                                       "    <td>").append(toStr(s2)).append("</td>\n" +
                                       "    <td>").append(toStr(s3)).append("</td>\n" +
                                       "    <td>").append(toStr(s4)).append("</td>\n" +
                                       "    <td>").append(toStr(s5)).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                    if (out.length() > 4*1024) {
                        System.err.println("*** out.length=" + out.length());
                    }
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuilder() with toStr()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuilder buf = new StringBuilder();
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(toStr(s1)).append("</td>\n" +
                                       "    <td>").append(toStr(s2)).append("</td>\n" +
                                       "    <td>").append(toStr(s3)).append("</td>\n" +
                                       "    <td>").append(toStr(s4)).append("</td>\n" +
                                       "    <td>").append(toStr(s5)).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuffer(4*1024)") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuffer buf = new StringBuffer(4*1024);
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                    if (out.length() > 4*1024) {
                        System.err.println("*** out.length=" + out.length());
                    }
                }
            });
        //
        bm.task(new Benchmarker.Task("StringBuffer()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringBuffer buf = new StringBuffer();
                        buf.append("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.append("  <tr>\n" +
                                       "    <td>").append(s1).append("</td>\n" +
                                       "    <td>").append(s2).append("</td>\n" +
                                       "    <td>").append(s3).append("</td>\n" +
                                       "    <td>").append(s4).append("</td>\n" +
                                       "    <td>").append(s5).append("</td>\n" +
                                       "  </tr>\n");
                            // pass
                        }
                        buf.append("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("ArrayList(256).add() (no join)") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        ArrayList<String> buf = new ArrayList<String>(256);
                        buf.add("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.add("  <tr>\n" +
                            "    <td>"); buf.add(s1); buf.add("</td>\n" +
                            "    <td>"); buf.add(s2); buf.add("</td>\n" +
                            "    <td>"); buf.add(s3); buf.add("</td>\n" +
                            "    <td>"); buf.add(s4); buf.add("</td>\n" +
                            "    <td>"); buf.add(s5); buf.add("</td>\n" +
                            "  </tr>\n");
                            // pass
                        }
                        buf.add("</table>\n");
                        //out = buf.toString();
                    }
                    //verify(out);
                }
            });
         //
        bm.task(new Benchmarker.Task("StringArray().toString()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    String out = null;
                    while (--loop >= 0) {
                        StringArray buf = new StringArray();
                        buf.add("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.add("  <tr>\n" +
                            "    <td>"); buf.add(s1); buf.add("</td>\n" +
                            "    <td>"); buf.add(s2); buf.add("</td>\n" +
                            "    <td>"); buf.add(s3); buf.add("</td>\n" +
                            "    <td>"); buf.add(s4); buf.add("</td>\n" +
                            "    <td>"); buf.add(s5); buf.add("</td>\n" +
                            "  </tr>\n");
                            // pass
                        }
                        buf.add("</table>\n");
                        out = buf.toString();
                    }
                    verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("StringArray().toChars()") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    //String out = null;
                    char[] chars = null;
                    while (--loop >= 0) {
                        StringArray buf = new StringArray();
                        buf.add("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.add("  <tr>\n" +
                            "    <td>"); buf.add(s1); buf.add("</td>\n" +
                            "    <td>"); buf.add(s2); buf.add("</td>\n" +
                            "    <td>"); buf.add(s3); buf.add("</td>\n" +
                            "    <td>"); buf.add(s4); buf.add("</td>\n" +
                            "    <td>"); buf.add(s5); buf.add("</td>\n" +
                            "  </tr>\n");
                            // pass
                        }
                        buf.add("</table>\n");
                        //out = buf.toString();
                        chars = buf.toChars();
                    }
                    //verify(out);
                }
            });
        //
        bm.task(new Benchmarker.Task("StringArray() (no join)") {
                public void run(int loop) {
                    String[] m = members();
                    String s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
                    //String out = null;
                    char[] chars = null;
                    while (--loop >= 0) {
                        StringArray buf = new StringArray();
                        buf.add("<table>\n");
                        for (int j = 0; j < 20; j++) {
                            buf.add("  <tr>\n" +
                            "    <td>"); buf.add(s1); buf.add("</td>\n" +
                            "    <td>"); buf.add(s2); buf.add("</td>\n" +
                            "    <td>"); buf.add(s3); buf.add("</td>\n" +
                            "    <td>"); buf.add(s4); buf.add("</td>\n" +
                            "    <td>"); buf.add(s5); buf.add("</td>\n" +
                            "  </tr>\n");
                            // pass
                        }
                        buf.add("</table>\n");
                        //out = buf.toString();
                        //chars = buf.toChars();
                    }
                    //verify(out);
                }
            });
        //
        boolean reverse = System.getenv("R") != null;
        bm.run(reverse);
    }

}