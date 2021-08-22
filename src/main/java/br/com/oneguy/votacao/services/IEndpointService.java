package br.com.oneguy.votacao.services;

import javax.servlet.http.HttpServletRequest;

public interface IEndpointService {
    String process(final String endpoint, final String id, final HttpServletRequest request);
}
