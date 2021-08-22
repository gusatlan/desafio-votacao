package br.com.oneguy.votacao.domain.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MinuteMeetingDTO extends BaseIdDTO implements Serializable {
    @JsonProperty("description")
    private String description = null;

    @JsonProperty("resume")
    private String resume = null;

    @JsonProperty("votosSim")
    private Long countAgree = null;

    @JsonProperty("votosNao")
    private Long countDisagree = null;

    @JsonProperty("votosTotal")
    private Long countTotal = null;

    /**
     * Constructor
     */
    public MinuteMeetingDTO() {
        this.description = null;
        this.resume = null;
        this.countAgree = null;
        this.countDisagree = null;
        this.countTotal = null;
    }

    /**
     * Constructor
     * @param id
     */
    public MinuteMeetingDTO(final String id) {
        super(id);
        this.description = null;
        this.resume = null;
        this.countAgree = null;
        this.countDisagree = null;
        this.countTotal = null;
    }

    /**
     * Constructor
     * @param id
     * @param description
     * @param resume
     */
    public MinuteMeetingDTO(final String id, final String description, final String resume) {
        super(id);
        setDescription(description);
        setResume(resume);
        this.countAgree = null;
        this.countDisagree = null;
        this.countTotal = null;
    }


    /*
     * Setters and Getters
     */

    /**
     * Return description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Return resume
     * @return resume
     */
    public String getResume() {
        return resume;
    }

    /**
     * Set resume
     * @param resume
     */
    public void setResume(final String resume) {
        this.resume = resume;
    }

    /**
     * Return countAgree
     * @return countAgree
     */
    public Long getCountAgree() {
        if(countAgree == null) {
            this.countAgree = 0L;
        }
        return countAgree;
    }

    /**
     * Set countAgree
     * @param countAgree
     */
    public void setCountAgree(final Long countAgree) {
        this.countAgree = countAgree != null && countAgree >= 0 ? countAgree : null;
    }

    /**
     * Return countDisagree
     * @return countDisagree
     */
    public Long getCountDisagree() {
        if(countDisagree == null) {
            this.countDisagree = 0L;
        }
        return countDisagree;
    }

    /**
     * Set countDisagree
     * @param countDisagree
     */
    public void setCountDisagree(Long countDisagree) {
        this.countDisagree = countDisagree != null && countDisagree >= 0 ? countDisagree : null;
    }

    /**
     * Return countTotal
     * @return countTotal
     */
    public Long getCountTotal() {
        if(countTotal == null) {
            this.countTotal = 0L;
        }
        return countTotal;
    }

    /**
     * Set countTotal
     * @param countTotal
     */
    public void setCountTotal(Long countTotal) {
        this.countTotal = countTotal != null && countTotal >= 0 ? countTotal : null;
    }
}
