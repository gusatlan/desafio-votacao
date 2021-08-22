package br.com.oneguy.votacao.test.domain.persistence;

import br.com.oneguy.votacao.domain.persistence.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PollMeetingTest {

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        PollMeetingPU obj = new PollMeetingPU();

        Assertions.assertFalse(obj.isValid());
        obj.setMinuteMeeting(new MinuteMeetingPU());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
        obj.getMinuteMeeting().setDescription("Description");

        Assertions.assertTrue(obj.isValid());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeInvalid() {
        PollMeetingPU obj = new PollMeetingPU();

        Assertions.assertFalse(obj.isValid());
        obj.setMinuteMeeting(new MinuteMeetingPU());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if session open
     */
    @Test
    public void shouldBeOpen() {
        PollMeetingPU obj = new PollMeetingPU();

        Assertions.assertFalse(obj.isValid());
        obj.setMinuteMeeting(new MinuteMeetingPU());

        Assertions.assertFalse(obj.isValid());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));

        Assertions.assertFalse(obj.isValid());
        obj.getMinuteMeeting().setDescription("Description");

        Assertions.assertTrue(obj.isValid());
        Assertions.assertTrue(obj.isOpen());
    }

    /**
     * Test vote, a Associate dont vote twice
     */
    @Test
    public void shouldBeVoted() {
        PollMeetingPU obj = new PollMeetingPU();
        AssociatePU duplicate = new AssociatePU("123");

        obj.setMinuteMeeting(new MinuteMeetingPU());
        obj.setEndAt(obj.getBeginAt().plusMinutes(1L));
        obj.getMinuteMeeting().setDescription("Description");

        obj.addVote(duplicate, Vote.AGREE);
        obj.addVote(duplicate, Vote.AGREE);

        obj.addVote(new AssociatePU("1"), Vote.AGREE);
        obj.addVote(new AssociatePU("2"), Vote.AGREE);

        obj.addVote(new AssociatePU("3"), Vote.DISAGREE);
        obj.addVote(new AssociatePU("4"), Vote.DISAGREE);
        obj.addVote(new AssociatePU("5"), Vote.DISAGREE);
        obj.addVote(new AssociatePU("6"), Vote.DISAGREE);

        Assertions.assertEquals(4L, obj.countDisagrees());
        Assertions.assertEquals(3L, obj.countAgrees());
        Assertions.assertEquals(obj.countAgrees() + obj.countDisagrees(), obj.countTotal());
    }

    /**
     * Test vote, a Associate dont vote when the poll is not open
     */
    @Test
    public void shouldBeNotVoteWhenClosed() {
        PollMeetingPU obj = new PollMeetingPU();

        obj.setMinuteMeeting(new MinuteMeetingPU());
        obj.setEndAt(null);
        obj.getMinuteMeeting().setDescription("Description");

        obj.addVote(new AssociatePU("1"), Vote.AGREE);
        obj.addVote(new AssociatePU("2"), Vote.AGREE);
        obj.addVote(new AssociatePU("3"), Vote.DISAGREE);
        obj.addVote(new AssociatePU("4"), Vote.AGREE);

        Assertions.assertTrue(obj.getVotes().isEmpty());
    }
}
