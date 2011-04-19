#!/usr/bin/env ruby

class MainApp

  BASE_URL = 'http://localhost:8080/'
  PATHS = [
    'java_servlet/stocks-jsp',
    'java_servlet/stocks-velocity',
    'java_servlet/stocks-stringbuilder',
    'java_servlet/stocks-kwartzite',
  ]

  def initialize(requests=nil, concurrency=nil, cycle=nil)
    @requests    = requests    || 10000
    @concurrency = concurrency || 4
    @cycle       = 5
    @sleep       = 30
  end

  attr_accessor :requests, :concurrencty

  def run
    warm_up()
    (1..@cycle).each do |i|
      run_benchmark(i)
    end
  end

  private

  def shell(command)
    puts "$ #{command}"
    system command
  end

  def invoke_ab(n, c, url, outfile)
    shell "ab -n #{n} -c #{c} #{url} > #{outfile}"
  end

  def warm_up
    PATHS.each do |path|
      invoke_ab(100, @concurrency, BASE_URL + path, '/dev/null')
    end
  end

  def run_benchmark(i)
    PATHS.each do |path|
      log_name = "#{File.basename(path)}.#{i}.log"
      invoke_ab(@requests, @concurrency, BASE_URL + path, log_name)
      puts "## sleeping #{@sleep} seconds..."
      sleep @sleep
    end
  end

end


if __FILE__ == $0
  MainApp.new.run()
end
