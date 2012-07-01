# -*- coding: utf-8 -*-

###
### $Release: $
### $Copyright: copyright(c) 2010-2011 kuwata-lab.com all rights reserved $
### $License: Public Domain $
###

import sys, os, re, time, gc
from os   import times as _os_times
from time import time  as _time_time

python2 = sys.version_info[0] == 2
python3 = sys.version_info[0] == 3

if python2:
    from StringIO import StringIO
if python3:
    basestring = str
    xrange = range
    from io import StringIO

__all__     = ('Benchmarker', )
__version__ = "$Release: 0.0.0 $".split(' ')[1]


class Format(object):

    def __init__(self):
        #: sets 'label_width' property.
        self.label_width  = 30
        #self.label       = '%-30s'
        #: sets 'time' property.
        self.time         = '%9.4f'
        #self.times       = '%9.4f %9.4f %9.4f %9.4f'
        #self.time_label  = '%9s'
        #self.times_label = '%9s %9s %9s %9s'

    def _get_label_width(self):
        #: returns '__label_with' attribute.
        return self.__label_width

    def _set_label_width(self, width):
        #: sets both '__label_width' and 'label' attributes.
        self.__label_width = width
        self.label = '%-' + str(width) + 's'

    label_width = property(_get_label_width, _set_label_width)

    def _get_time(self):
        #: returns '__time' attribute.
        return self.__time

    def _set_time(self, fmt):
        #: sets '__time', 'time_label', 'times' and 'times_label' attrs.
        sfmt = re.sub(r'\.\d+f', 's', fmt)   # ex. '%9.4f' -> '%9s'
        self.__time      = fmt
        self.time_label  = sfmt
        self.times       = ' '.join((fmt, fmt, fmt, fmt))
        self.times_label = ' '.join((sfmt, sfmt, sfmt, sfmt))

    time = property(_get_time, _set_time)


format = Format()


class Echo(object):

    def __init__(self, out):
        self._out = out
        self.prev = ''

    @classmethod
    def create_dummy(cls):
        #: returns Echo object with dummy I/O.
        return cls(StringIO())

    def flush(self):
        #: calls _out.flush() only if _out has 'flush' method.
        if hasattr(self._out, 'flush'):
            self._out.flush()

    def str(self, string):
        #: does nothing if argument is empty.
        if not string:
            return
        #: writes argument and keep it to prev attribute.
        self._out.write(string)
        self.prev = string

    __call__ = str

    def text(self, string):
        #: does nothing if argument is empty.
        if not string:
            return
        #: adds '\n' at the end of argument if it doesn't end with '\n'.
        if not string.endswith("\n"):
            string += "\n"
        #: writes argument and keep it to prev attribute.
        self.str(string)

    def separator(self):
        prev = self.prev
        #: prints an empty line if nothing is printed.
        if   not prev:               self.str("\n")
        #: prints nothing if prev is empty line.
        elif prev.endswith("\n\n"):  pass
        #: print an empty line elsewhere.
        elif prev.endswith("\n"):    self.str("\n")
        else:                        self.str("\n\n")

    def section_title(self, title, head1='user', head2='sys', head3='total', head4='real'):
        #: prints separator.
        self.separator()
        #: prints title and headers.
        self.str(format.label % title)
        self.str(format.times_label % (head1, head2, head3, head4))
        self.str("\n")

    def task_label(self, label):
        #: shrinks too long label.
        if len(label) > format.label_width:
            label = label[:format.label_width - 3] + '...'
        #: prints label.
        self.str(format.label % label)
        #: flushes output.
        self.flush()

    def task_times(self, user, sys, total, real):
        #: prints times.
        self.str(format.times % (user, sys, total, real))
        self.str("\n")

    def task_message(self, message):
        #: prints message instead of times.
        self.str(message + "\n")


echo       = Echo(sys.stdout)
echo_error = Echo(sys.stderr)


