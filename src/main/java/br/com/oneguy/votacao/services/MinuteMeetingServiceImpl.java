package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.repositories.IMinuteMeetingRepository;
import br.com.oneguy.votacao.utils.Action;
import br.com.oneguy.votacao.utils.CRUD;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MinuteMeetingServiceImpl implements IMinuteMeetingService {

    private final IMinuteMeetingRepository repository;
    private final Logger logger;
    private final IValidatorService validator;
    private final ISendMessage sendMessage;

    @Autowired
    public MinuteMeetingServiceImpl(final IMinuteMeetingRepository repository,
                                    final Logger logger,
                                    final IValidatorService validator,
                                    final ISendMessage sendMessage) {
        this.repository = repository;
        this.logger = logger;
        this.validator = validator;
        this.sendMessage = sendMessage;
    }

    /**
     * Return MinuteMeeting
     *
     * @param id
     * @return minuteMeeting
     */
    @Override
    public MinuteMeetingPU findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Convert MinuteMeetingPU to MinuteMeetingDTO
     *
     * @param value
     * @return dto
     */
    @Override
    public MinuteMeetingDTO convert(final MinuteMeetingPU value) {
        MinuteMeetingDTO dto = new MinuteMeetingDTO();

        try {
            dto.setId(value.getId());
            dto.setDescription(value.getDescription());
            dto.setResume(value.getResume());

            try {
                dto.setCountTotal(value.getPoll().countTotal());
                dto.setCountAgree(value.getPoll().countAgrees());
                dto.setCountDisagree(value.getPoll().countDisagrees());
            } catch (Exception e) {
                dto.setCountTotal(0L);
                dto.setCountAgree(0L);
                dto.setCountDisagree(0L);
            }

        } catch (Exception e) {
            dto = null;
            logger.error("ERROR in CONVERT MinuteMeetingPU to MinuteMeetingDTO", e);
        }


        return dto;
    }

    @Override
    public Collection<MinuteMeetingDTO> convert(final Collection<MinuteMeetingPU> values) {
        return values.stream().filter(p -> p != null).map(p -> convert(p)).collect(Collectors.toSet());
    }

    /**
     * Return minuteMeetings
     *
     * @return minuteMeetings
     */
    @Override
    public Collection<MinuteMeetingPU> findAll() {
        return repository.findAll();
    }

    /**
     * Add MinuteMeeting
     *
     * @param value
     * @return minuteMeeting added
     */
    @Override
    @Transactional
    public MinuteMeetingPU add(final MinuteMeetingPU value) throws Exception {
        MinuteMeetingPU obj = null;

        try {
            if (findById(value.getId()) != null) {
                throw new EntityExistsException();
            }

            obj = repository.save(value);
            logger.info("MinuteMeeting {} added", value.getId());
        } catch (Exception e) {
            logger.error("ERROR on ADD MinuteMeeting", e);
            throw e;
        }

        return obj;
    }

    /**
     * Update MinuteMeeting
     *
     * @param value
     * @return minuteMeeting updated
     */
    @Override
    @Transactional
    public MinuteMeetingPU update(final MinuteMeetingPU value) throws Exception {
        MinuteMeetingPU obj = null;

        try {
            if (findById(value.getId()) == null) {
                throw new EntityNotFoundException();
            }

            obj = repository.save(value);
            logger.info("MinuteMeeting {} updated", value.getId());
        } catch (Exception e) {
            logger.error("ERROR on UPDATE MinuteMeeting", e);
            throw e;
        }

        return obj;
    }

    /**
     * Delete MinuteMeeting
     *
     * @param id
     * @return success
     */
    @Override
    @Transactional
    public boolean remove(final String id) throws Exception {

        try {
            MinuteMeetingPU obj = findById(id);
            if (obj == null) {
                throw new EntityNotFoundException();
            }

            repository.delete(obj);
            logger.info("MinuteMeeting {} deleted", obj.getId());
        } catch (Exception e) {
            logger.error("ERROR on DELETE MinuteMeeting", e);
            throw e;
        }

        return true;
    }

    /**
     * Validate entity
     *
     * @param value
     * @return valid
     */
    @Override
    @Transactional
    public boolean validate(MinuteMeetingPU value) {
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
     * Check if entity exists
     *
     * @param id
     * @return exist
     */
    @Override
    public boolean exists(final String id) {
        return findById(id) != null;
    }

    /**
     * Add MinuteMeetingDTO
     *
     * @param value
     * @return response
     */
    @Override
    public String add(final MinuteMeetingDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para inserir");
            }

            if (!validateEntity(value).isEmpty()) {
                throw new ValidationException("Erro na validação da Ata para inserir");
            }

            if (exists(value.getId())) {
                throw new EntityExistsException("Ata já cadastrada para inserir");
            }

            sendMessage.send(new Action<>(value, CRUD.CREATE));
            id = value.getId();
        } catch (Exception e) {
            logger.error("ERROR on VALIDATE to ADD MinuteMeetingDTO {}", e, value);
            throw e;
        }

        return id;
    }

    /**
     * Update MinuteMeetingDTO
     *
     * @param value
     * @return response
     */
    @Override
    public String update(final MinuteMeetingDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para atualizar");
            }

            if (!validateEntity(value).isEmpty()) {
                throw new ValidationException("Erro na validação da Ata para atualizar");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Ata não cadastrada");
            }

            sendMessage.send(new Action<>(value, CRUD.UPDATE));
            id = value.getId();
        } catch (Exception e) {
            logger.error("ERROR on VALIDATE to UPDATE MinuteMeetingDTO {}", e, value);
            throw e;
        }

        return id;
    }

    /**
     * Remove MinuteMeetingDTO
     *
     * @param value
     * @return response
     */
    @Override
    public String remove(final MinuteMeetingDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para excluir");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Ata não existe para excluir");
            }

            sendMessage.send(new Action<>(value, CRUD.DELETE));
            id = value.getId();
        } catch (Exception e) {
            logger.error("ERROR on VALIDATE to REMOVE MinuteMeetingDTO {}", e, value);
            throw e;
        }

        return id;
    }

}
