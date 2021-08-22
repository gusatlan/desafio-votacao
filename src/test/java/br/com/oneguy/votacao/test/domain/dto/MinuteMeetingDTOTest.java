package br.com.oneguy.votacao.test.domain.dto;

import br.com.oneguy.votacao.domain.dto.v1.MinuteMeetingDTO;
import br.com.oneguy.votacao.domain.persistence.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinuteMeetingDTOTest {

    /**
     * Test if not valid
     */
    @Test
    public void shouldMinuteMeetingNotValid() {
        String id = "1";
        String description = "  Description Minute 01    ";
        String resume = "  Resume of Minute 01    ";
        Long countTotal = 30L;
        Long countAgree = 10L;
        Long countDisagree = 20L;

        MinuteMeetingDTO obj = new MinuteMeetingDTO();

        obj.setId(id);
        obj.setDescription(description);
        obj.setResume(resume);

        obj.setCountTotal(null);
        obj.setCountAgree(countAgree + 3);
        obj.setCountDisagree(countDisagree + 4);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertNotEquals(description, obj.getDescription());
        Assertions.assertNotEquals(resume, obj.getResume());

        Assertions.assertNotEquals(countTotal, obj.getCountTotal());
        Assertions.assertNotEquals(countAgree, obj.getCountAgree());
        Assertions.assertNotEquals(countDisagree, obj.getCountDisagree());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldMinuteMeetingValid() {
        String id = "1";
        String description = "  Description Minute 01    ".trim();
        String resume = "  Resume of Minute 01    ".trim();
        Long countTotal = 30L;
        Long countAgree = 10L;
        Long countDisagree = 20L;

        MinuteMeetingDTO obj = new MinuteMeetingDTO();

        obj.setId(id);
        obj.setDescription(description);
        obj.setResume(resume);

        obj.setCountTotal(-10L);
        obj.setCountAgree(countAgree);
        obj.setCountDisagree(countDisagree);

        Assertions.assertEquals(id, obj.getId());
        Assertions.assertEquals(description, obj.getDescription());
        Assertions.assertEquals(resume, obj.getResume());

        Assertions.assertEquals(countTotal, obj.getCountTotal());
        Assertions.assertEquals(countAgree, obj.getCountAgree());
        Assertions.assertEquals(countDisagree, obj.getCountDisagree());

    }

}
