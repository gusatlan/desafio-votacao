package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

public interface IMinuteMeetingService {
    MinuteMeetingPU findById(String id);

    Collection<MinuteMeetingPU> findAll();

    Page<MinuteMeetingPU> findAll(final Pageable pageable);

    @Transactional
    MinuteMeetingPU add(MinuteMeetingPU value) throws Exception;

    @Transactional
    MinuteMeetingPU update(MinuteMeetingPU value) throws Exception;

    @Transactional
    boolean remove(String id) throws Exception;

    boolean validate(MinuteMeetingPU value);

    <T> Set<String> validateEntity(T obj);

    boolean exists(String id);

    String add(MinuteMeetingDTO value) throws Exception;

    String update(MinuteMeetingDTO value) throws Exception;

    String remove(MinuteMeetingDTO value) throws Exception;

    MinuteMeetingDTO convert(final MinuteMeetingPU value);

    Collection<MinuteMeetingDTO> convert(final Collection<MinuteMeetingPU> values);
}
