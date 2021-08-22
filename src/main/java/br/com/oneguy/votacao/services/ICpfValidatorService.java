package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.utils.CpfState;

public interface ICpfValidatorService {

    CpfState checkCpf(final String identification);

}
