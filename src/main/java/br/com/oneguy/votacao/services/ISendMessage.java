package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.BaseIdDTO;
import br.com.oneguy.votacao.utils.Action;

public interface ISendMessage {

    <T extends BaseIdDTO> void send(final Action<T> value) throws Exception;
}
