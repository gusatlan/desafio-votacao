package br.com.oneguy.votacao.test.services;

import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.domain.persistence.PollMeetingPU;
import br.com.oneguy.votacao.services.IPollMeetingService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PollMeetingServiceTest {

    @Mock
    private IPollMeetingService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Create a new PollMeeting")
    @Test
    public void shouldCreatePollMeeting() throws Exception {
        MinuteMeetingPU minute = new MinuteMeetingPU("001", "Description 01", "Resume 01");
        PollMeetingPU obj = new PollMeetingPU();

        obj.setMinuteMeeting(minute);
        obj.setId("000001");
        obj.getBeginAt();

        ZonedDateTime endAt = obj.getBeginAt().plusMinutes(5L);
        obj.adjustDuration(5L);

        minute.setPoll(obj);

        when(service.add(any(PollMeetingPU.class))).thenReturn(obj);

        PollMeetingPU saved = service.add(obj);

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getMinuteMeeting(), minute);
        Assertions.assertEquals(saved, minute.getPoll());
        Assertions.assertEquals(saved.getBeginAt(), obj.getBeginAt());
        Assertions.assertEquals(saved.getEndAt(), endAt);
        Assertions.assertEquals(saved.getEndAt(), obj.getEndAt());

    }

}
