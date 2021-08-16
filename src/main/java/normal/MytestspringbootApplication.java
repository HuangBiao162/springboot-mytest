package normal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MytestspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytestspringbootApplication.class, args);
    }

}