class Benchmarker(object):

    verbose = True

    def __init__(self, width=None, loop=1, cycle=1, extra=0, verbose=None):
        #: sets format.label_with if 'wdith' option is specified.
        if width:
            format.label_width = width
        #: sets 'loop', 'cycle', and 'extra' attributes.
        self.loop   = loop
        self.cycle  = cycle
        self.extra  = extra
        #: sets 'verbose' attribute if its option is specified.
        if verbose is not None:  self.verbose = verbose
        #
        self._setup("##")
        self.all_results = None
        #: creates Statistics object using STATISTICS variable.
        self.stats = STATISTICS()
        #: overrides by command-line option.
        global cmdopt
        if cmdopt.verbose is not None:  self.verbose = cmdopt.verbose
        if cmdopt.loop    is not None:  self.loop    = cmdopt.loop
        if cmdopt.cycle   is not None:  self.cycle   = cmdopt.cycle
        if cmdopt.extra   is not None:  self.extra   = cmdopt.extra

    def _setup(self, section_title):
        self._section_title = section_title
        self.results = []
        self._current_empty_result = None

    def __enter__(self):
        #: prints platform information.
        echo.text(self.platform())
        #: returns self.
        return self

    def __exit__(self, *args):
        #results = self.results[:]
        #results.sort(key=lambda x: x.real)
        results = self.results
        #: prints separator and ranking.
        echo.separator()
        echo.text(self.stats.ranking(results))
        #: prints separator and ratio matrix.
        echo.separator()
        echo.text(self.stats.ratio_matrix(results))

    def __call__(self, label):
        #: prints section title if called at the first time.
        is_first = not self.results and self._current_empty_result is None
        if is_first:
            echo.section_title(self._section_title)
        #: creates new Result object.
        result = Result(label)
        #: saves created Result object except that label is specified to skip in command-line.
        if not cmdopt.should_skip(label):
            self.results.append(result)
        #: returns Task object with Result object.
        #: passes current empty result to task object.
        return Task(result, loop=self.loop, _empty=self._current_empty_result)

    def empty(self):
        #: creates a task for empty loop and keeps it.
        task = self.__call__('(Empty)')
        self._current_empty_result = task.result
        #: created task should not be included in self.results.
        assert self.results[-1] is task.result
        self.results.pop()
        #: returns a Task object.
        return task

    def __iter__(self):
        #: calls _repeat_block().
        return self._repeat_block(self.cycle, self.extra, True)

    def repeat(self, cycle, extra=0):
        #: calls _repeat_block().
        return self._repeat_block(cycle, extra, False)

    def _repeat_block(self, cycle, extra, emulate_with_stmt):
        #: calls __enter__() if emulate_with_stmt is True.
        if emulate_with_stmt:
            self.__enter__()
        #: if cycle is 1 and extra is 0 then just behave like with-statement.
        if cycle == 1 and extra == 0:
            yield self
        else:
            #: replaces 'echo' object to stderr temporarily if verbose.
            #: replaces 'echo' object to dummy I/O temporarily if not verbose.
            global echo, echo_error
            echo_bkup = echo
            echo = self.verbose and echo_error or Echo.create_dummy()
            #: invokes block for 'cycle + 2*extra' times.
            self.all_results = []
            for i in xrange(cycle + 2 * extra):
                #: resets some properties for each repetition.
                self._setup("## (#%d)" % (i+1))
                #: keeps all results.
                self.all_results.append(self.results)
                yield self
            #: restores 'echo' object after block.
            echo = echo_bkup
            #: calculates average of results.
            avg_results = self._calc_average_results(self.all_results, extra)
            self.results = avg_results
            #: prints averaged results.
            self._echo_average_section(avg_results, extra, len(self.all_results))
        #: calls __eixt__() if emulate_with_stmt is True.
        if emulate_with_stmt:
            self.__exit__()

    def _calc_average_results(self, all_results, extra):
        #: prints min-max section title if extra is specified.
        if extra:
            #echo.section_title('## Remove min & max', 'min', '(#N)', 'max', '(#N)')
            echo.section_title('## Remove min & max', 'min', 'cycle', 'max', 'cycle')
        #: calculates average of results and returns it.
        avg_results = []
        if self.all_results:
            for i in xrange(len(all_results[0])):
                results = [ arr[i] for arr in all_results ]
                #: prints min-max section if extra is specified.
                if extra:
                    results = self._remove_min_and_max(results, extra)
                #
                result = Result.average(results)
                avg_results.append(result)
        return avg_results

    def _echo_average_section(self, avg_results, extra, n):
        #: prints average section title.
        title = extra and '## Average of %s (=%s-2*%s)' % (n-2*extra, n, extra) \
                      or  '## Average of %s' % n
        echo.section_title(title)
        #: prints averaged results.
        for r in avg_results:
            echo.task_label(r.label)
            echo.task_times(*r.to_tuple())

    def _remove_min_and_max(self, results, extra):
        #: removes min and max result.
        results  = results[:]
        id2index = dict([ (id(r), i) for i, r in enumerate(results) ])
        sorted   = results[:]
        sorted.sort(key=lambda r: r.real)
        s1, s2 = format.time, format.time_label
        fmt     = s1 + ' ' + s2 + ' ' + s1 + ' ' + s2 + '\n'
        label   = results[0].label
        indices = []
        for i in xrange(extra):
            r_min = sorted[i]
            r_max = sorted[-i-1]
            i_min = id2index[id(r_min)]
            i_max = id2index[id(r_max)]
            s_min = '(#%s)' % (i_min+1)
            s_max = '(#%s)' % (i_max+1)
            indices.extend((i_min, i_max))
            #: prints removed data.
            echo.task_label(label)
            echo.str(fmt % (r_min.real, s_min, r_max.real, s_max))
            label = ''
        indices.sort(reverse=True)
        for i in indices:
            del results[i]
        #: returns new results.
        return results

    def run(self, func, *args):   # **kwargs
        #: uses func doc string or name as label.
        label = func.__doc__ or func.__name__
        #: same as 'self.__call__(label).run(func)'.
        return self(label).run(func, *args)   # **kwargs

    def skip(self, label_or_func, message='(skipped)'):
        #: accepts label string or function object.
        if isinstance(label_or_func, basestring):
            label = label_or_func
        else:
            func = label_or_func
            label = func.__doc__ or func.__name__
        #: prints task label and message.
        echo.task_label(label)
        echo.task_message(message)

    @staticmethod
    def platform():
        #: returns platform information.
        buf = []
        a = buf.append
        a("## benchmarker:       release %s (for python)\n" % (__version__, ))
        a("## python platform:   %s %s\n" % (sys.platform, sys.version.splitlines()[1]))
        a("## python version:    %s\n"    % (sys.version.split(' ')[0], ))
        a("## python executable: %s\n"    % (sys.executable))
        return ''.join(buf)


