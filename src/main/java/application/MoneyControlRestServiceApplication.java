package application;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class MoneyControlRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MoneyControlRestServiceApplication.class);
        app.setBanner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                out.print("\n\nTHIS IS MY BANNER!\n\n");
            }
        });
        app.run(args);
    }


}
