package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;

import javax.transaction.Transactional;
import java.util.Set;

public interface IPollMeetingService {
    PollMeetingPU findById(String id);

    boolean exists(String id);

    boolean validate(PollMeetingPU value);

    <T> Set<String> validateEntity(T obj);

    @Transactional
    PollMeetingPU add(PollMeetingPU value);

    String add(PollMeetingDTO value) throws Exception;

    PollMeetingPU convert(PollMeetingDTO value);
}
