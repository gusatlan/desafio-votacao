package br.com.oneguy.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {

        try {
            Thread.sleep(30000);
        } catch(Exception e) {
        }

        SpringApplication.run(Application.class, args);
    }
}
