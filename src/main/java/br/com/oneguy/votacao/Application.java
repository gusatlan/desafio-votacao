package br.com.oneguy.votacao;

import br.com.oneguy.votacao.config.LoggerConfig;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        Logger logger = new LoggerConfig().getLogger();

        try {
            Long delay = Long.valueOf(System.getenv("WAIT_START_DURATION"));
            logger.info("Waiting {}s to startup database...", delay);
            Thread.sleep(delay * 1000);
        } catch (Exception e) {
        }

        logger.info("Starting application...");
        SpringApplication.run(Application.class, args);
    }
}