class Result(object):

    def __init__(self, label):
        #: takes label argument.
        self.label = label
        self.user = self.sys = self.total = self.real = 0.0

    def _set(self, user, sys, total, real):
        #: sets times values as attributes.
        self.user  = user
        self.sys   = sys
        self.total = total
        self.real  = real
        #: returns self.
        return self

    def __repr__(self):
        #: returns represented string.
        name = self.__class__.__name__
        f = lambda x: '%.3f' % x
        return '<%s label=%r user=%s sys=%s total=%s real=%s>' % \
                  (name, self.label, f(self.user), f(self.sys), f(self.total), f(self.real))

    def to_tuple(self):
        #: returns a tuple with times.
        return (self.user, self.sys, self.total, self.real)

    @classmethod
    def average(cls, results):
        #: calculates averaged result from results.
        if not results:
            return None
        label = results[0].label
        avg = cls(label)
        for r in results:
            assert r.label == label
            avg.user  += r.user
            avg.sys   += r.sys
            avg.total += r.total
            avg.real  += r.real
        n = len(results)
        avg.user  /= n
        avg.sys   /= n
        avg.total /= n
        avg.real  /= n
        #: returns averaged result.
        return avg


class Task(object):

    def __init__(self, result, loop=1, _empty=None):
        #: takes a Result object, loop, and _empty result.
        self.result = result
        self.loop   = loop or 1
        self._empty = _empty

    def __enter__(self):
        #: prints task label.
        echo.task_label(self.result.label)
        #: starts full-GC.
        gc.collect()
        #: saves current timestamp.
        self._times = _os_times()
        self._time  = _time_time()
        #: returns self.
        return self

    def __exit__(self, *args):
        #: calculates user, sys, total and real times.
        time  = _time_time()
        times = _os_times()
        r = self.result
        r.user  = times[0] - self._times[0]
        r.sys   = times[1] - self._times[1]
        r.total = r.user + r.sys
        r.real  = time - self._time
        del self._times, self._time
        #: removes empty loop data if they are specified.
        if self._empty:
            r.user  -= self._empty.user
            r.sys   -= self._empty.sys
            r.total -= self._empty.total
            r.real  -= self._empty.real
        #: prints times.
        echo.task_times(*r.to_tuple())

    def run(self, func, *args):   # **kwargs
        #: just returns if task is specified to skip in command-line.
        if cmdopt.should_skip(self.result.label):
            return self
        #
        loop = self.loop
        #: calls __enter__() to simulate with-statement.
        self.__enter__()
        #: calls function with arguments.
        try:
            #: calls functions N times if 'loop' is specified.
            if loop > 1:
                for i in xrange(loop):
                    func(*args)   # **kwargs
            else:
                func(*args)       # **kwargs
        #: calls __exit__() to simulate with-statement.
        finally:
            self.__exit__(*sys.exc_info())
        #: returns self.
        return self

    def __iter__(self):
        #: just returns if task is specified to skip in command-line.
        if cmdopt.should_skip(self.result.label):
            return
        #
        loop = self.loop
        raised = False
        #: calls __enter__() to simulate with-statement.
        self.__enter__()
        #
        try:
            #: executes block for N times if 'loop' is specified.
            if loop > 1:
                for i in xrange(1, loop+1):
                    yield i
            #: executes block only once if 'loop' is not specified.
            else:
                yield 1
        except Exception:
            raised = True
        #: calls __exit__() to simulate with-statement.
        self.__exit__(*sys.exc_info())
        if raised:
            raise


