package gyqw.test;

import gyqw.util.Logger;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * @author fred
 * 2020-03-11 3:42 PM
 */
public class Yeqinwen {
    private Logger logger = new Logger();

    @Test
    public void charTest() {
        int x = 25508;
        char y = (char) x;
        logger.info(y);
    }

    @Test
    public void calcTest() {
        int m = 1;
        int n = 2;
        int sum = m++ + ++n - n-- - --m + n-- - --m;

        logger.info("m: %s, n: %s, sum: %s", m, n, sum);

        int x = 1;
        int y = x++;
        logger.info("x: %s, y: %s", x, y);

        x = 1;
        y = ++x;
        logger.info("x: %s, y: %s", x, y);
    }

    @Test
    public void scannerTest() {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        logger.info(i);

        switch (1){

        }
    }
}
