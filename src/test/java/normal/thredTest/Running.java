package normal.thredTest;

import java.util.HashMap;
import java.util.Map;

public class Running {

    private static Map <Long, String> map = new HashMap <>();
    private static Long count = 0L;

    private static void run(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (Running.class){
            String name = Thread.currentThread().getName();
            count++;
            map.put(count, name);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 1; i <= 5; i++) {
            Thread thread = new Thread(() -> run());
            thread.setName("name"+i);
            thread.start();
        }

        Thread.sleep(2000);

        for (Map.Entry <Long, String> map : map.entrySet()) {
            System.out.println("第"+map.getKey()+"名："+map.getValue());
        }

    }

}
