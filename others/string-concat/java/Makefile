N = 100000
C = 3

.SUFFIXES: .java .class

base = bench_strcat

.java.class:
	javac $<

bench:	$(base).class
	N=$(N) C=$(C) java -server $(base)

clean:
	rm -f *.class
