package gyqw.model.multithread;

/**
 * @author fred
 * 2019-10-09 2:44 PM
 */
public class ThreadUnsafeExample {
    private int cnt = 0;

    public void add() {
        cnt++;
    }

    public int get() {
        return cnt;
    }
}
