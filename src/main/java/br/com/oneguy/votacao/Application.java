package br.com.oneguy.votacao;

import br.com.oneguy.votacao.config.LoggerConfig;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    private static final long WAIT = 30;

    public static void main(final String[] args) {
        Logger logger = new LoggerConfig().getLogger();

        logger.info("Waiting {}s to startup database...", WAIT);

        try {
            Thread.sleep(WAIT * 1000);
        } catch (Exception e) {
        }

        logger.info("Starting application...");
        SpringApplication.run(Application.class, args);
    }
}
