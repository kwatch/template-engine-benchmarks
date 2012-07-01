<?php

///
/// $Release: $
/// $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
/// $License: MIT License $
///


class Benchmarker {
    var $_empty_task = null;
    var $_tasks = array();

    function __construct($loop=100000) {
        $this->loop = $loop;
    }

    function emptyTask($body) {
        $this->_empty_task = $body;
    }

    function task($title, $body) {
        $this->_tasks[] = array($title, $body);
    }

    function run($loop=null) {
        $this->print_environment();
        $tasks = $this->_tasks;
        if ($this->_empty_task) {
            array_unshift($tasks, array("(Empty)", $this->_empty_task));
        }
        if (! $loop) $loop = $this->loop;
        print("# loop=$loop\n");
        $cycle = getenv('C') ? (int)getenv('C') : 3;
        for ($i = 1; $i <= $cycle; $i++) {
            $this->_run_tasks($loop, $i, $tasks);
        }
    }

    function _run_tasks($loop, $cycle, $tasks) {
        print("\n");
        printf("%-30s%10s%10s\n", "# cycle=$cycle", "real", "actual");
        $empty_time = 0.0;
        foreach ($tasks as $i=>$pair) {
            list($title, $body) = $pair;
            if (function_exists('gc_collect_cycles')) {
                gc_collect_cycles();
            }
            printf('%-30s', $title);
            $start = microtime();
            $body($loop);
            $stop = microtime();
            $real = $this->_elapsed($start, $stop);
            $actual = $real - $empty_time;
            if ($i == 0 && $this->_empty_task) {
                $empty_time = $real;
            }
            printf("%10.3f%10.3f\n", $real, $actual);
        }
    }

    function _elapsed($start, $stop) {
        list($msec1, $sec1) = explode(' ', $start);
        list($msec2, $sec2) = explode(' ', $stop);
        $sec  = intval($sec2) - intval($sec1);
        $msec = floatval($msec2) - floatval($msec1);
        return $sec + $msec;
    }

    function print_environment() {
        printf("# %-20s%s\n", 'PHP_VERSION:', PHP_VERSION);
        printf("# %-20s%s\n", 'PHP_OS:', PHP_OS);
        //printf("# %-20s%s\n", 'php_uname:', php_uname());
        //printf("# %-20s%s\n", 'loop:', $this->loop);
        print("\n");
    }

}

