package main

import (
  "fmt"
  "os"
  "time"
  "strings"
  "bytes"
  "strconv"
  "io/ioutil"
)


func members() []string {
  arr := []string { "Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon" }
  return arr
}


func verify(out string) bool {
  chars, err := ioutil.ReadFile("../expected.out")
  expected := string(chars)
  if err != nil {
    os.Stderr.WriteString("*** failed: " + err.String())
    return false
  }
  if out != expected {
    os.Stderr.WriteString("*** expected == out: failed\n")
    return false
  }
  return true
}


func task_empty(loop int) string {
  //s1 := "Haruhi"; s2 := "Mikuru"; s3 := "Yuki"; s4 := "Itsuki"; s5 := "Kyon"
  for i := 0; i < loop; i++ {
    for j := 0; j < 20; j++ {
      // pass
    }
  }
  return ""
}


func task_strcat1(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := ""
    buf += `<table>
`;  for j := 0; j < 20; j++ {
    buf += `  <tr>
    <td>` + s1 + `</td>
    <td>` + s2 + `</td>
    <td>` + s3 + `</td>
    <td>` + s4 + `</td>
    <td>` + s5 + `</td>
  </tr>
`;  }
    buf += `</table>
`;
    out = buf
  }
  //verify(out)
  return out
}


func task_strcat2(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := ""
    buf += `<table>
`;  for j := 0; j < 20; j++ {
    buf += `  <tr>
    <td>`; buf += s1; buf += `</td>
    <td>`; buf += s2; buf += `</td>
    <td>`; buf += s3; buf += `</td>
    <td>`; buf += s4; buf += `</td>
    <td>`; buf += s5; buf += `</td>
  </tr>
`;  }
    buf += `</table>
`;
    out = buf
  }
  //verify(out)
  return out
}


func task_append1(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := []string{}
    buf = append(buf, `<table>
`); for j := 0; j < 20; j++ {
      buf = append(buf, `  <tr>
    <td>`, s1, `</td>
    <td>`, s2, `</td>
    <td>`, s3, `</td>
    <td>`, s4, `</td>
    <td>`, s5, `</td>
  </tr>
`); }
    buf = append(buf, `</table>
`);
    out = strings.Join(buf, "")
  }
  //verify(out)
  return out
}


func task_append2(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := []string{}
    buf = append(buf, `<table>
`); for j := 0; j < 20; j++ {
      buf = append(buf, `  <tr>
    <td>` + s1 + `</td>
    <td>` + s2 + `</td>
    <td>` + s3 + `</td>
    <td>` + s4 + `</td>
    <td>` + s5 + `</td>
  </tr>
`); }
    buf = append(buf, `</table>
`);
    out = strings.Join(buf, "")
  }
  //verify(out)
  return out
}


func task_append3(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := []string{}
    buf = append(buf, `<table>
`); for j := 0; j < 20; j++ {
      buf = append(buf, fmt.Sprintf(`  <tr>
    <td>%s</td>
    <td>%s</td>
    <td>%s</td>
    <td>%s</td>
    <td>%s</td>
  </tr>
`, s1, s2, s3, s4, s5));
    }
    buf = append(buf, `</table>
`);
    out = strings.Join(buf, "")
  }
  verify(out)
  return out
}


func task_bufstr1(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := bytes.NewBufferString("")
    fmt.Fprint(buf, `<table>
`); for j := 0; j < 20; j++ {
      buf.WriteString(`  <tr>
    <td>`); buf.WriteString(s1); buf.WriteString(`</td>
    <td>`); buf.WriteString(s2); buf.WriteString(`</td>
    <td>`); buf.WriteString(s3); buf.WriteString(`</td>
    <td>`); buf.WriteString(s4); buf.WriteString(`</td>
    <td>`); buf.WriteString(s5); buf.WriteString(`</td>
  </td>
`);
    }
    buf.WriteString(`</table>
`);
    out = string(buf.Bytes())
  }
  //fmt.Printf("%s", out)
  return out
}


func task_bufstr2(loop int) string {
  m := members()
  s1 := m[0]; s2 := m[1]; s3 := m[2]; s4 := m[3]; s5 := m[4];
  out := ""
  for i := 0; i < loop; i++ {
    buf := bytes.NewBufferString("")
    fmt.Fprint(buf, `<table>
`); for j := 0; j < 20; j++ {
      buf.WriteString(`  <tr>
    <td>` + s1 + `</td>
    <td>` + s2 + `</td>
    <td>` + s3 + `</td>
    <td>` + s4 + `</td>
    <td>` + s5 + `</td>
  </tr>
`); }
    buf.WriteString(`</table>
`);
    out = string(buf.Bytes())
  }
  //verify(out)
  return out
}


func run(loop int, empty_time float64, body func(int) string, title string) float64 {
  fmt.Printf("%-45s", title)
  start := time.Nanoseconds()
  body(loop)
  stop := time.Nanoseconds()
  //fmt.Printf("%10.3f\n", float64(stop - start)/1000000000.0)
  real := float64(stop - start)/(1000*1000*1000)
  actual := real - empty_time
  fmt.Printf(" %10.3f %10.3f\n", real, actual)
  return real
}


func main() {
  loop := 100000
  if os.Getenv("N") != "" {
    n, err := strconv.Atoi(os.Getenv("N"))
    if err != nil {
      os.Stderr.WriteString("*** can't convert N\n")
    }
    loop = n
  }
  fmt.Printf("*** loop=%d\n", loop)
  cycle := 3
  if os.Getenv("C") != "" {
    c, err := strconv.Atoi(os.Getenv("C"))
    if err != nil {
      os.Stderr.WriteString("*** can't convert C\n")
    }
    cycle = c
  }
  for i := 1; i <= cycle; i++ {
    fmt.Printf("\n")
    fmt.Printf("%-45s %10s %10s\n", "# cycle=" + strconv.Itoa(i), "real", "actual")
    empty_time := 0.0
    empty_time = run(loop, empty_time, task_empty, `(Empty)`)
    run(loop, empty_time, task_strcat1, `str+=s1+s2+s3`)
    //run(loop, empty_time, task_strcat2, `str+=s1;str+=s2;str+=s3;`)
    run(loop, empty_time, task_append1, `append(a,s1,s2,s3);strings.Join(a)`)
    run(loop, empty_time, task_append2, `append(a,s1+s2+s3);strings.Join(a)`)
    //run(loop, empty_time, task_append3, `append(a,fmt.Sprintf(s1,s2,s3))`)
    run(loop, empty_time, task_bufstr1, `bytes.NewBufferString().Write(s);Write(s)`)
    run(loop, empty_time, task_bufstr2, `bytes.NewBufferString().Write(s+s+s)`)
  }
}
