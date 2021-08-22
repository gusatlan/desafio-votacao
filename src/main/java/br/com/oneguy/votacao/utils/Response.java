package br.com.oneguy.votacao.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Response<T> {
    @JsonProperty("response")
    private T response = null;

    @JsonProperty("errors")
    private Collection<Error> errors = null;

    @JsonProperty("status")
    private HttpStatus status = null;

    /**
     * Constructor
     */
    public Response() {
        this.response = null;
        this.errors = null;
        this.status = null;
    }

    /**
     * Constructor
     * @param response
     * @param errors
     * @param status
     */
    public Response(final T response, final Collection<Error> errors, final HttpStatus status) {
        setResponse(response);
        setErrors(errors);
        setStatus(status);
    }

    /*
     * Setters and Getters
     */

    /**
     * Return response
     * @return response
     */
    public T getResponse() {
        return response;
    }

    /**
     * Set response
     * @param response
     */
    public void setResponse(final T response) {
        this.response = response;
    }

    /**
     * Return errors
     * @return errors
     */
    public Collection<Error> getErrors() {
        return errors;
    }

    /**
     * Set errors
     * @param errors
     */
    public void setErrors(final Collection<Error> errors) {
        this.errors = errors;
    }

    /**
     * Return statusCode
     * @return statusCode
     */
    public HttpStatus getStatus() {
        if(status == null) {
            if(getErrors() == null || getErrors().isEmpty()) {
                this.status = HttpStatus.OK;
            } else {
                this.status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return status;
    }

    /**
     * Set status
     * @param status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return getStatus().value();
    }

    /**
     * Convert errors messages to Errors
     * @param messages
     */
    public void addMessages(final Collection<String> messages) {
        if(messages != null && !messages.isEmpty()) {
            setErrors(messages
                    .stream()
                    .filter(p -> p != null && !p.trim().isEmpty())
                    .map(p -> new Error(p))
                    .collect(Collectors.toSet()));
        }
    }

    /**
     * Add message as Error
     * @param message
     */
    public void addMessage(final String message) {
        if(message != null && !message.trim().isEmpty()) {
            if(getErrors() == null) {
                setErrors(new HashSet<>());
            }

            getErrors().add(new Error(message));
        }
    }

}
