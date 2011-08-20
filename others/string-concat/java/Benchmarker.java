import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


class Benchmarker {

    //class Task {
    //    private String _title;
    //    private Runnable _body;
    //
    //    public String getTitle() { return _title; }
    //    public Runnable getBody() { return _body; }
    //
    //    Task(String title, Runnable body) {
    //        _title = title;
    //        _body = body;
    //    }
    //}

    public static class Task {
        private String _title;
        public String getTitle() { return _title; }
        public void run(int loop) {}
        public Task(String title) { _title = title; }
        public Task() { _title = "(Empty)"; }
    }

    private int _loop;
    private int _cycle;
    private List<Task> _tasks;
    private Task _empty_task;

    public Benchmarker(int loop, int cycle) {
        _loop = loop;
        _cycle = cycle;
        _tasks = new ArrayList<Task>();
    }

    public Benchmarker(int loop) {
        this(loop, 1);
    }

    public void emptyTask(Task t) {
        _empty_task = t;
    }

    public void task(Task t) {
        _tasks.add(t);
    }

    public void run() {
        run(_loop, false);
    }

    public void run(int loop) {
        run(loop, false);
    }

    public void run(boolean reverse) {
        run(_loop, reverse);
    }

    public void run(int loop, boolean reverse) {
        _printEnvironment();
        List<Task> tasks = new ArrayList<Task>();
        tasks.addAll(_tasks);
        if (reverse) Collections.reverse(tasks);
        if (_empty_task != null) tasks.add(0, _empty_task);
        System.out.printf("# loop = %d\n", loop);
        for (int i = 1; i <= _cycle; i++) {
            _runTask(loop, i, tasks);
        }
    }

    private void _runTask(int loop, int cycle, List<Task> tasks) {
        System.out.println();
        System.out.printf("%-40s %10s %10s\n", "# cycle=" + cycle, "real", "actual");
        long empty_time = 0;
        for (Task t: tasks) {
            System.out.printf("%-40s", t.getTitle());
            System.gc();  // start GC
            long start = System.currentTimeMillis();
            t.run(loop);
            long stop = System.currentTimeMillis();
            long real = stop - start;
            long actual = real - empty_time;
            if (t == _empty_task) {
                empty_time = real;
            }
            System.out.printf(" %10.3f %10.3f\n", real/1000.0, actual/1000.0);
        }
    }

    private void _printEnvironment() {
        String[] props = {
            "java.version", "java.vendor",
            "os.name", "os.arch", "os.version",
        };
        for (String prop: props) {
            System.out.printf("# %-20s%s\n", prop + ":", System.getProperty(prop));
        }
        System.out.println();
    }

}
