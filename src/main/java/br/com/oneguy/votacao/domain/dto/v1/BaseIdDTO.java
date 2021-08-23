package br.com.oneguy.votacao.domain.dto.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public class BaseIdDTO implements Serializable {
    @JsonProperty("id")
    private String id = null;

    /**
     * Constructor
     */
    public BaseIdDTO() {
        this.id = null;
    }

    /**
     * Constructor
     *
     * @param id
     */
    public BaseIdDTO(final String id) {
        setId(id);
    }

    @Override
    public boolean equals(final Object other) {
        return other != null && other instanceof  BaseIdDTO && getId().equalsIgnoreCase(((BaseIdDTO) other).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\"}", getId());
    }

    /*
     * Setters and Getters
     */

    /**
     * Return id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(String id) {
        this.id = id != null && !id.trim().isEmpty() ? id.trim().toLowerCase() : null;
    }
}
