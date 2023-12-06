package me.project;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableEncryptableProperties
public class UnsecureBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnsecureBlogApplication.class, args);
    }
}
