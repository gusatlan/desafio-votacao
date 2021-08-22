package br.com.oneguy.votacao.domain.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PollMeetingDTO extends BaseIdDTO implements Serializable {
    @JsonProperty("minuteMeetingId")
    private String minuteMeetingId = null;

    @JsonProperty("duration")
    private Long duration = null;

    /**
     * Constructor
     */
    public PollMeetingDTO() {
        this.minuteMeetingId = null;
        this.duration = null;
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
    public void setMinuteMeetingId(String minuteMeetingId) {
        this.minuteMeetingId = minuteMeetingId != null && !minuteMeetingId.trim().isEmpty() ? minuteMeetingId.trim().toLowerCase() : null;
    }

    /**
     * Return duration
     *
     * @return duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * Set duration
     *
     * @param duration
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
