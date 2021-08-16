package thread;

public class ThreadLocalTest {

    public static final ThreadLocal <Integer> threadLocal = new ThreadLocal <Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {

        for (int j = 0; j < 3; j++) {
            new Thread(() -> {
                runTest();
            }).start();

        }
    }

    public static void runTest() {
        for (int i = 0; i < 5; i++) {
            Integer n = threadLocal.get();
            n += i;
            threadLocal.set(n);
            System.out.println(Thread.currentThread().getName() + "===" + n);
        }
    }

}
