package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateVoteDTO;
import br.com.oneguy.votacao.domain.persistence.AssociateVotePU;

import javax.transaction.Transactional;
import java.util.Set;

public interface IAssociateVoteService {
    AssociateVotePU findById(String id);

    boolean exists(String id);

    boolean validate(AssociateVotePU value);

    <T> Set<String> validateEntity(T obj);

    @Transactional
    AssociateVotePU add(AssociateVotePU value);

    String add(AssociateVoteDTO value) throws Exception;

    AssociateVotePU convert(final AssociateVoteDTO value);
}
