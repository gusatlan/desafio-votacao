package br.com.oneguy.votacao.test.domain.dto;

import br.com.oneguy.votacao.domain.dto.v1.AssociateVoteDTO;
import br.com.oneguy.votacao.domain.persistence.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssociateVoteDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldAssociateVoteNotValid() {
        String id = "1";
        String associateId = "ASSC001";
        String minuteMeetingId = "MIN001";

        AssociateVoteDTO obj = new AssociateVoteDTO();

        obj.setId(id);
        obj.setAssociateId(associateId);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setVote(Vote.AGREE);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertNotEquals(associateId, obj.getAssociateId());
        Assertions.assertNotEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertEquals(Vote.AGREE, obj.getVote());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldAssociateVoteValid() {
        String id = "1";
        String associateId = "ASSC001".toLowerCase();
        String minuteMeetingId = "MIN001".toLowerCase();

        AssociateVoteDTO obj = new AssociateVoteDTO();

        obj.setId(id);
        obj.setAssociateId(associateId);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setVote(Vote.AGREE);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(associateId, obj.getAssociateId());
        Assertions.assertEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertEquals(Vote.AGREE, obj.getVote());
    }

}
