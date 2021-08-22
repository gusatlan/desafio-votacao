package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.repositories.IAssociateRepository;
import br.com.oneguy.votacao.utils.Action;
import br.com.oneguy.votacao.utils.CRUD;
import br.com.oneguy.votacao.utils.CpfState;
import br.com.oneguy.votacao.utils.StringUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Set;

@Service
public class AssociateServiceImpl implements IAssociateService {

    private final IAssociateRepository repository;
    private final ICpfValidatorService cpfValidatorService;
    private final Logger logger;
    private final IValidatorService validator;
    private final ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param repository
     * @param cpfValidatorService
     * @param logger
     * @param validator
     * @param sendMessage
     */
    @Autowired
    public AssociateServiceImpl(final IAssociateRepository repository,
                                final ICpfValidatorService cpfValidatorService,
                                final Logger logger,
                                final IValidatorService validator,
                                final ISendMessage sendMessage) {
        this.repository = repository;
        this.cpfValidatorService = cpfValidatorService;
        this.logger = logger;
        this.validator = validator;
        this.sendMessage = sendMessage;
    }


    /**
     * Validate Identification
     *
     * @param identification
     * @return state
     */
    public CpfState validateIdentification(final String identification) {
        return cpfValidatorService.checkCpf(identification);
    }

    /**
     * Return collection of Associate by identification
     *
     * @param identification
     * @return associates
     */
    public Collection<AssociatePU> findByIdentification(final String identification) {
        Collection<AssociatePU> items = repository
                .findByIdentification(
                        identification != null && !identification.trim().isEmpty()
                                ? StringUtil.clean(identification)
                                : null);
        return items;
    }

    /**
     * Return Associate
     *
     * @param id
     * @return associate
     */
    public AssociatePU findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Return associates
     *
     * @return associates
     */
    public Collection<AssociatePU> findAll() {
        return repository.findAll();
    }

    /**
     * Add Associate
     *
     * @param value
     * @return associate added
     */
    @Transactional
    public AssociatePU add(final AssociatePU value) throws Exception {
        AssociatePU obj = null;

        try {
            if (findById(value.getId()) != null) {
                throw new EntityExistsException();
            }

            if (!findByIdentification(value.getIdentification()).isEmpty()) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associate {} {} added", value.getId(), value.getIdentification());
        } catch (Exception e) {
            logger.error("ERROR on ADD Associate", e);
            throw e;
        }

        return obj;
    }

    /**
     * Update Associate
     *
     * @param value
     * @return associate updated
     */
    @Transactional
    public AssociatePU update(final AssociatePU value) {
        AssociatePU obj = null;

        try {
            if (findById(value.getId()) == null) {
                throw new EntityNotFoundException();
            }

            if (findByIdentification(value.getIdentification()).stream().filter(p -> !p.getId().equalsIgnoreCase(value.getId())).count() > 0) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associate {} {} updated", value.getId(), value.getIdentification());
        } catch (Exception e) {
            logger.error("ERROR on UPDATE Associate", e);
        }

        return obj;
    }

    /**
     * Delete Associate
     *
     * @param id
     * @return success
     */
    @Transactional
    public boolean remove(final String id) {
        boolean success = true;

        try {
            AssociatePU obj = findById(id);
            if (obj == null) {
                throw new EntityNotFoundException();
            }

            repository.delete(obj);
            logger.info("Associate {} {} deleted", obj.getId(), obj.getIdentification());
        } catch (Exception e) {
            success = false;
            logger.error("ERROR on DELETE Associate", e);
        }

        return success;
    }

    /**
     * Validate entity
     *
     * @param value
     * @return valid
     */
    @Override
    public boolean validate(AssociatePU value) {
        return value != null && value.isValid() && validateEntity(value).isEmpty();
    }

    /**
     * Check violations
     *
     * @param obj
     * @param <T>
     * @return violations
     */
    public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }

    /**
     * Check if entity exists
     *
     * @param id
     * @return exist
     */
    public boolean exists(final String id) {
        return findById(id) != null;
    }

    /**
     * Add AssociateDTO
     *
     * @param value
     * @return response
     */
    public String add(final AssociateDTO value) throws Exception {
        String id = null;

        if (value == null) {
            throw new NullPointerException("Associado nulo para inserir");
        }

        if (!validateEntity(value).isEmpty()) {
            throw new ValidationException("Erro na validação do Associado para inserir");
        }

        if (exists(value.getId())) {
            throw new EntityExistsException("Associado já cadastrado para inserir");
        }

        if (findByIdentification(value.getIdentification()).stream().filter(p -> !p.getId().equalsIgnoreCase(value.getId())).count() > 0) {
            throw new ValidationException("CPF duplicado");
        }

        sendMessage.send(new Action<>(value, CRUD.CREATE));
        id = value.getId();

        return id;
    }

    /**
     * Update AssociateDTO
     *
     * @param value
     * @return response
     */
    public String update(final AssociateDTO value) throws Exception {
        String id = null;

        if (value == null) {
            throw new NullPointerException("Associado nulo para atualizar");
        }

        if (!validateEntity(value).isEmpty()) {
            throw new ValidationException("Erro na validação do Associado para atualizar");
        }

        if (!exists(value.getId())) {
            throw new EntityNotFoundException("Associado não cadastrado");
        }

        if (findByIdentification(value.getIdentification()).stream().filter(p -> !p.getId().equalsIgnoreCase(value.getId())).count() > 0) {
            throw new ValidationException("CPF duplicado");
        }

        sendMessage.send(new Action<>(value, CRUD.UPDATE));
        id = value.getId();

        return id;
    }

    /**
     * Remove AssociateDTO
     *
     * @param value
     * @return response
     */
    public String remove(final AssociateDTO value) throws Exception {
        String id = null;

        if (value == null) {
            throw new NullPointerException("Associado nulo para excluir");
        }

        if (!exists(value.getId())) {
            throw new EntityNotFoundException("Associado não existe para excluir");
        }

        sendMessage.send(new Action<>(value, CRUD.DELETE));
        id = value.getId();

        return id;
    }

}
