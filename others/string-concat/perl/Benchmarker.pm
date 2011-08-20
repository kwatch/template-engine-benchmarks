# -*- coding: utf-8 -*-

###
### $Release: $
### $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
### $License: MIT License $
###

package Benchmarker;
use strict;
use warnings;
use Time::HiRes;



sub new {
    my $class = shift;
    my ($loop, $cycle) = @_;
    my $this = {
        loop => $loop || 100*1000,
        cycle => $cycle,
        _tasks => [],
        _empty_task => undef,
    };
    return bless($this, $class);
}


sub empty_task {
    my $this = shift;
    my ($body, ) = @_;
    $this->{_empty_task} = {title=>"(Empty)", body=>$body};
}


sub task {
    my $this = shift;
    my ($title, $body) = @_;
    push(@{$this->{_tasks}}, {title=>$title, body=>$body});
}


sub run {
    my $this = shift;
    my @tasks = ();
    my $empty_task = $this->{_empty_task};
    push @tasks, $this->{_empty_task} if $empty_task;
    push @tasks, @{$this->{_tasks}};
    #
    my $loop = $this->{loop};
    my $cycle = $this->{cycle} || 1;
    $this->print_environment();
    print "# loop=$loop\n";
    for my $i (1..$cycle) {
        print("\n");
        $this->_run_tasks($loop, $i, @tasks);
    }
    #
}


sub _run_tasks {
    my $this = shift;
    my ($loop, $cycle, @tasks) = @_;
    printf "%-35s%10s%10s\n", "# cycle=$cycle", "real", "actual";
    my $empty_time = 0.0;
    my $i = 0;
    for my $t (@tasks) {
        $i++;
        printf "%-35s", $t->{title};
        my $start = Time::HiRes::time();
        $t->{body}($loop);
        my $stop = Time::HiRes::time();
        my $real = $stop - $start;
        my $actual = $real - $empty_time;
        $empty_time = $real if $i == 1 && $this->{_empty_task};
        printf "%10.3f%10.3f\n", $real, $actual;
    }
}


sub print_environment {
    print "# version (\$]): $]\n";
    print "\n";
}


1;
