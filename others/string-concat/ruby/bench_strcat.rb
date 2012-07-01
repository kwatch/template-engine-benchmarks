# -*- coding: utf-8 -*-

###
### $Release: $
### $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
### $License: Creative Commons Attribution (CC BY) $
###

require './benchmarker'

members = ["Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon"]
#s1, s2, s3, s4, s5 = members

loop  = (ENV['N'] || 100*1000).to_i
cycle = (ENV['C'] || 3).to_i

$expected = File.open('../expected.out') {|f| f.read }

def verify(out)
  $expected == out or raise "$expected == out: failed"
end

puts "## loop = #{loop}"
puts

Benchmarker.new(:cycle=>cycle) do |bm|

  bm.empty_task do
    for i in 1..loop
      for j in 1..20
        # pass
      end
    end
  end

  out = nil

  bm.task "String#<<" do
    s1, s2, s3, s4, s5 = members
    for i in 1..loop
      buf = ""
      buf << "<table>\n"
      for j in 1..20
        buf << "  <tr>
    <td>" << s1 << "</td>
    <td>" << s2 << "</td>
    <td>" << s3 << "</td>
    <td>" << s4 << "</td>
    <td>" << s5 << "</td>
  </tr>\n"
      end
      buf << "</table>\n"
      out = buf
    end
  end
  verify(out)

  bm.task "String#<< + \#{}" do
    s1, s2, s3, s4, s5 = members
    for i in 1..loop
      buf = ""
#      buf << %Q`<table>\n`
#      for j in 1..20
#        buf << %Q`  <tr>
#    <td>#{s1}</td>
#    <td>#{s2}</td>
#    <td>#{s3}</td>
#    <td>#{s4}</td>
#    <td>#{s5}</td>
#  </tr>\n`
#      end
#      buf << %Q`</table>\n`
      buf << "<table>\n"
      for j in 1..20
        buf << "  <tr>
    <td>#{s1}</td>
    <td>#{s2}</td>
    <td>#{s3}</td>
    <td>#{s4}</td>
    <td>#{s5}</td>
  </tr>\n"
      end
      buf << "</table>\n"
      out = buf
    end
  end
  verify(out)

  bm.task "Array#push + join" do
    s1, s2, s3, s4, s5 = members
    for i in 1..loop
      buf = []
      buf.push("<table>\n")
      for j in 1..20
        buf.push("  <tr>
    <td>", s1, "</td>
    <td>", s2, "</td>
    <td>", s3, "</td>
    <td>", s4, "</td>
    <td>", s5, "</td>
  </tr>\n")
      end
      buf.push("</table>\n")
      out = buf.join()
    end
  end
  verify(out)

  bm.task "Array#<< + join" do
    s1, s2, s3, s4, s5 = members
    for i in 1..loop
      buf = []
      buf << "<table>\n"
      for j in 1..20
        buf << "  <tr>
    <td>" << s1 << "</td>
    <td>" << s2 << "</td>
    <td>" << s3 << "</td>
    <td>" << s4 << "</td>
    <td>" << s5 << "</td>
  </tr>\n"
      end
      buf << "</table>\n"
      out = buf.join()
    end
  end
  verify(out)

  bm.task "Array#push only (no join)" do
    s1, s2, s3, s4, s5 = members
    for i in 1..loop
      buf = []
      buf.push("<table>\n")
      for j in 1..20
        buf.push("  <tr>
    <td>", s1, "</td>
    <td>", s2, "</td>
    <td>", s3, "</td>
    <td>", s4, "</td>
    <td>", s5, "</td>
  </tr>\n")
      end
      buf.push("</table>\n")
      #out = buf.join()
    end
  end
  #verify(out)

end
