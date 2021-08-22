package br.com.oneguy.votacao.domain.dto.v1;

import br.com.oneguy.votacao.domain.persistence.Vote;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Locale;

public class AssociateVoteDTO extends BaseIdDTO implements Serializable {
    @JsonProperty("minuteMeetingId")
    private String minuteMeetingId = null;

    @JsonProperty("associateId")
    private String associateId = null;

    @JsonProperty("vote")
    private Vote vote = null;


    /**
     * Constructor
     */
    public AssociateVoteDTO() {
        this.minuteMeetingId = null;
        this.associateId = null;
        this.vote = null;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return minuteMeetingId
     *
     * @return minuteMeetingId
     */
    public String getMinuteMeetingId() {
        return minuteMeetingId;
    }

    /**
     * Set minuteMeetingId
     *
     * @param minuteMeetingId
     */
    public void setMinuteMeetingId(final String minuteMeetingId) {
        this.minuteMeetingId = minuteMeetingId != null && !minuteMeetingId.trim().isEmpty() ? minuteMeetingId.trim().toLowerCase() :null;
    }

    /**
     * Return associateId
     *
     * @return associateId
     */
    public String getAssociateId() {
        return associateId;
    }

    /**
     * Set associateId
     *
     * @param associateId
     */
    public void setAssociateId(final String associateId) {
        this.associateId = associateId != null && !associateId.trim().isEmpty() ? associateId.trim().toLowerCase() : null;
    }

    /**
     * Return vote
     *
     * @return vote
     */
    public Vote getVote() {
        return vote;
    }

    /**
     * Set vote
     *
     * @param vote
     */
    public void setVote(final Vote vote) {
        this.vote = vote;
    }
}
