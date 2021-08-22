package br.com.oneguy.votacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidationConfig {

    private final ValidatorFactory factory;
    private final Validator validator;

    /**
     * Constructor
     */
    public ValidationConfig() {
        this.factory = Validation.buildDefaultValidatorFactory();
        this.validator  = factory.getValidator();
    }

    /**
     * Return validator
     * @return validator
     */
    @Bean
    public Validator getValidator() {
        return this.validator;
    }
}
