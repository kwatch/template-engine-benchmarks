// -*- coding: utf-8 -*-

///
/// $Release: $
/// $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
/// $License: Creative Commons Attribution (CC BY) $
///

var assert = require("assert");
var fs = require("fs");

var fd_dev_null = fs.openSync('/dev/null', 'w');

var Benchmarker = require('./benchmarker').Benchmarker;

function members() {
  return ["Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon"];
}

var expected = fs.readFileSync('../expected.out', 'utf8');
var expectedLength = expected.length;

function verify(out) {
  //return;
  assert.equal(out.length, expectedLength);
}
if (process.env.D) {
  verify = function verify(out) {
    assert.equal(out, expected);
  };
}

var loop = 100*1000;
if (process.env.N) loop  = + process.env.N;
var cycle = 3;
if (process.env.C) cycle = + process.env.C;

var bm = new Benchmarker(loop, cycle);
bm.width = 45;


bm.emptyTask(function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  while (--loop >= 0) {
    for (var j = 0; j < 20; j++) {
      // pass
    }
  }
});


bm.task("a.push(s,s,s);a.join", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [];
    buf.push("<table>\n");
    for (var j = 0; j < 20; j++) {
      buf.push("  <tr>\n\
    <td>", s1, "</td>\n\
    <td>", s2, "</td>\n\
    <td>", s3, "</td>\n\
    <td>", s4, "</td>\n\
    <td>", s5, "</td>\n\
  </tr>\n");
    }
    buf.push("</table>\n");
    out = buf.join("");
  }
  verify(out);
});


bm.task("a.push(s,s,s) only (no join)", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [];
    buf.push("<table>\n");
    for (var j = 0; j < 20; j++) {
      buf.push("  <tr>\n\
    <td>", s1, "</td>\n\
    <td>", s2, "</td>\n\
    <td>", s3, "</td>\n\
    <td>", s4, "</td>\n\
    <td>", s5, "</td>\n\
  </tr>\n");
    }
    buf.push("</table>\n");
    //out = buf.join("");
  }
  //verify(out);
});


bm.task("a.push(s,s,s);a.join (w. Buffer)", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [];
    buf.push("<table>\n");
    for (var j = 0; j < 20; j++) {
      buf.push("  <tr>\n\
    <td>", s1, "</td>\n\
    <td>", s2, "</td>\n\
    <td>", s3, "</td>\n\
    <td>", s4, "</td>\n\
    <td>", s5, "</td>\n\
  </tr>\n");
    }
    buf.push("</table>\n");
    out = buf.join("");
    new Buffer(out, 'utf8');    // !!!
  }
  verify(out);
});


bm.task("a.push(s,s,s);a.join (w. /dev/null)", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [];
    buf.push("<table>\n");
    for (var j = 0; j < 20; j++) {
      buf.push("  <tr>\n\
    <td>", s1, "</td>\n\
    <td>", s2, "</td>\n\
    <td>", s3, "</td>\n\
    <td>", s4, "</td>\n\
    <td>", s5, "</td>\n\
  </tr>\n");
    }
    buf.push("</table>\n");
    out = buf.join("");
    fs.writeSync(fd_dev_null, out);    // !!!
  }
  verify(out);
});


bm.task("a[i++]=s;a.join", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [], i = 0;
    buf[i++] = "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf[i++] = "  <tr>\n\
    <td>"; buf[i++] = s1; buf[i++] = "</td>\n\
    <td>"; buf[i++] = s2; buf[i++] = "</td>\n\
    <td>"; buf[i++] = s3; buf[i++] = "</td>\n\
    <td>"; buf[i++] = s4; buf[i++] = "</td>\n\
    <td>"; buf[i++] = s5; buf[i++] = "</td>\n\
  </tr>\n";
    }
    buf[i++] = "</table>\n";
    out = buf.join("");
  }
  verify(out);
});


bm.task("a[++i]=s;a.join", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [""], i = 0;
    buf[++i] = "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf[++i] = "  <tr>\n\
    <td>"; buf[++i] = s1; buf[++i] = "</td>\n\
    <td>"; buf[++i] = s2; buf[++i] = "</td>\n\
    <td>"; buf[++i] = s3; buf[++i] = "</td>\n\
    <td>"; buf[++i] = s4; buf[++i] = "</td>\n\
    <td>"; buf[++i] = s5; buf[++i] = "</td>\n\
  </tr>\n";
    }
    buf[++i] = "</table>\n";
    out = buf.join("");
  }
  verify(out);
});


