JAVADIR=../../java

copy:
	rm templates/stocks.vm;   ln $(JAVADIR)/templates/stocks.vm templates
	rm templates/stocks.html; ln $(JAVADIR)/templates/stocks.html templates
	rm velocity.properties;   ln $(JAVADIR)/velocity.properties .
	rm src/Stock.java;        ln $(JAVADIR)/src/Stock.java src
	rm src/StocksHtml.java;   ln $(JAVADIR)/src/StocksHtml.java src
	rm src/StocksPage.java;   ln $(JAVADIR)/src/StocksPage.java src
	rm src/kwartzite/*.java;  ln $(JAVADIR)/src/kwartzite/*.java src/kwartzite
