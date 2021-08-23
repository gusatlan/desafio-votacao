package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import br.com.oneguy.votacao.repositories.IPollMeetingRepository;
import br.com.oneguy.votacao.utils.Action;
import br.com.oneguy.votacao.utils.CRUD;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Set;

@Service
public class PollMeetingServiceImpl implements IPollMeetingService {

    private final Logger logger;
    private final IPollMeetingRepository repository;
    private final IValidatorService validator;
    private final IMinuteMeetingService minuteMeetingService;
    private ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param logger
     * @param repository
     * @param validator
     * @param minuteMeetingService
     * @param sendMessage
     */
    public PollMeetingServiceImpl(
            final Logger logger,
            final IPollMeetingRepository repository,
            final IValidatorService validator,
            final IMinuteMeetingService minuteMeetingService,
            final ISendMessage sendMessage
    ) {
        this.logger = logger;
        this.minuteMeetingService = minuteMeetingService;
        this.validator = validator;
        this.repository = repository;
        this.sendMessage = sendMessage;
    }

    /**
     * Return poll by id
     *
     * @param id
     * @return pollMeeting
     */
    @Override
    public PollMeetingPU findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Check if poll exists
     *
     * @param id
     * @return exists
     */
    @Override
    public boolean exists(final String id) {
        return findById(id) != null;
    }

    /**
     * Validate entity
     *
     * @param value
     * @return valid
     */
    @Override
    public boolean validate(PollMeetingPU value) {
        return value != null && value.isValid() && validateEntity(value).isEmpty();
    }

    /**
     * Check violations
     *
     * @param obj
     * @param <T>
     * @return violations
     */
    @Override
    public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }

    /**
     * Add PollMeeting
     *
     * @param value
     * @return poll
     */
    @Override
    @Transactional
    public PollMeetingPU add(final PollMeetingPU value) throws Exception {
        PollMeetingPU obj = value;

        try {
            if (value == null) {
                throw new NullPointerException();
            }

            if (value.getMinuteMeeting() == null) {
                throw new NullPointerException("Sessão de votação sem Ata");
            }

            if (!minuteMeetingService.exists(value.getMinuteMeeting().getId())) {
                throw new EntityNotFoundException("Ata não encontrada " + value.getMinuteMeeting().getId());
            }

            if (minuteMeetingService.findById(value.getMinuteMeeting().getId()).getPoll() != null) {
                throw new EntityExistsException("Ata já tem sessão de votação " + value.getMinuteMeeting().getId());
            }

            if (!validate(value)) {
                throw new ValidationException("Sessão de votação inválida");
            }

            obj = repository.save(value);
        } catch (Exception e) {
            logger.error("ERROR on ADD PollMeeting {}: ", e, obj);
            throw e;
        }

        return obj;
    }

    /**
     * Add PollMeetingDTO
     *
     * @param value
     * @return response
     */
    @Override
    public String add(final PollMeetingDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException();
            }

            if (value.getMinuteMeetingId() == null) {
                throw new NullPointerException("Sessão de votação sem Ata");
            }

            if (!minuteMeetingService.exists(value.getMinuteMeetingId())) {
                throw new EntityNotFoundException("Ata não encontrada " + value.getMinuteMeetingId());
            }

            if(value.getId() == null) {
                value.setId(new PollMeetingPU().getId());
            }

            if (minuteMeetingService.findById(value.getMinuteMeetingId()).getPoll() != null) {
                throw new EntityExistsException("Ata já tem sessão de votação " + value.getMinuteMeetingId());
            }

            if (!validator.validate(value)) {
                throw new ValidationException("Sessão de votação inválida");
            }

            sendMessage.send(new Action<>(value, CRUD.CREATE));
            id = value.getId();
        } catch (Exception e) {
            logger.error("ERROR on VALIDATE PollMeeting {} to ADD: ", e, value);
            throw e;
        }

        return id;
    }

    /**
     * Convert PollMeetingDTO to PollMeetingPU
     *
     * @param value
     * @return pollMeeting
     */
    @Override
    public PollMeetingPU convert(final PollMeetingDTO value) {
        PollMeetingPU obj = new PollMeetingPU();

        obj.setId(value.getId());
        obj.setMinuteMeeting(minuteMeetingService.findById(value.getMinuteMeetingId()));
        obj.adjustDuration(value.getDuration());

        return obj;
    }
}
