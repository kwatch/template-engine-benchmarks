#!/usr/bin/env ruby
# -*- coding: utf-8 -*-

require 'open3'

BenchTarget = Struct.new(:name, :classname, :classpath)

class MainApp

  def initialize(argv=nil)
    @argv = argv || ARGV
  end

#--
#  def parse(io)
#    results = []
#    target = nil
#    ARGF.each_line do |line|
#      if line =~ /^(\w+):/
#        target = $1
#      elsif line =~ /\[java\] ntimes: (\d+), real time: (\d+)\(msec\)/
#        results << [target, $2.to_i]
#        target = nil
#      end
#    end
#    return results
#  end
#++

  TARGETS = [
    BenchTarget.new('velocity17', 'StocksVelocityBench', ['lib/velocity-1.7.jar', 'lib/velocity-1.7-dep.jar']),
    BenchTarget.new('velocity16', 'StocksVelocityBench', ['lib/velocity-1.6.4.jar', 'lib/velocity-1.6.4-dep.jar']),
    BenchTarget.new('velocity15', 'StocksVelocityBench', ['lib/velocity-1.5.jar', 'lib/velocity-dep-1.5.jar']),
    BenchTarget.new('stringbuilder', 'StocksStringBuilderBench', []),
    BenchTarget.new('kwartzite', 'StocksKwartziteBench', []),
  ]

  def run
    names, results = run_benchmarks()
    averages = report_results(names, results)
    puts separator()
    report_matrix(names, averages)
  end

  def separator
    #"## ----------------------------------------------------------------------"
    ""
  end

  def run_benchmarks
    results = {}
    names = []
    (1..5).each do |n|
      $stderr.puts "## ##{n}"
      TARGETS.each do |target|
        name = target.name
        unless results[name]
          results[name] = []
          names << name
        end
        msec = java(target)
        results[name] << msec
      end
      $stderr.puts separator
    end
    return names, results
  end

  def java(target)
    t = target
    classpath ||= t.classpath
    classpath.unshift 'classes' unless classpath.include?('classes')
    command = "java -server -classpath #{classpath.join(':')} #{t.classname}"
    $stderr.puts "$ #{command} > output.#{t.name}"
    #system command
    output = result = nil
    Open3.popen3(command) do |stdin, stdout, stderr|
      stdin.close()
      output = stdout.read()
      result = stderr.read()
    end
    File.open("output.#{t.name}", 'wb') {|f| f.write(output) }
    $stderr.puts result
    result =~ /ntimes: (\d+), real time: (\d+)\(msec\)/  or
      raise "#{result.inspect}: unexpected result"
    return $2.to_i
  end

  def report_results(names, results)
    count = results[names.first].length
    headers = ["## results"] + (1..count).collect {|i| "##{i}" } + ["avg(msec)"]
    format = "%-18s" + "%9s" * count + "%12s"
    puts format % headers
    averages = {}
    names.each do |name|
      count = results[name].length
      total = 0; results[name].each {|x| total += x }
      avg = total.to_f / count
      averages[name] = avg
      format = "%-18s" + "%9s" * count + "%12.2f"
      values = [name] + results[name] + [avg]
      puts format % values
    end
    return averages
  end

  def report_matrix(names, averages)
    #pairs = averages.sort_by {|name, avg| avg }
    pairs = names.collect {|name| [name, averages[name]] }
    count = pairs.length
    format = "%-20s" + "%9s" + " %8s" * count
    headers = ["## matrix", "avg(msec)"] + ('A'..'Z').to_a[0, count].collect {|x| "[#{x}]"}
    puts format % headers
    label = 'A'
    format = "%-20s" + '%9.2f' + '%8.1f%%' * count
    pairs.each do |name, avg|
      values = ["[#{label}] #{name}", avg] + pairs.collect {|_, v| 100.0 * v / avg }
      puts format % values
      label = label.succ
    end
  end

end


if __FILE__ == $0
  MainApp.new.run()
end
