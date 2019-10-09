package gyqw.util;

/**
 * @author fred
 * 2019-09-21 10:54 AM
 */
public class Logger {

    public Logger() {
    }

    public Logger(String marker) {
        info(marker);
    }

    public void info(Object info) {
        System.out.println(info);
    }

    public void info(String info, Object... param) {
        System.out.println(String.format(info, param));
    }

    public void error(Exception e) {
        e.printStackTrace();
    }
}
