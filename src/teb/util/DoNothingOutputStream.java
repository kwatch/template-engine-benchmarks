package teb.util;

import java.io.*;

public class DoNothingOutputStream extends java.io.OutputStream {

    @Override
    public void write(int b) throws IOException {
    }

    public void write(byte[] bs) throws IOException {
    }

    public void write(byte b[], int off, int len) throws IOException{
    }

}
