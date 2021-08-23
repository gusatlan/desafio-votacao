package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.utils.CpfState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.Order;
import java.util.Collection;
import java.util.Set;

public interface IAssociateService {

    CpfState validateIdentification(final String identification);

    Collection<AssociatePU> findByIdentification(final String identification);

    Collection<AssociatePU> findAll();

    Page<AssociatePU> findAll(final Pageable page);

    AssociatePU findById(final String id);

    AssociatePU add(final AssociatePU value) throws Exception;

    String add(final AssociateDTO value) throws Exception;

    AssociatePU update(final AssociatePU value) throws Exception;

    String update(final AssociateDTO value) throws Exception;

    boolean remove(final String id) throws Exception;

    String remove(final AssociateDTO value) throws Exception;

    boolean validate(final AssociatePU value);

    <T> Set<String> validateEntity(T obj);

    boolean exists(final String id);

    AssociateDTO convert(final AssociatePU value) throws NullPointerException;

}