class Statistics(object):

    KEY        = 'real'
    SORT       = True
    REVERSE    = False
    COMPENSATE = 0.0     # or -100.0

    def _sorted(self, results):
        #: not modify passed results.
        sorted = results[:]
        #: returns sorted results.
        sorted.sort(key=lambda x: getattr(x, self.KEY), reverse=self.REVERSE)
        return sorted

    def ranking(self, results):
        #: returns ranking as string.
        buf = []; b = buf.append
        ## header
        b(format.label % "## Ranking")
        b(format.time_label % "real")
        b("\n")
        ## body
        #: returns empty ranking if results is empty.
        if not results:
            return ''.join(buf)
        key = self.KEY
        sorted = self._sorted(results)
        fastest = sorted[self.REVERSE and -1 or 0]
        base = getattr(fastest, key)
        if self.SORT:  results = sorted
        for result in results:
            val   = getattr(result, key)
            ratio = 100.0 * base / val
            chart = '*' * int(round(ratio / 4))
            b(format.label % result.label)
            b(format.time % val)
            b(" (%5.1f%%) %s\n" % (ratio, chart))
        ##
        return ''.join(buf)

    def ratio_matrix(self, results):
        #: returns ratio matrix as string.
        buf = []; b = buf.append
        ## cell width
        if results:
            reals = [ result.real for result in results ]
            reals.sort()
            max_ratio = 100 * reals[-1] / reals[0]
            width = len(str(int(max_ratio))) + 2
            if width < 6: width = 6
        else:
            width = 6
        ## header
        b(format.label % "## Ratio Matrix")
        b(format.time_label % "real")
        fmt = "  %" + str(width) + "s"      # ex. " %7s "
        for i in xrange(len(results)):
            label = "[%02d]" % (i+1)
            b(fmt % label)
        b("\n")
        ## matrix
        #: returns empty ranking if results is empty.
        if not results:
            return ''.join(buf)
        key = self.KEY
        if self.SORT:  results = self._sorted(results)
        compensate = self.COMPENSATE
        fmt = " %" + str(width) + ".1f%%"   # ex. " %7.1f%%"
        for i, result in enumerate(results):
            label = "[%02d] %s" % (i+1, result.label)
            base = getattr(result, key)
            b(format.label % label)
            b(format.time % base)
            for r in results:
                ratio = 100.0 * getattr(r, key) / base + compensate
                b(fmt % ratio)
            b("\n")
        ##
        return ''.join(buf)


