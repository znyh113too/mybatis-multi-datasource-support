package test.chao.base;

/**
 * @author xiezhengchao
 * @since 2018/5/20 14:52
 */
public class DataSourceSelector {
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String key) {
        THREAD_LOCAL.set(key);
    }

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
