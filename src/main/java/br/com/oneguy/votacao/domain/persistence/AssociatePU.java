package br.com.oneguy.votacao.domain.persistence;

import br.com.oneguy.votacao.utils.StringUtil;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "votacao_associado", uniqueConstraints = @UniqueConstraint(name="uc_associado", columnNames = {"cpf"}))
public class AssociatePU extends BaseIdPU implements Serializable {
    @NotNull
    @Size(max = 11, message = "Tamanho máximo de CPF ultrapassado")
    @Column(name = "cpf", length = 11, nullable = true)
    private String identification = null;

    /**
     * Constructor
     */
    public AssociatePU() {
        this.identification = null;
    }

    /**
     * Constructor
     *
     * @param identification
     */
    public AssociatePU(final String identification) {
        setIdentification(identification);
    }

    /**
     * Constructor
     * @param id
     * @param identification
     */
    public AssociatePU(final String id, final String identification) {
        super(id);
        setIdentification(identification);
    }

    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\", \"identification\": \"%s\"}", getId(), getIdentification());
    }

    /**
     * Return identification
     *
     * @return identification
     */
    public String getIdentification() {
        return identification;
    }

    /**
     * Set identification
     *
     * @param identification
     */
    public void setIdentification(final String identification) {
        this.identification = identification != null && !identification.trim().isEmpty() ? StringUtil.clean(identification) : null;
    }

    /**
     * Check if valid
     *
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getIdentification() != null;
    }

    /**
     * Check if sames
     *
     * @param other
     * @return same
     */
    public boolean same(final AssociatePU other) {
        return other != null &&
                other.getIdentification() != null &&
                getIdentification() != null &&
                other.getIdentification().equalsIgnoreCase(getIdentification());
    }

}
