package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.dto.v1.AssociateVoteDTO;
import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.utils.Action;
import br.com.oneguy.votacao.utils.CRUD;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.oneguy.votacao.utils.ApplicationConstants.*;

@Component
public class KafkaConsumerMessageImpl implements IConsumerMessage {

    private final ObjectMapper mapper;
    private final Logger logger;
    private final IAssociateService associateService;
    private final IMinuteMeetingService minuteMeetingService;
    private final IPollMeetingService pollMeetingService;
    private final IAssociateVoteService associateVoteService;

    /**
     * Constructor
     * @param mapper
     * @param logger
     * @param associateService
     * @param minuteMeetingService
     * @param pollMeetingService
     * @param associateVoteService
     */
    public KafkaConsumerMessageImpl(final ObjectMapper mapper,
                                    final Logger logger,
                                    final IAssociateService associateService,
                                    final IMinuteMeetingService minuteMeetingService,
                                    final IPollMeetingService pollMeetingService,
                                    final IAssociateVoteService associateVoteService) {
        this.mapper = mapper;
        this.logger = logger;
        this.associateService = associateService;
        this.minuteMeetingService = minuteMeetingService;
        this.pollMeetingService = pollMeetingService;
        this.associateVoteService = associateVoteService;
    }

    @KafkaListener(topics = {TOPIC_ASSOCIATE, TOPIC_MINUTE_MEETING, TOPIC_POLL_MEETING, TOPIC_VOTE}, groupId = GROUP_ID)
    public void consume(@Payload final String message, @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic) {
        logger.info("Received from topic: {}, message: {}", topic, message);

        switch (topic) {
            case TOPIC_ASSOCIATE:
                handleAssociate(buildAction(message, AssociateDTO.class));
            break;
            case TOPIC_MINUTE_MEETING:
                handleMinuteMeeting(buildAction(message, MinuteMeetingDTO.class));
                break;
            case TOPIC_POLL_MEETING:
                handlePollMeeting(buildAction(message, PollMeetingDTO.class));
                break;
            case TOPIC_VOTE:
                handleVote(buildAction(message, AssociateVoteDTO.class));
                break;
        }
    }

    /**
     * Build action from json
     * @param message
     * @param clazz
     * @param <T>
     * @return action
     */
    private <T> Action<T> buildAction(final String message, final Class<T> clazz) {
        Action<T> action = new Action<>();

        try {
            JsonNode root = mapper.readTree(message);

            action.setObj(mapper.treeToValue(root.at("/obj"), clazz));
            action.setAction(mapper.treeToValue(root.at("/action"), CRUD.class));
        } catch(Exception e) {
            action = null;
            logger.error("Não foi possível descobrir a Action", e);
        }

        return action;
    }

    /**
     * Handle actions for Associate
     * @param action
     */
    private void handleAssociate(final Action<AssociateDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    associateService.add(new AssociatePU(action.getObj().getId(), action.getObj().getIdentification()));
                    break;
                case UPDATE:
                    associateService.update(new AssociatePU(action.getObj().getId(), action.getObj().getIdentification()));
                    break;
                case DELETE:
                    associateService.remove(action.getObj().getId());
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on AssociateDTO", e);
        }
    }

    /**
     * Handle actions for MinuteMeeting
     * @param action
     */
    private void handleMinuteMeeting(final Action<MinuteMeetingDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    minuteMeetingService.add(new MinuteMeetingPU(action.getObj().getId(), action.getObj().getDescription(), action.getObj().getResume()));
                    break;
                case UPDATE:
                    minuteMeetingService.update(new MinuteMeetingPU(action.getObj().getId(), action.getObj().getDescription(), action.getObj().getResume()));
                    break;
                case DELETE:
                    minuteMeetingService.remove(action.getObj().getId());
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on MinuteMeetingDTO", e);
        }
    }

    /**
     * Handle actions for PollMeeting
     * @param action
     */
    private void handlePollMeeting(final Action<PollMeetingDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    pollMeetingService.add(pollMeetingService.convert(action.getObj()));
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on PollMeetingDTO", e);
        }
    }

    /**
     * Handle actions for AssociateVote
     * @param action
     */
    private void handleVote(final Action<AssociateVoteDTO> action) {

        try {
            switch (action.getAction()) {
                case CREATE:
                    associateVoteService.add(associateVoteService.convert(action.getObj()));
                    break;
            }

        } catch(Exception e) {
            logger.error("ERROR on AssociateVoteDTO", e);
        }
    }
}
