==============================================================================
              JAVA TEMPLATE ENGINE BENCHMARKER v1.0
==============================================================================

This is a benchmark program for a collection of Java template engines:
  * stringbuilder - the baseline. manually created page using StringBulder
  * freemarker - v2.3.19
  * velocity - v1.7
  * rythm - v1.0-b2-SNAPSHOT
  * httl - 1.0.5
  * beetl - 1.2Beta
  * jamon - 2.3
  * jangod - no version info
  * thymeleaf - 2.0.15

You need apache ant to run the program, just type "ant" and it will start. 
This program accept parameters to configure how to run the benchmark:

 1. out: specify how to output rendering result, could be:
   * os: output to OutputStream (default);
   * w:  output to Writer
   * s:  return String

 2. wtimes: specify number of warm up loops.
    default: 100

 3. ntimes: specify number of benchmark loops.
    default: 10000

 4. buf: specify whether use BufferedWriter or OS
    default: true
    
 5. rythm.new: for rythm engine only. specify whether use new API to
    run rythm benchmark or not. Default: false

The arguments should be passed in using property setting. e.g

   ant -Dout=w \
       -Dwtimes=10 \
       -Dntimes=1000 \
       -Dbuf=false \
       -Drythm.new=true

Should you have any suggestion or comments, please raise issue via 
https://github.com/greenlaw110/template-engine-benchmarks/issues