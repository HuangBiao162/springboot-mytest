package thread;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;


public class CallableTest extends Thread implements Runnable{

    @Autowired
    AsyncTaskExecutor asyncTaskExecutor;

    public static CountDownLatch countDownLatch = new CountDownLatch(0);


    @Test
    public void t1(){
        long startTime = System.currentTimeMillis();

        Object result1 = null;
        Object result2 = null;

        TaskHandler t1 = new TaskHandler(1, "call任务1", 1000L);
        TaskHandler t2 = new TaskHandler(2, "call任务2", 2000L);
        TaskHandler t3 = new TaskHandler(3, "call任务3", 3000L);
        List <TaskHandler> taskList = new ArrayList <>();
        taskList.add(t1);
        taskList.add(t2);
        taskList.add(t3);

        Thread wnd = new Thread(() -> {
            System.out.println("窝嫩叠");
        });

        //start() 方法用于启动线程，run() 方法用于执行线程的运行时代码。run() 可以重复调用，而 start() 只能调用一次。
        wnd.start();
        wnd.run();
        wnd.run();

        for(TaskHandler taskHandler : taskList){
            FutureTask <Object> future = new FutureTask <>(taskHandler);

            try {
                Thread thread = new Thread(future);
                thread.start();
                //thread.run();
                Object o = future.get();
                System.out.println(String.valueOf(o));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        long endTime = System.currentTimeMillis();
        System.out.println("花费时间：" + (endTime - startTime));
        System.out.println("end");
    }


    class TaskHandler implements Callable {

        private int num;
        private String context;
        private Long time;

        public TaskHandler(int num, String context, Long time) {
            this.num = num;
            this.context = context;
            this.time = time;
        }

        @Override
        public Object call() throws Exception {
            Thread.sleep(time);
            return context;
        }
    }

}
