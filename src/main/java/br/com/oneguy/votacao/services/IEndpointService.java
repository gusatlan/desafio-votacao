package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.utils.Response;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.function.Function;

public interface IEndpointService {
    <T> ResponseEntity<Response<String>> proccess(Function<T, String> exec, T value, Long timeout, String uriTemplate, Collection<String> violations);
}
