<?php

///
/// $Release: $
/// $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
/// $License: Creative Commons Attribution (CC BY) $
///


require_once('Benchmarker.php');

$members = array("Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon");

$loop = 100*1000;
if (isset($_SERVER['N'])) $loop = intval($_SERVER['N']);

$bm = new Benchmarker($loop);

$expected = file_get_contents('../expected.out');

function verify($out) {
    global $expected;
    if ($out != $expected) {
        throw new Exception('$expected == $out: failed.');
    }
}



function task_empty($n) {
    while (--$n >= 0) {
        for ($j = 0; $j < 20; $j++) {
            // pass
        }
    }
}
$bm->emptyTask('task_empty');


$title = '$str.$str.$str';
function task_strcat1($n) {
    global $members;
    list($s1, $s2, $s3, $s4, $s5) = $members;
    while (--$n >= 0) {
        $out = "";
        $out .= "<table>\n";
        for ($j = 0; $j < 20; $j++) {
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
    verify($out);
}
$bm->task($title, 'task_strcat1');


$title = '$arr[]=$str;implode();';
function task_strcat2($n) {
    global $members;
    list($s1, $s2, $s3, $s4, $s5) = $members;
    while (--$n >= 0) {
        $buf = array();
        $buf[] = "<table>\n";
        for ($j = 0; $j < 20; $j++) {
            $buf[] = "  <tr>
    <td>"; $buf[] = $s1; $buf[] = "</td>
    <td>"; $buf[] = $s2; $buf[] = "</td>
    <td>"; $buf[] = $s3; $buf[] = "</td>
    <td>"; $buf[] = $s4; $buf[] = "</td>
    <td>"; $buf[] = $s5; $buf[] = "</td>
  </tr>\n";
        }
        $buf[] = "</table>\n";
        $out = implode($buf);
    }
    verify($out);
}
$bm->task($title, 'task_strcat2');


$title = 'ob_start();echo()';
function task_strcat3($n) {
    global $members;
    list($s1, $s2, $s3, $s4, $s5) = $members;
    while (--$n >= 0) {
        ob_start();
        echo "<table>\n";
        for ($j = 0; $j < 20; $j++) {
            echo "  <tr>
    <td>", $s1, "</td>
    <td>", $s2, "</td>
    <td>", $s3, "</td>
    <td>", $s4, "</td>
    <td>", $s5, "</td>
  </tr>\n";
        }
        echo "</table>\n";
        $out = ob_get_clean();
    }
    verify($out);
}
$bm->task($title, 'task_strcat3');


$bm->run();