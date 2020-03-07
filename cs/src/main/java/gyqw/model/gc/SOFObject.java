package gyqw.model.gc;

/**
 * @author fred
 * 2020/3/7 4:58 下午
 */
public class SOFObject {
    private int length;

    public SOFObject(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void increment() {
        this.length++;
    }
}
