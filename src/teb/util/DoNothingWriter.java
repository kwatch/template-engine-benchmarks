package teb.util;

import java.io.*;

// coming from https://github.com/httl/httl-benchmark/blob/master/src/test/java/httl/test/util/DiscardWriter.java

public class DoNothingWriter extends Writer {

	@Override
	public Writer append(char c) throws IOException {
		return this;
	}

	@Override
	public Writer append(CharSequence csq, int start, int end)
			throws IOException {
		return this;
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		return this;
	}

	@Override
	public void write(char[] cbuf) throws IOException {
	}

	@Override
	public void write(int c) throws IOException {
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
	}

	@Override
	public void write(String str) throws IOException {
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {
	}

}