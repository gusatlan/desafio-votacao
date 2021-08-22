package br.com.oneguy.votacao.test.domain.persistence;

import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinuteMeetingTest {

    /**
     * Test if invalid
     */
    @Test
    public void shouldBeInvalid() {
        MinuteMeetingPU obj = new MinuteMeetingPU();

        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String resume = "Resume of minute      ";
        String description = "Description of minute     ";
        MinuteMeetingPU obj = new MinuteMeetingPU(description, resume);

        Assertions.assertTrue(obj.isValid());
        Assertions.assertEquals(description.trim(), obj.getDescription());
        Assertions.assertEquals(resume.trim(), obj.getResume());

        Assertions.assertNotEquals(description, obj.getDescription());
        Assertions.assertNotEquals(resume, obj.getResume());
    }

    /**
     * Test create poll
     */
    @Test
    public void shouldBeCreatePoll() {
        MinuteMeetingPU obj = new MinuteMeetingPU("Description of minute     ", "Resume of minute      ");

        Assertions.assertTrue(obj.isValid());

        obj.createPoll(null);

        Assertions.assertNotNull(obj.getPoll());
        Assertions.assertNotNull(obj.getPoll().getEndAt());

    }


}
