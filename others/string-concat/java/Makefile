N = 100000

.SUFFIXES: .java .class

base = bench_strcat

.java.class:
	javac $<

bench:	$(base).class
	N=$(N) java -server $(base)

clean:
	rm -f *.class
