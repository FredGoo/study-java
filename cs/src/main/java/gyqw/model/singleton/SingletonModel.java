package gyqw.model.singleton;

/**
 * @author fred
 * 2019-10-22 10:29 AM
 */
public class SingletonModel {
    private volatile static SingletonModel uniqueInstance;

    private SingletonModel() {
    }

    public static SingletonModel getUniqueInstance() {
        // 判断是否已经实例化
        if (uniqueInstance == null) {
            // 类加锁
            synchronized (SingletonModel.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new SingletonModel();
                }
            }
        }

        return uniqueInstance;
    }
}