STATISTICS = Statistics


class CommandOption(object):

    def __init__(self):
        self.verbose = None
        self.loop    = None
        self.cycle   = None
        self.extra   = None
        self.exclude = None
        self.args    = []
        self._exclude_rexps = []
        self._include_rexps = []
        self._user_option_dict = {}
        self.__parser = None

    def __getitem__(self, name):
        #: returns user option value if exists.
        #: returns None if not exist.
        return self._user_option_dict.get(name, None)

    def __setitem__(self, name, value):
        #: sets user option value.
        self._user_option_dict[name] = value

    def get(self, name, default=None):
        #: returns user option value if exists.
        #: returns default value if not exist.
        return self._user_option_dict.get(name, default)

    @property
    def parser(self):
        # creates new option parser object when it is not set.
        if not self.__parser:
            self.__parser = self._new_option_parser()
        #: returns an option parser object.
        return self.__parser

    def _new_option_parser(self):
        #: returns an OptionParser object.
        import optparse
        usage = 'Usage: %prog [options] [labels...]'
        parser = optparse.OptionParser(usage=usage, version=__version__, conflict_handler="resolve")
        add = parser.add_option
        add("-h", "--help",    dest="help",    action="store_true",     help="show help")
        add("-v", "--version", dest="version", action="store_true",     help="show version")
        add("-q", None,        dest="quiet",   action="store_true",     help="quiet (not verbose)    # same as Benchmarker(verbose=False)")
        add("-n", None,        dest="loop",    metavar="N", type="int", help="loop each benchmark    # same as Benchmarker(loop=N)")
        add("-c", None,        dest="cycle",   metavar="N", type="int", help="cycle all benchmarks   # same as Benchmarker(cycle=N)")
        add("-X", None,        dest="extra",   metavar="N", type="int", help="ignore N of min/max    # same as Benchmarker(extra=N)")
        add("-x", None,        dest="exclude", metavar="regexp",        help="skip benchmarks matched to regexp pattern")
        return parser

    def _separate_user_options(self, argv):
        #: separates args which starts with '--' from argv.
        new_argv = []
        user_options = []
        for arg in argv:
            if arg.startswith("--") and arg not in ("--help", "--version"):
                user_options.append(arg)
            else:
                new_argv.append(arg)
        return new_argv, user_options

    def _populate_opts(self, opts, args):
        #: sets attributes according to options.
        if opts.quiet   is not None:  self.verbose = False
        if opts.loop    is not None:  self.loop    = int(opts.loop)
        if opts.cycle   is not None:  self.cycle   = int(opts.cycle)
        if opts.extra   is not None:  self.extra   = int(opts.extra)
        if opts.exclude is not None:  self.exclude = opts.exclude
        self.args = args
        #: converts patterns into regexps.
        self._exclude_rexps = opts.exclude and [re.compile(opts.exclude)] or []
        self._include_rexps = [ re.compile(_meta2rexp(arg)) for arg in self.args ]

    def _parse_user_options(self, user_options):
        d = {}
        for option in user_options:
            #: raises ValueError if user option is invalid format.
            m = re.match('^--([-\w]+)(=.*)?$', option)
            if not m:
                raise ValueError("%s: invalid format user option." % option)
            name  = m.group(1)
            #: if value is not specified then uses True instead.
            value = not m.group(2) and True or m.group(2)[1:]
            d[name] = value
        #: returns a dictionary object.
        return d

    def _help_message(self, parser=None):
        #: returns help message.
        if parser is None:  parser = self._new_option_parser()
        msg = parser.format_help()
        msg += r"""
  --name[=val]   user-defined option
                 ex.
                     # get value of user-defined option
                     from benchmarker import cmdopt
                     print(repr(cmdopt['name']))  #=> 'val'

Examples:

  ### cycle all benchmarks 5 times with 1000,000 loop
  $ python %(file)s -c 5 -n 1000000

  ### invoke bench1, bench2, and so on
  $ python %(file)s 'bench*'

  ### invoke al benchmarks except bench1, bench2, and bench3
  $ python %(file)s -x '^bench[1-3]$'

  ### invoke all benchmarks with user-defined options
  $ python %(file)s --name1 --name2=value2
"""[1:] % {'file': sys.argv and os.path.basename(sys.argv[0]) or 'foo.py'}
        return msg

    def parse(self, argv=None):
        #: uses sys.argv when argv is not specified.
        if argv is None: argv = sys.argv
        #: parses command line options and sets attributes.
        argv, user_options = self._separate_user_options(argv)
        opts, args = self.parser.parse_args(argv)
        args = args[1:]
        self._populate_opts(opts, args)
        self._user_option_dict = self._parse_user_options(user_options)
        #: if '-h' or '--help' specified then print help message and exit.
        if opts.help:
            print(self._help_message(self.parser))
            sys.exit()
        #: if '-v' or '--version' specified then print version and exit.
        if opts.version:
            print(__version__)
            sys.exit()

    def should_skip(self, task_label):
        #: returns False if task is for empty loop.
        if task_label == '(Empty)':
            return False
        #: returns True if task label matches to exclude pattern.
        if self._exclude_rexps:
            for rexp in self._exclude_rexps:
                if rexp.search(task_label):
                    return True
        #: when labels are specified in command-line...
        if self.args:
            #: returns False if task label matches to them.
            assert self._include_rexps
            for rexp in self._include_rexps:
                if rexp.search(task_label):
                    return False
            #: returns True if task label doesn't match to them.
            return True
        #: returns False if no labels specified in command-line.
        else:
            return False


cmdopt = CommandOption()


def _meta2rexp(metastr):
    #: converts a string containing metacharacters into regexp string.
    buf = []; b = buf.append
    b('^')
    i = 0
    n = len(metastr)
    while i < n:
        ch = metastr[i]
        #: converts '*' into '.*'.
        if ch == '*':
            b('.*')
        #: converts '?' into '.'.
        elif ch == '?':
            b('.')
        #: converts '{aa,bb,(cc)}' into '(aa|bb|\(cc\))'.
        elif ch == '{':
            j = i + 1
            while j < n and metastr[j] != '}':
                j += 1
            if j == n:
                raise ValueError("%s: '{' is not closed by '}'." % metastr)
            assert metastr[j] == '}'
            s = '('
            for word in metastr[i+1:j].split(','):
                b(s)
                b(re.escape(word))
                s = '|'
            if s == '(':
                raise ValueError("%s: '{}' is empty.")
            b(')')
            i = j
        #: escapes characters with re.escape().
        else:
            b(re.escape(ch))
        i += 1
    b('$')
    return ''.join(buf)
