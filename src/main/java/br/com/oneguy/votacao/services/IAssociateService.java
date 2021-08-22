package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.utils.CpfState;
import br.com.oneguy.votacao.utils.Response;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

public interface IAssociateService {

    CpfState validateIdentification(final String identification);

    Collection<AssociatePU> findByIdentification(final String identification);

    Collection<AssociatePU> findAll();

    AssociatePU findById(final String id);

    AssociatePU add(final AssociatePU value) throws Exception;

    String add(final AssociateDTO value) throws Exception;

    AssociatePU update(final AssociatePU value);

    String update(final AssociateDTO value) throws Exception;

    boolean remove(final String id);

    String remove(final AssociateDTO value) throws Exception;

    boolean validate(final AssociatePU value);

    <T> Set<String> validateEntity(T obj);

    boolean exists(final String id);

}