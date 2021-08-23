package br.com.oneguy.votacao.domain.dto.v1;

import br.com.oneguy.votacao.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AssociateDTO extends BaseIdDTO implements Serializable {

    @NotBlank(message="CPF obrigatório")
    @NotNull(message="CPF não pode ser nulo")
    @JsonProperty("identification")
    private String identification = null;

    /**
     * Constructor
     */
    public AssociateDTO() {
        this.identification = null;
    }

    /**
     * Constructor
     * @param identification
     */
    public AssociateDTO(final String identification) {
        setIdentification(identification);
    }

    /**
     * Constructor
     * @param id
     * @param identification
     */
    public AssociateDTO(final String id, final String identification) {
        super(id);
        setIdentification(identification);
    }

    /*
     * Setters and Getters
     */

    /**
     * Return identification
     * @return identification
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * Set identification
     * @param identification
     */
    public void setIdentification(String identification) {
        this.identification = identification != null && !identification.trim().isEmpty() ? StringUtil.clean(identification) : null;
    }
}