bm.task("a[a.length]=s;a.join", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = [];
    buf[buf.length] = "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf[buf.length] = "  <tr>\n\
    <td>"; buf[buf.length] = s1; buf[buf.length] = "</td>\n\
    <td>"; buf[buf.length] = s2; buf[buf.length] = "</td>\n\
    <td>"; buf[buf.length] = s3; buf[buf.length] = "</td>\n\
    <td>"; buf[buf.length] = s4; buf[buf.length] = "</td>\n\
    <td>"; buf[buf.length] = s5; buf[buf.length] = "</td>\n\
  </tr>\n";
    }
    buf[buf.length] = "</table>\n";
    out = buf.join("");
  }
  verify(out);
});


bm.task("buf+=s;buf+=s;buf+=s", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "";
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>"; buf += s1; buf += "</td>\n\
    <td>"; buf += s2; buf += "</td>\n\
    <td>"; buf += s3; buf += "</td>\n\
    <td>"; buf += s4; buf += "</td>\n\
    <td>"; buf += s5; buf += "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
    verify(out);
  }
  verify(out);
});


bm.task("locals.buf+=s+s+s", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var locals = {buf: ""};
    locals.buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      locals.buf += "  <tr>\n\
    <td>" + s1 + "</td>\n\
    <td>" + s2 + "</td>\n\
    <td>" + s3 + "</td>\n\
    <td>" + s4 + "</td>\n\
    <td>" + s5 + "</td>\n\
  </tr>\n";
    }
    locals.buf += "</table>\n";
    out = locals.buf;
    verify(out);
  }
  verify(out);
});


bm.task("buf+=s+s+s", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "";
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + s1 + "</td>\n\
    <td>" + s2 + "</td>\n\
    <td>" + s3 + "</td>\n\
    <td>" + s4 + "</td>\n\
    <td>" + s5 + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
    verify(out);
  }
  verify(out);
});


bm.task("buf+=s+s+s (w. Buffer)", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "";
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + s1 + "</td>\n\
    <td>" + s2 + "</td>\n\
    <td>" + s3 + "</td>\n\
    <td>" + s4 + "</td>\n\
    <td>" + s5 + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
    verify(out);
    new Buffer(out, 'utf8');    // !!!
  }
  verify(out);
});


bm.task("buf+=s+s+s (w. /dev/null)", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "";
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + s1 + "</td>\n\
    <td>" + s2 + "</td>\n\
    <td>" + s3 + "</td>\n\
    <td>" + s4 + "</td>\n\
    <td>" + s5 + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
    verify(out);
    fs.writeSync(fd_dev_null, out, 0);    // !!!
  }
  verify(out);
});


bm.task("'str'+((v=value)==null?'':v)+'str'", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "", v;
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + ((v=s1)==null?'':v) + "</td>\n\
    <td>" + ((v=s2)==null?'':v) + "</td>\n\
    <td>" + ((v=s3)==null?'':v) + "</td>\n\
    <td>" + ((v=s4)==null?'':v) + "</td>\n\
    <td>" + ((v=s5)==null?'':v) + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
  }
  verify(out);
});


function toStr(value) {
  return value == null ? "" : value;
}

bm.task("'str'+toStr(value)+'str'", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "";
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + toStr(s1) + "</td>\n\
    <td>" + toStr(s2) + "</td>\n\
    <td>" + toStr(s3) + "</td>\n\
    <td>" + toStr(s4) + "</td>\n\
    <td>" + toStr(s5) + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
  }
  verify(out);
});


bm.task("'str'+_toStr(value)+'str'", function(loop) {
  var m = members();
  var s1 = m[0], s2 = m[1], s3 = m[2], s4 = m[3], s5 = m[4];
  var out = "";
  while (--loop >= 0) {
    var buf = "", _toStr = toStr;
    buf += "<table>\n";
    for (var j = 0; j < 20; j++) {
      buf += "  <tr>\n\
    <td>" + _toStr(s1) + "</td>\n\
    <td>" + _toStr(s2) + "</td>\n\
    <td>" + _toStr(s3) + "</td>\n\
    <td>" + _toStr(s4) + "</td>\n\
    <td>" + _toStr(s5) + "</td>\n\
  </tr>\n";
    }
    buf += "</table>\n";
    out = buf;
  }
  verify(out);
});


bm.run();
