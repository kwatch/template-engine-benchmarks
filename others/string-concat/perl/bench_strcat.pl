###
### $Release: $
### $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
### $License: Creative Commons Attribution (CC BY) $
###


use strict;
use warnings;
use Benchmarker;

my $items = [
    "Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon",
    "Tsuruya", "Taniguchi", "Kunikida", "Ryoko", "Sasaki"
    #"Yui", "Mio", "Ritsu", "Mugi", "Azusa",
    #"Ui", "Jun", "Nodoka", "Sawako", "Tonchan",
];
my ($s1, $s2, $s3, $s4, $s5) = ("Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon");


my $debug = 0;

my $expected;

sub verify {
    my $out = shift;
    if (! $expected) {
        $expected = $out;
    }
    else {
        $expected eq $out  or die('$expected eq $out: failed.');
    }
}


my $loop  = $ENV{'N'} || 10000;
my $cycle = $ENV['C'] || 3;

my $bm = Benchmarker->new($loop, $cycle);


$bm->empty_task(sub {
    my ($loop, ) = @_;
    my $out;
    for my $i (1..$loop) {
        for my $j (1..20) {
            # pass
        }
    }
});


$bm->task('push(@arr,$str);join(@arr)', sub {
    my ($loop, ) = @_;
    my $out;
    for my $i (1..$loop) {
        my @buf = ();
        push @buf, "<table>\n";
        for my $j (1..20) {
            push @buf, "  <tr>
    <td>", $s1, "</td>
    <td>", $s2, "</td>
    <td>", $s3, "</td>
    <td>", $s4, "</td>
    <td>", $s5, "</td>
  </tr>\n";
        }
        push @buf, "</table>\n";
        $out = join "", @buf;
    }
    print "*** \$out=$out\n" if $debug;
    verify($out) if $debug;
});


$bm->task('push(@arr,$str) only (no join)', sub {
    my ($loop, ) = @_;
    my $out;
    for my $i (1..$loop) {
        my @buf = ();
        push @buf, "<table>\n";
        for my $j (1..20) {
            push @buf, "  <tr>
    <td>", $s1, "</td>
    <td>", $s2, "</td>
    <td>", $s3, "</td>
    <td>", $s4, "</td>
    <td>", $s5, "</td>
  </tr>\n";
        }
        push @buf, "</table>\n";
        #$out = join "", @buf;
    }
    #print "*** \$out=$out\n" if $debug;
    #verify($out) if $debug;
});


$bm->task('$str.$str.$str', sub {
    my ($loop, ) = @_;
    my $out;
    for my $i (1..$loop) {
        $out = "";
        $out .= "<table>\n";
        for my $j (1..20) {
            $out .= "  <tr>
    <td>" . $s1 . "</td>
    <td>" . $s2 . "</td>
    <td>" . $s3 . "</td>
    <td>" . $s4 . "</td>
    <td>" . $s5 . "</td>
  </tr>\n";
        }
        $out .= "</table>\n";
    }
    print "*** \$out=$out\n" if $debug;
    verify($out) if $debug;
});


$bm->run();
