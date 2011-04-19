======
README
======

Servlet version of template engine benchmarks for Java.
This is mainly aimed to measure JSP performance.


Setup
=====

    ### set $CATALINA_HOME (mandatory)
    $ export CATALINA_HOME=/usr/local/java/tomcat7
    ### copy files
    $ cp -pr template-engine-benchmarks/java_servlet $CATALINA_HOME/webapps
    $ cd $CATALINA_HOME/webapps/java_servlet/WEB-INF/
    $ ls -F
    build.xml               src/                    velocity.properties
    jsp/                    templates/              web.xml
    ### get *.jar files
    $ mkdir -p lib
    $ cp $CATALINA_HOME/webapps/examples/WEB-INF/lib/standard.jar lib
    $ cp $CATALINA_HOME/webapps/examples/WEB-INF/lib/jstl.jar lib
    $ wget -P lib http://archive.apache.org/dist/velocity/engine/1.7/velocity-1.7.jar
    $ wget -P lib http://archive.apache.org/dist/velocity/engine/1.7/velocity-1.7-dep.jar
    ### compile
    $ ant
    $ ls -F
    build.xml               lib/                    velocity.properties
    classes/                src/                    web.xml
    jsp/                    templates/
    $ ls classes/
    Stock.class                             StocksStringBuilderServlet.class
    StocksJspEscapedServlet.class           StocksVelocityServlet.class
    StocksJspServlet.class


Benchmark
=========

    $ ab -n 10000 -c 4 http://localhost:8080/java_servlet/stocks-jsp
    $ ab -n 10000 -c 4 http://localhost:8080/java_servlet/stocks-velocity
    $ ab -n 10000 -c 4 http://localhost:8080/java_servlet/stocks-stringbuilder
    $ ab -n 10000 -c 4 http://localhost:8080/java_servlet/stocks-kwartzite

Or:

    $ siege -b -r 2500 -c 4 http://localhost:8080/java_servlet/stocks-jsp
    $ siege -b -r 2500 -c 4 http://localhost:8080/java_servlet/stocks-velocity
    $ siege -b -r 2500 -c 4 http://localhost:8080/java_servlet/stocks-stringbuilder
    $ siege -b -r 2500 -c 4 http://localhost:8080/java_servlet/stocks-kwartzite


Benchmark Result Example
========================

OS:: Mac OS X 10.6 Snow Leopard
Java:: 1.6.0_24
AppServer:: Tomcat 7.0.8
Hardware:: CPU Intel Core 2 Duo 2GHz, Mem 4GB

Average::

    Name             Time per request (mean)
    ----------------------------------
    JSP                  1.356 [ms]
    Velocity             1.208 [ms]
    StringBuilder        0.882 [ms]
    Kwartzite            0.959 [ms]


This shows that Velocity is more than 10 percent faster than JSP.
This results contain times of network, I/O, and so son.
Removing them, Velocity seems about 30 percent faster than JSP.

    Tv = 1.208     # velocity
    Tj = 1.356     # JSP
    Tb = 0.882     # StringBuilder

    X  = ?         # time for Network, I/O, ...
    Rv = ?         # rendering time with Velocity
    Rb = ?         # rendering time with StringBuilder
    Rj = ?         # rendering time with JSP

    X + Rv = Tv = 1.208                         # ... (1)
    X + Rb = Tb = 0.882                         # ... (2)
    X + Rj = Tj = 1.356                         # ... (3)

    Rb / Rv = 868.0 / 3032.4                    # from other benchmark
    Rb = (868.0 / 3032.4) * Rv = 0.286*Rv       # ... (4)

    Rv - Rb = Tv - Tb = 1.208 - 0.882 = 0.326   # from (1) and (2)
    Rv - Rb = Rv - 0.286*Rv = 0.714*Rv          # from (4)
    0.714*Rv = 0.326
    Rv = 0.326 / 0.714 = 0.457
    Rb = 0.286*Rv = 0.286*0.457 = 0.131         # from (4)

    X = Tv - Rv = 1.208 - 0.457 = 0.751         # from (1)
      = Tb - Rb = 0.882 - 0.131 = 0.751         # from (2)

    Rj = Tj - X = 1.356 - 0.751 = 0.605         # from (3)

    (Rj/Rv - 1) * 100 = (0.605 / 0.457 - 1) * 100
                      = 32.3 [%]

Conclustion: Velocity seems about 30 percent faster than JSP.
