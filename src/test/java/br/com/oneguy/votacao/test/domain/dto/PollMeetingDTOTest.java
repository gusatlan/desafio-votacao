package br.com.oneguy.votacao.test.domain.dto;


import br.com.oneguy.votacao.domain.dto.v1.PollMeetingDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PollMeetingDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldBeNotValid() {
        String id = " PM0001 ";
        String minuteMeetingId = "     MM000001   ";
        Long duration = 15L;
        PollMeetingDTO obj = new PollMeetingDTO();

        obj.setId(id);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setDuration(duration + 5);

        Assertions.assertNotEquals(id, obj.getId());
        Assertions.assertNotEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertNotEquals(duration, obj.getDuration());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String id = " PM0001 ".trim().toLowerCase();
        String minuteMeetingId = "     MM000001   ".trim().toLowerCase();
        Long duration = 15L;
        PollMeetingDTO obj = new PollMeetingDTO();

        obj.setId(id);
        obj.setMinuteMeetingId(minuteMeetingId);
        obj.setDuration(duration);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(minuteMeetingId, obj.getMinuteMeetingId());
        Assertions.assertEquals(duration, obj.getDuration());
    }

}
