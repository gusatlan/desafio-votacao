package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateVoteDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.domain.persistence.AssociateVotePU;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import br.com.oneguy.votacao.repositories.IAssociateRepository;
import br.com.oneguy.votacao.repositories.IAssociateVoteRepository;
import br.com.oneguy.votacao.repositories.IMinuteMeetingRepository;
import br.com.oneguy.votacao.repositories.IPollMeetingRepository;
import br.com.oneguy.votacao.utils.Action;
import br.com.oneguy.votacao.utils.CRUD;
import br.com.oneguy.votacao.utils.CpfState;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class AssociateVoteServiceImpl implements IAssociateVoteService {

    private final Logger logger;
    private final IValidatorService validator;
    private final ICpfValidatorService cpfValidatorService;
    private final IAssociateVoteRepository repository;
    private final IPollMeetingRepository pollMeetingRepository;
    private final IMinuteMeetingRepository minuteMeetingRepository;
    private final IAssociateRepository associateRepository;
    private final ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param logger
     * @param validator
     * @param cpfValidatorService
     * @param repository
     * @param pollMeetingRepository
     * @param minuteMeetingRepository
     * @param associateRepository
     * @param sendMessage
     */
    public AssociateVoteServiceImpl(
            final Logger logger,
            final IValidatorService validator,
            final ICpfValidatorService cpfValidatorService,
            final IAssociateVoteRepository repository,
            final IPollMeetingRepository pollMeetingRepository,
            final IMinuteMeetingRepository minuteMeetingRepository,
            final IAssociateRepository associateRepository,
            final ISendMessage sendMessage) {
        this.logger = logger;
        this.validator = validator;
        this.cpfValidatorService = cpfValidatorService;
        this.repository = repository;
        this.minuteMeetingRepository = minuteMeetingRepository;
        this.pollMeetingRepository = pollMeetingRepository;
        this.associateRepository = associateRepository;
        this.sendMessage = sendMessage;
    }

    /**
     * Return AssociateVote by id
     *
     * @param id
     * @return pollMeeting
     */
    @Override
    public AssociateVotePU findById(final String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Check if exists
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
    public boolean validate(AssociateVotePU value) {
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
     * Add Vote
     *
     * @param value
     * @return vote
     */
    @Override
    @Transactional
    public AssociateVotePU add(final AssociateVotePU value) {
        AssociateVotePU obj = value;

        try {
            if (value == null) {
                throw new NullPointerException("Voto nulo");
            }

            if (exists(value.getId())) {
                throw new EntityExistsException("Voto já existe");
            }

            if (value.getPoll() == null) {
                throw new NullPointerException("Sessão nula");
            }

            PollMeetingPU poll = pollMeetingRepository.getById(value.getPoll().getId());

            if (poll == null) {
                throw new EntityNotFoundException("Sessão não existe");
            }

            if (!poll.isOpen()) {
                throw new ValidationException("Sessão de voto encerrada");
            }

            if (value.getAssociate() == null || value.getAssociate().getId() == null) {
                throw new NullPointerException("Associado nulo");
            }

            AssociatePU associate = associateRepository.getById(value.getAssociate().getId());

            if (associate == null) {
                throw new EntityNotFoundException("Associado não existe");
            }

            // Commented because the REST Service of Cpf validation is not indepotent
            // Comentado por causo do validador de CPF não ser indepotente, gerando saídas diferentes para o mesmo CPF

            /*
            CpfState cpfState = cpfValidatorService.checkCpf(associate.getIdentification());

            if (cpfState.equals(CpfState.NOT_FOUND)) {
                throw new EntityNotFoundException("CPF inexistente");
            }

            if (cpfState.equals(CpfState.UNABLE)) {
                throw new ValidationException("CPF não permitido para votar");
            }
             */

            if (poll.getVotes()
                    .stream()
                    .anyMatch(p -> p.getAssociate().getId().equalsIgnoreCase(value.getAssociate().getId()) ||
                            p.getAssociate().getIdentification().equalsIgnoreCase(value.getAssociate().getIdentification()))) {
                throw new EntityExistsException("Associado já votou na sessão");
            }

            if (value.getVote() == null) {
                throw new ValidationException("Voto do associado nulo");
            }

            obj.setAssociate(associate);
            obj.setPoll(poll);
            obj = repository.save(obj);
        } catch (Exception e) {
            logger.warn("Vote not be able to compute {} vote: {}, associate: {}, poll: {}", e,
                    obj,
                    obj.getAssociate() != null ? obj.getAssociate() : null,
                    obj.getPoll() != null ? obj.getPoll() : null);
            throw e;
        }

        return obj;
    }

    /**
     * Add Vote
     *
     * @param value
     * @return vote
     */
    @Override
    @Transactional
    public String add(final AssociateVoteDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Voto nulo");
            }

            // If not setted id, set
            if (value.getId() == null) {
                value.setId(new AssociateVotePU().getId());
            }

            if (!validator.validate(value)) {
                throw new ValidationException("Voto inválido");
            }

            if (exists(value.getId())) {
                throw new EntityExistsException("Voto já existe");
            }

            if (value.getMinuteMeetingId() == null) {
                throw new NullPointerException("Ata nula");
            }

            MinuteMeetingPU minuteMeeting = minuteMeetingRepository.getById(value.getMinuteMeetingId());

            if (minuteMeeting == null) {
                throw new EntityNotFoundException("Ata não existe");
            }

            PollMeetingPU poll = minuteMeeting.getPoll();

            if (poll == null) {
                throw new EntityNotFoundException("Sessão não existe");
            }

            if (!poll.isOpen()) {
                throw new ValidationException("Sessão de voto encerrada");
            }

            if (value.getAssociateId() == null) {
                throw new NullPointerException("Associado nulo");
            }

            AssociatePU associate = associateRepository.getById(value.getAssociateId());

            if (associate == null) {
                throw new EntityNotFoundException("Associado não existe");
            }

            CompletableFuture<CpfState> cpfState = CompletableFuture.supplyAsync(() -> cpfValidatorService.checkCpf(associate.getIdentification()));

            if (poll.getVotes()
                    .stream()
                    .anyMatch(p -> p.getAssociate().getId().equalsIgnoreCase(value.getAssociateId()))) {
                throw new EntityExistsException("Associado já votou na sessão");
            }

            if (value.getVote() == null) {
                throw new ValidationException("Voto do associado nulo");
            }

            if (cpfState.get(20, TimeUnit.SECONDS).equals(CpfState.NOT_FOUND)) {
                throw new EntityNotFoundException("CPF inexistente");
            }

            if (cpfState.get(20, TimeUnit.SECONDS).equals(CpfState.UNABLE)) {
                throw new ValidationException("CPF não permitido para votar");
            }

            id = value.getId();
            sendMessage.send(new Action<>(value, CRUD.CREATE));
        } catch (Exception e) {
            logger.warn("Vote not be able to compute {} vote: {}, associate: {}, minute: {}", e,
                    value.getId(),
                    value.getAssociateId(),
                    value.getMinuteMeetingId());
            throw e;
        }

        return id;
    }

    /**
     * Convert AssociateVoteDTO to AssociateVotePU
     *
     * @param value
     * @return associateVote
     */
    @Transactional
    public AssociateVotePU convert(final AssociateVoteDTO value) {
        AssociateVotePU obj = new AssociateVotePU();

        try {
            obj.setId(value.getId());
            obj.setAssociate(associateRepository.getById(value.getAssociateId()));
            obj.setPoll(minuteMeetingRepository.getById(value.getMinuteMeetingId()).getPoll());
            obj.setVote(value.getVote());

        } catch (Exception e) {
            obj = null;
            logger.error("ERROR on CONVERT AssociateVoteDTO to AssociateVotePU", e);
        }

        return obj;
    }

}
