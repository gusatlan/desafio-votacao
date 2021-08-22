package br.com.oneguy.votacao.utils;

public class ApplicationConstants {

    public static final String ENDPOINT_ROOT_V1 = "/api/v1";
    public static final String ENDPOINT_ASSOCIATE_V1 = ENDPOINT_ROOT_V1 + "/associate";
    public static final String ENDPOINT_MINUTE_MEETING_V1 = ENDPOINT_ROOT_V1 + "/minutemeeting";
    public static final String ENDPOINT_POLL_MEETING_V1 = ENDPOINT_ROOT_V1 + "/pollmeeting";
    public static final String ENDPOINT_VOTE_V1 = ENDPOINT_ROOT_V1 + "/vote";

    public static final  String TOPIC_ASSOCIATE= "votacao-associate";
    public static final  String TOPIC_MINUTE_MEETING = "votacao-minute";
    public static final  String TOPIC_POLL_MEETING = "votacao-poll";
    public static final  String TOPIC_VOTE = "votacao-vote";
    public static final  String GROUP_ID = "votacao";

}
