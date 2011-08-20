.SUFFIXES: .go .6

.go.6:
	6g $<

.6.out:
	6l -o $@ $<

all:	bench_strcat.out
	./bench_strcat.out

clean:
	rm -f *.out