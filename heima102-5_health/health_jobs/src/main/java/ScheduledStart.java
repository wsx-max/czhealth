import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ScheduledStart {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");
        System.in.read();
    }
}
