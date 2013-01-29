==============================================================================
              JAVA TEMPLATE ENGINE BENCHMARKER v1.0
==============================================================================

This is a benchmark program for a collection of Java template engines

You need apache ant to run the program, just type "ant" and it will start. 
This program accept parameters to configure how to run the benchmark. There
are four arguments for general settings:

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

The arguments should be passed in by property setting. e.g

   ant -Dout=w \
       -Dwtimes=10 \
       -Dntimes=1000 \
       -Dbuf=false \
       -Drythm.new=true

    
In addition for Rythm engine specifically you can specify whether to launch it 
with new API use "rythm.new" parameter. default value is false.

Should you have any suggestion or comments, please raise issue via 
https://github.com/greenlaw110/template-engine-benchmarks/issues