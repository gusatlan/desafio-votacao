package br.com.oneguy.votacao.services;

import java.util.Set;

public interface IValidatorService {
    <T> Set<String> validateEntity(T entity);

    <T> boolean validate(T entity);
}
