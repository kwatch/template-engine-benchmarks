/*
 * $Copyright: copyright(c) 2007-2011 kuwata-lab.com all rights reserved. $
 * $License: Creative Commons Attribution (CC BY) $
 */
package teb;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

import com.floreysoft.jmte.*;
import teb.model.Stock;


public class JMTE extends _BenchBase {

    Engine engine;
    private String template = "templates/stocks.jmte.html";
    
    public JMTE() {
        engine = new Engine();
    }
    
    @Override
    public void execute(boolean warmUp, Writer w0, Writer w1, int ntimes, List<Stock> items) throws Exception {
        String output = null;
        String tmpl = readTemplateFile(template);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", items);
        while (--ntimes >= 0) {
            if (!warmUp && ntimes == 0) output = engine.transform(tmpl, model);
            else engine.transform(tmpl, model);
        }
        if (!warmUp) {
            w1.write(output);
        }
    }

    public static void main(String[] args) throws Exception {
        new JMTE().run();
    }
    
    private String readTemplateFile(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("template file " + fileName + " does not exists");
        }
        
        // code comes from http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
        FileInputStream stream = new FileInputStream(new File(fileName));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }

}
