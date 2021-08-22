package br.com.oneguy.votacao.test.domain.persistence;

import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.domain.persistence.AssociateVotePU;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import br.com.oneguy.votacao.domain.persistence.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssociateVoteTest {

    /**
     * Test if valid
     */
    @Test
    public void shouldAssociateVoteValid(){
        AssociateVotePU obj = new AssociateVotePU(new AssociatePU(), Vote.AGREE);

        obj.getAssociate().setIdentification("12345678901");
        obj.setPoll(new PollMeetingPU());
        Assertions.assertTrue(obj.isValid());
    }

    /**
     * Test if invalid
     */
    @Test
    public void shouldAssociateVoteInvalid(){
        AssociateVotePU obj = new AssociateVotePU(new AssociatePU(), Vote.AGREE);

        obj.getAssociate().setIdentification("    ");
        obj.setPoll(new PollMeetingPU());
        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if both AssociatePU are same
     */
    @Test
    public void shouldAssociatesSame() {
        AssociateVotePU obj1 = new AssociateVotePU(new AssociatePU("123"), Vote.AGREE);
        AssociateVotePU obj2 = new AssociateVotePU(new AssociatePU("123"), Vote.DISAGREE);

        Assertions.assertTrue(obj1.same(obj2));
        Assertions.assertNotEquals(obj1, obj2);
        Assertions.assertNotEquals(obj1.getAssociate(), obj2.getAssociate());

        Assertions.assertNotEquals(obj1.getVote(), obj2.getVote());

        obj1.setVote(Vote.DISAGREE);
        Assertions.assertEquals(obj1.getVote(), obj2.getVote());
    }

    /**
     * Test if both AssociatePU are diff
     */
    @Test
    public void shouldAssociatesDiff() {
        AssociateVotePU obj1 = new AssociateVotePU(new AssociatePU("1234"), Vote.AGREE);
        AssociateVotePU obj2 = new AssociateVotePU(new AssociatePU("123"), Vote.DISAGREE);

        Assertions.assertFalse(obj1.same(obj2));
        Assertions.assertNotEquals(obj1, obj2);
        Assertions.assertNotEquals(obj1.getAssociate(), obj2.getAssociate());
    }

}
