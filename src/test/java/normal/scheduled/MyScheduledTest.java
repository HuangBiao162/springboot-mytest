package normal.scheduled;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MyScheduledTest {
    @Autowired
    MyScheduled01 myScheduled01;

    private static final String tokenurl = "https://api.talkingdata.com/tdmkaccount/authen/app/v3";
    private static final String refreshTokenurl = "https://api.talkingdata.com/authentication/api/refreshToken";
    private static final String apikey = "ea63aaa9b0614964b4f19df9d18c6410";
    private static final String apitoken = "214bf006c85b432597d1a43b42113ea2";

    @Test
    public void mapTest(){
        Map params = new HashMap<String, Object>(2);
        params.put("apikey", "apikey");
        params.put("refreshToken", "refreshTokenKey");
        System.out.println(params);
        params.remove("refreshToken");
        params.put("apitoken", "apitoken");
        System.out.println(params);
    }



}
