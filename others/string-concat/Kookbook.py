from __future__ import with_statement
import sys, os

kookbook.default = 'all'

@recipe
@spices('-n loop: loop time (default 100*1000)',
        '-c cycle: cycle to repeat (default 3)')
def all(c, *args, **kwargs):
    loop  = int(kwargs.get('n', 100*1000))
    cycle = int(kwargs.get('c', 3))
    ldir = '../logs'
    mkdir_p('logs')
    langs = ['cpp', 'java', 'javascript', 'perl', 'php', 'lua', 'python', 'ruby', 'go', ]
    alternatives = {
        'ruby':   ('rbx', ),
        'python': ('pypy', ),
        'lua':    ('luajit', ),
    }
    for lang in langs:
        print()
        print("============================== " + lang)
        with chdir(lang):
            system_f(c%"make N=$(loop) C=$(cycle) 2>&1 | tee $(ldir)/$(lang).log")
            for bin in alternatives.get(lang, ()):
                print()
                print("------------------------------ " + bin)
                system_f(c%"make N=$(loop) C=$(cycle) CMD=$(bin) 2>&1 | tee $(ldir)/$(bin).log")


data = r"""
node.js (0.5.4):        0.467           # 0.067
g++ (4.2.1):            0.778
java (1.6.0_26):        1.046           # 0.810
pypy (1.6):             1.061
php (5.3.6):            1.591
luajit (2.0.0-b8):      1.587
perl (5.12.1):          1.736
lua (5.1.4):            1.835
python (3.2.1):         1.847
rubinius (1.2.4):       2.059
go (6g r57.2):          2.846
ruby (1.9.2p290):       4.723
"""

data = r"""
javascript:      0.318
perl:    0.850
php:     2.620
python:  3.036
ruby:    2.839
"""

@recipe
def report(c):
    import yaml
    ydata = yaml.load(data)
    rank = []
    for lang, sec in ydata.items():
        rank.append((lang, sec, float(100*1000) / sec))
        rank.sort(key=lambda t: t[-1], reverse=True)
    for lang, sec, point in rank:
        print("%s\t%.1f" % (lang, point))


#### * report (recipe=report)
tsv = r"""
node.js (0.5.4)	214132.8
g++ (4.2.1)	128534.7
java (1.6.0_26)	95602.3
pypy (1.6)	94250.7
luajit (2.0.0-b8)	63012.0
php (5.3.6)	62853.6
perl (5.12.1)	57603.7
lua (5.1.4)	54495.9
python (3.2.1)	54141.9
rubinius (1.2.4)	48567.3
go (6g r57.2)	35137.0
ruby (1.9.2p290)	21173.0
"""
