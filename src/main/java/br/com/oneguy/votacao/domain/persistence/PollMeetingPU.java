package br.com.oneguy.votacao.domain.persistence;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

@Entity
@Table(name="votacao_sessao", uniqueConstraints = @UniqueConstraint(name="uc_votacao_sessao", columnNames = {"ata_id", "inicio_sessao"}))
public class PollMeetingPU extends BaseIdPU implements Serializable, Comparable<PollMeetingPU> {

    @NotNull
    @Column(name="inicio_sessao", nullable = false)
    private ZonedDateTime beginAt = null;

    @Column(name="termino_sessao")
    private ZonedDateTime endAt = null;

    @NotNull(message = "Ata inválida")
    @OneToOne
    @JoinColumn(name="ata_id")
    private MinuteMeetingPU minuteMeeting = null;

    @OneToMany(mappedBy = "poll", orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<AssociateVotePU> votes = null;

    @Column(name="votos_sim")
    private Long votesAgrees = null;

    @Column(name="votos_nao")
    private Long votesDisagrees = null;

    @Column(name="votos_total")
    private Long votesTotal = null;

    /**
     * Constructor
     */
    public PollMeetingPU() {
        this.beginAt = null;
        this.endAt = null;
        this.minuteMeeting = null;
        this.votes = null;
        this.votesAgrees = null;
        this.votesDisagrees = null;
        votesTotal = null;
    }

    /**
     * Destroy Event
     */
    @PreDestroy
    public void release() {
        if(votes != null) {
            this.votes.clear();
            this.votes = null;
        }
    }

    @PrePersist
    @PreUpdate
    public void updateVotes() {
        setVotesAgrees(countAgrees());
        setVotesDisagrees(countDisagrees());
        setVotesTotal(countTotal());
    }

    @Override
    public int compareTo(final PollMeetingPU other) {
        int compare = -1;

        if(other != null) {
            int[] compares = {
                    getMinuteMeeting() != null && other.getMinuteMeeting() != null ? getMinuteMeeting().getId().compareToIgnoreCase(other.getMinuteMeeting().getId()) : 1,
                    getBeginAt() != null && other.getBeginAt() != null ? getBeginAt().compareTo(other.getBeginAt()) : 1
            };

            for(int c: compares) {
                compare = c;
                if(c != 0) {
                    break;
                }
            }
        }

        return compare;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return beginAt
     * @return beginAt
     */
    public ZonedDateTime getBeginAt() {
        if(beginAt == null) {
            this.beginAt = ZonedDateTime.now();
        }

        return beginAt;
    }

    /**
     * Set beginAt
     * @param beginAt
     */
    public void setBeginAt(final ZonedDateTime beginAt) {
        this.beginAt = beginAt;
    }

    /**
     * Return endAt
     * @return endAt
     */
    public ZonedDateTime getEndAt() {
        return endAt;
    }

    /**
     * Set endAt
     * @param endAt
     */
    public void setEndAt(final ZonedDateTime endAt) {
        this.endAt = endAt;
    }

    /**
     * Return minuteMeeting
     * @return minuteMeeting
     */
    public MinuteMeetingPU getMinuteMeeting() {
        return minuteMeeting;
    }

    /**
     * Set minuteMeeting
     * @param minuteMeeting
     */
    public void setMinuteMeeting(MinuteMeetingPU minuteMeeting) {
        this.minuteMeeting = minuteMeeting;
    }

    /**
     * Return votes
     * @return votes
     */
    public Collection<AssociateVotePU> getVotes() {
        if(votes == null) {
            this.votes = new HashSet<>();
        }
        return votes;
    }

    /**
     * Set votes
     * @param votes
     */
    public void setVotes(Collection<AssociateVotePU> votes) {
        this.votes = votes;
    }

    /**
     * Return votesAgrees
     * @return votesAgrees
     */
    public Long getVotesAgrees() {
        if(votesAgrees == null) {
            this.votesAgrees = 0L;
        }

        return votesAgrees;
    }

    /**
     * Set votesAgrees
     * @param votesAgrees
     */
    public void setVotesAgrees(final Long votesAgrees) {
        this.votesAgrees = votesAgrees != null && votesAgrees >= 0L ? votesAgrees : 0L;
    }

    /**
     * Return votesDisagrees
     * @return votesDisagrees
     */
    public Long getVotesDisagrees() {
        if(votesDisagrees == null) {
            this.votesDisagrees = 0L;
        }

        return votesDisagrees;
    }

    /**
     * Set votesDisagrees
     * @param votesDisagrees
     */
    public void setVotesDisagrees(final Long votesDisagrees) {
        this.votesDisagrees = votesDisagrees != null && votesDisagrees >= 0L ? votesDisagrees : 0L;
    }

    /**
     * Return votesTotal
     * @return votesTotal
     */
    public Long getVotesTotal() {
        if(votesTotal == null) {
            this.votesTotal = 0L;
        }
        return votesTotal;
    }

    /**
     * Set votesTotal
     * @param votesTotal
     */
    public void setVotesTotal(final Long votesTotal) {
        this.votesTotal = votesTotal != null && votesTotal >= 0L ? votesTotal : 0L;
    }

    /**
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getMinuteMeeting() != null && getMinuteMeeting().isValid() && (getEndAt() == null || (getEndAt().isAfter(getBeginAt())));
    }

    @Transient
    public boolean isOpen() {
        ZonedDateTime now = ZonedDateTime.now();
        return isValid() && getEndAt() != null && !now.isBefore(getBeginAt()) && !now.isAfter(getEndAt());
    }

    /**
     * Stream of valid AssociateVotePU
     * @return stream of votes
     */
    private Stream<AssociateVotePU> validVotes() {
        return getVotes().stream().filter(p -> p != null && p.isValid() && p.getPoll().equals(this));
    }

    /**
     * Compute votes, if type is null return null
     * @param type
     * @return count
     */
    private Long computeVotes(final Vote type) {
        Long count = null;

        if(type != null) {
            count = validVotes().filter(p -> p.getVote().equals(type)).count();
        }

        return count;
    }

    /**
     * Compute agrees
     * @return count
     */
    public Long countAgrees() {
        return computeVotes(Vote.AGREE);
    }

    /**
     * Compute disagrees
     * @return count
     */
    public Long countDisagrees() {
        return computeVotes(Vote.DISAGREE);
    }

    /**
     * Compute total votes
     * @return total votes
     */
    public Long countTotal() {
        return validVotes().count();
    }

    /**
     * Check if already voted
     * @param value
     * @return voted
     */
    public boolean alreadyVoted(final AssociatePU value) {
        return value != null && validVotes().filter(p -> p != null && p.getAssociate() != null && p.getAssociate().equals(value)).count() > 0;
    }

    /**
     * Add a vote
     * @param value
     * @return sucess
     */
    public boolean addVote(AssociateVotePU value) {
        boolean sucess = isOpen() && value != null && value.getAssociate() != null && value.getAssociate().isValid() && !alreadyVoted(value.getAssociate());

        if(sucess) {
            value.setPoll(this);
            getVotes().add(value);
        }

        return sucess;
    }

    /**
     * Add a vote
     * @param associate
     * @param vote
     * @return sucess
     */
    public boolean addVote(final AssociatePU associate, final Vote vote) {
        AssociateVotePU obj = new AssociateVotePU();

        obj.setAssociate(associate);
        obj.setVote(vote);

        return addVote(obj);
    }

    /**
     * Set end date
     * @param minutes
     */
    public void adjustDuration(final Long minutes) {
        setEndAt(getBeginAt().plusMinutes(minutes != null && minutes >0 ? minutes : 1L));
    }
}
