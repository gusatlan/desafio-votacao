package br.com.oneguy.votacao.domain.persistence;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Vote {
    AGREE(true, "Sim"), DISAGREE(false, "Não");

    private Boolean agree = null;
    private String description = null;

    Vote(final boolean agree, final String description) {
        setAgree(agree);
        setDescription(description);
    }

    /**
     * Return agree (true or false)
     * @return agree
     */
    public Boolean getAgree() {
        return agree;
    }

    /**
     * Set agree
     * @param agree
     */
    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    /**
     * Return description
     * @return description
     */
    @JsonValue
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
