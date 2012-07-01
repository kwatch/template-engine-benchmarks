======
README
======

This is a collection of benchmark program for template engines in Java.


How to
======

First, download Apache Velocity jar files::

    $ wget http://archive.apache.org/dist/velocity/engine/1.7/velocity-1.7.jar
    $ wget http://archive.apache.org/dist/velocity/engine/1.7/velocity-1.7-dep.jar
    $ wget http://archive.apache.org/dist/velocity/engine/1.6.4/velocity-1.6.4.jar
    $ wget http://archive.apache.org/dist/velocity/engine/1.6.4/velocity-1.6.4-dep.jar
    $ wget http://archive.apache.org/dist/velocity/engine/1.5/velocity-1.5.jar
    $ wget http://archive.apache.org/dist/velocity/engine/1.5/velocity-dep-1.5.jar
    $ mkdir -p lib
    $ mv velocity-*.jar lib

Second, compile src/*.java files by Apache Ant.

    $ ant compile

Finally, start benchmarking by Ant or 'bench.rb' (Ruby script).

    $ ant benchmarks                    ## simple
    ## or
    $ ruby bench.rb 2> /dev/null        ## detailed


Example Result
==============

Environment: Mac OS X 10.6 Snow Leopard, Java 1.6.0_24, CPU Intel Core 2 Duo 2GHz, Mem 4GB

    $ ruby bench.rb 2> /dev/null
    ## results               #1       #2       #3       #4       #5   avg(msec)
    velocity17             3090     3004     3092     3020     2956     3032.40
    velocity16             3181     3072     3091     3094     3172     3122.00
    velocity15             3678     3775     3611     3661     3799     3704.80
    stringbuilder           840      879      851      841      929      868.00
    kwartzite              1946     1983     1904     1987     1875     1939.00
    
    ## matrix           avg(msec)      [A]      [B]      [C]      [D]      [E]
    [A] velocity17        3032.40   100.0%   103.0%   122.2%    28.6%    63.9%
    [B] velocity16        3122.00    97.1%   100.0%   118.7%    27.8%    62.1%
    [C] velocity15        3704.80    81.9%    84.3%   100.0%    23.4%    52.3%
    [D] stringbuilder      868.00   349.4%   359.7%   426.8%   100.0%   223.4%
    [E] kwartzite         1939.00   156.4%   161.0%   191.1%    44.8%   100.0%

This shows that::

* Newer version of Velocity is a little faster than older version.
* But Velocity is three or four times slower than StringBuilder.


Tips
====

* You can edit 'bench.properties' and change number of times to render template.
* Each benchmark program prints rendered output into 'output.*' files.
