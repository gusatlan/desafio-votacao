package br.com.oneguy.votacao.domain.dto.v1;

import br.com.oneguy.votacao.domain.persistence.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AssociateVoteDTO extends BaseIdDTO implements Serializable {
    @JsonProperty("sessaoId")
    private String pollMeetingId = null;

    @JsonProperty("associadoId")
    private String associateId = null;

    @JsonProperty("voto")
    private Vote vote = null;


    /**
     * Constructor
     */
    public AssociateVoteDTO() {
        this.pollMeetingId = null;
        this.associateId = null;
        this.vote = null;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return pollMeetingId
     * @return pollMeetingId
     */
    public String getPollMeetingId() {
        return pollMeetingId;
    }

    /**
     * Set pollMeetingId
     * @param pollMeetingId
     */
    public void setPollMeetingId(final String pollMeetingId) {
        this.pollMeetingId = pollMeetingId;
    }

    /**
     * Return associateId
     * @return associateId
     */
    public String getAssociateId() {
        return associateId;
    }

    /**
     * Set associateId
     * @param associateId
     */
    public void setAssociateId(final String associateId) {
        this.associateId = associateId;
    }

    /**
     * Return vote
     * @return vote
     */
    public Vote getVote() {
        return vote;
    }

    /**
     * Set vote
     * @param vote
     */
    public void setVote(final Vote vote) {
        this.vote = vote;
    }
}
