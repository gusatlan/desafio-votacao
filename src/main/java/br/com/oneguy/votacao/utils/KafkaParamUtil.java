package br.com.oneguy.votacao.utils;

import org.springframework.beans.factory.annotation.Value;

public class KafkaParamUtil {
    public static final  String TOPIC_ASSOCIATE= "votacao-associate";
    public static final  String TOPIC_MINUTE_MEETING = "votacao-minute";
    public static final  String TOPIC_POLL_MEETING = "votacao-poll";
    public static final  String TOPIC_VOTE = "votacao-vote";
    public static final  String GROUP_ID = "votacao";

}
