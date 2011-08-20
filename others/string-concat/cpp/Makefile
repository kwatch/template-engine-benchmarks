.SUFFIXES: .exe .cpp

.cpp.exe:
	g++ -o $@ $<

bench:	bench_strcat.exe
	./bench_strcat.exe

clean:
	rm -f *.exe