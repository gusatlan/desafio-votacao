package br.com.oneguy.votacao.test.services;

import br.com.oneguy.votacao.domain.persistence.*;
import br.com.oneguy.votacao.services.IAssociateVoteService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateVoteServiceTest {

    @Mock
    private IAssociateVoteService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Vote on poll")
    @Test
    public void shouldCreateAssociateVote() throws Exception {
        MinuteMeetingPU minute = new MinuteMeetingPU("001", "Description 01", "Resume 01");
        AssociatePU associate = new AssociatePU("A01", "12345678901");
        PollMeetingPU poll = new PollMeetingPU();
        AssociateVotePU vote = new AssociateVotePU();

        poll.setMinuteMeeting(minute);
        poll.setId("000001");
        poll.getBeginAt();
        poll.adjustDuration(5L);

        minute.setPoll(poll);

        vote.setId("V01");
        vote.setAssociate(associate);
        vote.setVote(Vote.AGREE);
        poll.addVote(vote);

        vote.getPoll().updateVotes();

        when(service.add(any(AssociateVotePU.class))).thenReturn(vote);

        AssociateVotePU saved = service.add(vote);

        Assertions.assertEquals(saved.getId(), vote.getId());
        Assertions.assertEquals(saved.getAssociate(), associate);
        Assertions.assertEquals(saved.getPoll(), poll);
        Assertions.assertTrue(saved.getPoll().isOpen());

        Assertions.assertEquals(1L, vote.getPoll().getVotesTotal());
        Assertions.assertEquals(1L, vote.getPoll().getVotesAgrees());
        Assertions.assertNotEquals(1L, vote.getPoll().getVotesDisagrees());

    }

}
