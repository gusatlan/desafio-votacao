package br.com.oneguy.votacao.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="votacao_ata")
public class MinuteMeetingPU extends BaseIdPU implements Serializable {
    @Column(name="descricao", length = 50)
    private String description = null;

    @Column(name="resumo", length = 255)
    private String resume = null;

    @OneToOne(mappedBy = "minuteMeeting", orphanRemoval = true, fetch = FetchType.EAGER)
    private PollMeetingPU poll = null;

    /**
     * Constructor
     */
    public MinuteMeetingPU() {
        this.description = null;
        this.resume = null;
        this.poll = null;
    }

    /**
     * Constructor
     * @param description
     * @param resume
     */
    public MinuteMeetingPU(final String description, final String resume) {
        setDescription(description);
        setResume(resume);
        this.poll = null;
    }

    /**
     * Constructor
     * @param id
     * @param description
     * @param resume
     */
    public MinuteMeetingPU(final String id, final String description, final String resume) {
        super(id);
        setDescription(description);
        setResume(resume);
        this.poll = null;
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
        this.description = description != null && !description.trim().isEmpty() ? description.trim() : null;
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
        this.resume = resume != null && !resume.trim().isEmpty() ? resume.trim() : null;
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
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getId() != null && getDescription() != null;
    }

    /**
     * Create a poll
     * @param durationMinutes
     * @return poll
     */
    public PollMeetingPU createPoll(final Long durationMinutes) {
        PollMeetingPU obj = new PollMeetingPU();

        obj.setMinuteMeeting(this);
        obj.setEndAt(obj.getBeginAt().plusMinutes(durationMinutes != null ? durationMinutes : 1L));
        setPoll(obj);

        return obj;
    }
}
