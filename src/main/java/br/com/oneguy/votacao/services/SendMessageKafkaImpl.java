package br.com.oneguy.votacao.services;

import br.com.oneguy.votacao.domain.dto.v1.*;
import br.com.oneguy.votacao.utils.Action;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static br.com.oneguy.votacao.utils.KafkaParamUtil.*;

@Service
public class SendMessageKafkaImpl implements ISendMessage {
    private ObjectMapper mapper = null;
    private final Logger logger;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor
     * @param kafkaTemplate
     * @param logger
     */
    @Autowired
    public SendMessageKafkaImpl(final KafkaTemplate<String, String> kafkaTemplate, final Logger logger) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = new ObjectMapper();
        this.logger = logger;
    }

    /**
     * Send message to the topic
     * @param <T>
     * @param value
     * @throws Exception
     */
    public <T extends BaseIdDTO> void send(final Action<T> value) throws Exception {
        String message = mapper.writeValueAsString(value);
        String topic = null;

        if(value.getObj() instanceof AssociateDTO) {
            topic = TOPIC_ASSOCIATE;
        } else if(value.getObj() instanceof MinuteMeetingDTO) {
            topic = TOPIC_MINUTE_MEETING;
        } else if(value.getObj() instanceof PollMeetingDTO) {
            topic = TOPIC_POLL_MEETING;
        } else if(value.getObj() instanceof AssociateVoteDTO) {
            topic = TOPIC_VOTE;
        }

        kafkaTemplate.send(topic, message);
        logger.info("Sending message to Kafka: topic:{}, message:{}", topic, message);
    }
}
