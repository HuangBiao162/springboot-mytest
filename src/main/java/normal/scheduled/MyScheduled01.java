package normal.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MyScheduled01 {

    //@Scheduled(cron = "0/1 * * * * *")
    public void systemContext01(){
        System.out.println("定时任务systemContext01执行了："+System.currentTimeMillis());
    }

    //@Scheduled(cron = "0/10 * * * * ?")
    public void systemContext02(){
        System.out.println("定时任务systemContext02执行了：我是systemContext02");
    }

}
