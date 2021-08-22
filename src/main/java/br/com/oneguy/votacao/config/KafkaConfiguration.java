package br.com.oneguy.votacao.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

import static br.com.oneguy.votacao.utils.KafkaParamUtil.*;

@EnableKafka
@Configuration
public class KafkaConfiguration {
    @Value("${kafka.hosts:localhost:9092}")
    private String hosts;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        return new KafkaAdmin(configs);
    }

    /**
     * Topic associate
     *
     * @return topicAssociate
     */
    @Bean
    public NewTopic kafkaTopicAssociate() {
        return new NewTopic(TOPIC_ASSOCIATE, 1, (short) 1);
    }

    /**
     * Topic minuteMeeting
     *
     * @return topicMinuteMeeting
     */
    @Bean
    public NewTopic kafkaTopicMinuteMeeting() {
        return new NewTopic(TOPIC_MINUTE_MEETING, 1, (short) 1);
    }

    /**
     * Topic pollMeeting
     *
     * @return topicPollMeeting
     */
    @Bean
    public NewTopic kafkaTopicPollMeeting() {
        return new NewTopic(TOPIC_POLL_MEETING, 1, (short) 1);
    }

    /**
     * Topic vote
     *
     * @return topicVote
     */
    @Bean
    public NewTopic kafkaTopicVote() {
        return new NewTopic(TOPIC_VOTE, 1, (short) 1);
    }

    /**
     * Create producer factory
     *
     * @return factory
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Create template
     *
     * @return template
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Create consumer factory
     *
     * @return
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Create listener consumer
     * @return listenerConsumer
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
