package ma.enset.tpjwtspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TpJwtSpringApplicationTests {

    public static void  main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TpJwtSpringApplicationTests.class, args);
    }

}
