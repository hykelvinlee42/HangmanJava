package ca.cmpt213.a4.onlinehangman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application class is a bootstrap for starting Spring Boot + Thymeleaf.
 * @author Ho Yin Kelvin Lee
 * @author hyl30@sfu.ca
 * @version 1.0
 * @since 1.0
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
