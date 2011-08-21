.SUFFIXES: .exe .cpp

N=100000
C=3

.cpp.exe:
	g++ -o $@ $<

bench:	bench_strcat.exe
	N=$(N) C=$(C) ./bench_strcat.exe

clean:
	rm -f *.exe
