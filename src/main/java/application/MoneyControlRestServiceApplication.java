package application;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class MoneyControlRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MoneyControlRestServiceApplication.class);
        app.run(args);
    }
}
