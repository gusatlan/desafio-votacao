package br.com.oneguy.votacao.domain.persistence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="associado_voto", uniqueConstraints = @UniqueConstraint(name= "uc_associado_voto", columnNames = {"associado_id", "sessao_id"}))
public class AssociateVotePU extends BaseIdPU implements Serializable {

    @NotNull(message = "Associado não pode ser nulo")
    @ManyToOne
    @JoinColumn(name="associado_id")
    private AssociatePU associate = null;

    @NotNull(message = "Sessão pode ser nula")
    @ManyToOne
    @JoinColumn(name="sessao_id")
    private PollMeetingPU poll = null;

    @NotNull(message = "Voto não pode ser nulo")
    @Enumerated(EnumType.ORDINAL)
    @Column(name="voto", nullable = false)
    private Vote vote = null;

    /**
     * Constructor
     */
    public AssociateVotePU() {
        this.associate = null;
        this.poll = null;
        this.vote = null;
    }

    /**
     * Constructor
     * @param associate
     * @param vote
     */
    public AssociateVotePU(AssociatePU associate, Vote vote) {
        setAssociate(associate);
        setVote(vote);;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return associate
     * @return associate
     */
    public AssociatePU getAssociate() {
        return associate;
    }

    /**
     * Set associate
     * @param associate
     */
    public void setAssociate(AssociatePU associate) {
        this.associate = associate;
    }

    /**
     * Return poll
     * @return poll
     */
    public PollMeetingPU getPoll() {
        return poll;
    }

    /**
     * Set poll
     * @param poll
     */
    public void setPoll(PollMeetingPU poll) {
        this.poll = poll;
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
    public void setVote(Vote vote) {
        this.vote = vote;
    }

    /**
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getAssociate() != null && getAssociate().isValid() && getPoll() != null && getVote() != null;
    }

    /**
     * Check if same associate voting twice
     * @param other
     * @return same
     */
    public boolean same(final AssociateVotePU other) {
        return getAssociate() != null && getAssociate().same(other.getAssociate());
    }
}
