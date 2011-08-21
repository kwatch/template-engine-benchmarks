-- -*- coding: utf-8 -*-

---
--- $Release: $
--- $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
--- $License: Creative Commons Attribution (CC BY) $
---

benchmarker = require('benchmarker')

members = {"Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon"}


expected = null

fh, msg = io.open("../expected.out", "r")
if fh then
	expected = fh:read("*a")
	fh:close()
else
	print('*** error: ' .. msg)
end


function verify(out)
	if expected ~= out then
		print("*** expected == out: failed")
	end
end


loop = 0 + (os.getenv('N') or 100*1000)
debug = os.getenv('D') or false

bm = benchmarker.Benchmarker.new(loop)


function bench_empty(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		for j = 1, 20 do
			-- pass
		end
	end
end
bm:empty_task(bench_empty)


title = "str..str..str"
function bench_strcat1(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		buf = ""
		buf = buf .. "<table>\n"
		for j = 1, 20 do
			buf = buf .. "  <tr>\
    <td>" .. s1 .. "</td>\
    <td>" .. s2 .. "</td>\
    <td>" .. s3 .. "</td>\
    <td>" .. s4 .. "</td>\
    <td>" .. s5 .. "</td>\
  </tr>\n"
		end
		buf = buf .. "</table>\n"
	end
	if debug then verify(buf) end
end
bm:task(title, bench_strcat1)


title = "buf[#buf+1]=str;table.concat()"
function bench_strcat2(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		buf = {}
		buf[#buf+1] = "<table>\n"
		for j = 1, 20 do
			buf[#buf+1] = "  <tr>\
    <td>"; buf[#buf+1] = s1; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s2; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s3; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s4; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s5; buf[#buf+1] = "</td>\
  </tr>\n";
		end
		buf[#buf+1] = "</table>\n"
		out = table.concat(buf);
	end
	if debug then verify(buf) end
end
bm:task(title, bench_strcat2)


title = "buf[#buf+1]=str only (no concat)"
function bench_strcat3(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		buf = {}
		buf[#buf+1] = "<table>\n"
		for j = 1, 20 do
			buf[#buf+1] = "  <tr>\
    <td>"; buf[#buf+1] = s1; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s2; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s3; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s4; buf[#buf+1] = "</td>\
    <td>"; buf[#buf+1] = s5; buf[#buf+1] = "</td>\
  </tr>\n";
		end
		buf[#buf+1] = "</table>\n"
		--out = table.concat(buf);
	end
	--if debug then verify(buf) end
end
bm:task(title, bench_strcat3)


title = "table.insert();table.concat()"
function bench_strcat4(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		buf = {}
		table.insert(buf, "<table>\n")
		for j = 1, 20 do
			table.insert(buf, "  <tr>\
    <td>"); table.insert(buf, s1); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s2); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s3); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s4); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s5); table.insert(buf, "</td>\
  </tr>\n");
		end
		table.insert(buf, "</table>\n")
		out = table.concat(buf);
	end
	if debug then verify(buf) end
end
bm:task(title, bench_strcat4)


title = "table.insert() only (no concat)"
function bench_strcat5(n)
	local m = members
	local s1, s2, s3, s4, s5 = m[1], m[2], m[3], m[4], m[5]
	local out
	for i = 1, n do
		buf = {}
		table.insert(buf, "<table>\n")
		for j = 1, 20 do
			table.insert(buf, "  <tr>\
    <td>"); table.insert(buf, s1); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s2); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s3); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s4); table.insert(buf, "</td>\
    <td>"); table.insert(buf, s5); table.insert(buf, "</td>\
  </tr>\n");
		end
		table.insert(buf, "</table>\n")
		--out = table.concat(buf);
	end
	--if debug then verify(buf) end
end
bm:task(title, bench_strcat5)



loop = 10*1000


function main()
	bm:run()
end


main()
