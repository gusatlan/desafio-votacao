package br.com.oneguy.votacao.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Error implements Serializable {
    @JsonProperty("mensagem")
    private String message = null;

    /**
     * Constructor
     */
    public Error() {
        this.message = null;
    }

    /**
     * Constructor
     * @param message
     */
    public Error(final String message) {
        setMessage(message);
    }

    /*
     * Setters and Getters
     */

    /**
     * Return message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message != null && !message.trim().isEmpty() ? message.trim() : "Erro não esperado";
    }

}
