# -*- coding: utf-8 -*-

###
### $Release: $
### $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
### $License: Creative Commons Attribution (CC BY) $
###

from __future__ import with_statement

import sys, os, time
try:
    from cStringIO import StringIO
except ImportError:
    from io import StringIO

try:
    xrange
except NameError:
    xrange = range

from benchmarker import Benchmarker

members = ["Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon",]
s1, s2, s3, s4, s5 = members


with open('../expected.out') as f:
    expected = f.read()

def verify(out):
    assert expected == out


loop = int(os.getenv('N') or 100*1000)
if os.getenv('C'):
    cycle = int(os.getenv('C'))
else:
    cycle = 1


for bm in Benchmarker(cycle=cycle):
    print("## loop = %s" % loop)

    with bm.empty():
        for i in xrange(loop):
            for j in xrange(20):
                pass


    with bm("list.append()"):
        for i in xrange(loop):
            buf = []
            buf.append('''<table>\n''')
            for j in xrange(20):
                buf.append('''  <tr>
    <td>'''); buf.append(s1); buf.append('''</td>
    <td>'''); buf.append(s2); buf.append('''</td>
    <td>'''); buf.append(s3); buf.append('''</td>
    <td>'''); buf.append(s4); buf.append('''</td>
    <td>'''); buf.append(s5); buf.append('''</td>
  </tr>\n''');
            buf.append('''</table>\n''')
            out = "".join(buf)
    verify(out)


    with bm("append()"):
        for i in xrange(loop):
            buf = []; append = buf.append
            append('''<table>\n''')
            for j in xrange(20):
                append('''  <tr>
    <td>'''); append(s1); append('''</td>
    <td>'''); append(s2); append('''</td>
    <td>'''); append(s3); append('''</td>
    <td>'''); append(s4); append('''</td>
    <td>'''); append(s5); append('''</td>
  </tr>\n''');
            append('''</table>\n''')
            out = "".join(buf)
    verify(out)


    with bm("list.extend()"):
        for i in xrange(loop):
            buf = []
            buf.extend(('''<table>\n''',))
            for j in xrange(20):
                buf.extend(('''  <tr>
    <td>''', s1, '''</td>
    <td>''', s2, '''</td>
    <td>''', s3, '''</td>
    <td>''', s4, '''</td>
    <td>''', s5, '''</td>
  </tr>\n''',))
            buf.extend(('''</table>\n''',))
            out = "".join(buf)
    verify(out)


    with bm("extend()"):
        for i in xrange(loop):
            buf = []; extend = buf.extend
            extend(('''<table>\n''',))
            for j in xrange(20):
                extend(('''  <tr>
    <td>''', s1, '''</td>
    <td>''', s2, '''</td>
    <td>''', s3, '''</td>
    <td>''', s4, '''</td>
    <td>''', s5, '''</td>
  </tr>\n''',))
            extend(('''</table>\n''',))
            out = "".join(buf)
    verify(out)


    with bm("extend() only (no join)"):
        for i in xrange(loop):
            buf = []; extend = buf.extend
            extend(('''<table>\n''',))
            for j in xrange(20):
                extend(('''  <tr>
    <td>''', s1, '''</td>
    <td>''', s2, '''</td>
    <td>''', s3, '''</td>
    <td>''', s4, '''</td>
    <td>''', s5, '''</td>
  </tr>\n''',))
            extend(('''</table>\n''',))
            #out = "".join(buf)
    #verify(out)


    with bm("extend()+str()"):
        for i in xrange(loop):
            buf = []; extend = buf.extend
            extend(('''<table>\n''',))
            for j in xrange(20):
                extend(('''  <tr>
    <td>''', str(s1), '''</td>
    <td>''', str(s2), '''</td>
    <td>''', str(s3), '''</td>
    <td>''', str(s4), '''</td>
    <td>''', str(s5), '''</td>
  </tr>\n''',))
            extend(('''</table>\n''',))
            out = "".join(buf)
    verify(out)


    with bm("_str=str;extend()+_str()"):
        _str = str
        for i in xrange(loop):
            buf = []; extend = buf.extend
            extend(('''<table>\n''',))
            for j in xrange(20):
                extend(('''  <tr>
    <td>''', _str(s1), '''</td>
    <td>''', _str(s2), '''</td>
    <td>''', _str(s3), '''</td>
    <td>''', _str(s4), '''</td>
    <td>''', _str(s5), '''</td>
  </tr>\n''',))
            extend(('''</table>\n''',))
            out = "".join(buf)
    verify(out)


    with bm("buf[-1:] = ()"):
        for i in xrange(loop):
            buf = [""]
            buf[-1:] = ('''<table>\n''', "")
            for j in xrange(20):
                buf[-1:] = ('''  <tr>
    <td>''', s1, '''</td>
    <td>''', s2, '''</td>
    <td>''', s3, '''</td>
    <td>''', s4, '''</td>
    <td>''', s5, '''</td>
  </tr>\n''', "")
            buf[-1:] = ('''</table>\n''', "")
            out = "".join(buf)
    verify(out)


    with bm("StringIO.write()"):
        for i in xrange(loop):
            buf = StringIO()
            write = buf.write
            write('''<table>\n''')
            for j in xrange(20):
                write('''  <tr>
    <td>'''); write(s1); write('''</td>
    <td>'''); write(s2); write('''</td>
    <td>'''); write(s3); write('''</td>
    <td>'''); write(s4); write('''</td>
    <td>'''); write(s5); write('''</td>
  </tr>\n''');
            write('''</table>\n''')
            out = buf.getvalue()
    verify(out)
