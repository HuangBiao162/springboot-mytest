package thread;

import java.util.concurrent.CountDownLatch;

public class CountDownTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        new Thread(()->{
            try {
                System.out.println("线程1，睡2秒");
                Thread.sleep(2000);
                countDownLatch.countDown();
                System.out.println("线程1睡醒了，执行countDown操作,count="+countDownLatch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                System.out.println("线程2，睡4秒");
                Thread.sleep(4000);
                countDownLatch.countDown();
                System.out.println("线程2睡醒了，执行countDown操作,count="+countDownLatch.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
        System.out.println("主线程执行完..");
    }

}
