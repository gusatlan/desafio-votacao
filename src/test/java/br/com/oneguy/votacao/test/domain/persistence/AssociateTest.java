package br.com.oneguy.votacao.test.domain.persistence;

import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssociateTest {

    /**
     * Test if valid
     */
    @Test
    public void shouldBeValid() {
        String cpf = "123.456.789-01";
        String cpfDigits = "12345678901";
        AssociatePU obj = new AssociatePU();

        obj.setIdentification(cpf);
        Assertions.assertTrue(obj.isValid());
        Assertions.assertEquals(cpfDigits, obj.getIdentification());
    }

    /**
     * Test if invalid
     */
    @Test
    public void shouldBeInvalid() {
        AssociatePU obj = new AssociatePU();

        obj.setIdentification("  ");
        Assertions.assertFalse(obj.isValid());

        obj.setIdentification("");
        Assertions.assertFalse(obj.isValid());

        obj.setIdentification(null);
        Assertions.assertFalse(obj.isValid());
    }

    /**
     * Test if both AssociatePU are same (identification)
     */
    @Test
    public void shouldBeSame() {
        AssociatePU obj1 = new AssociatePU("12345678901");
        AssociatePU obj2 = new AssociatePU("12345678901");

        Assertions.assertTrue(obj1.same(obj2));
        Assertions.assertNotEquals(obj1, obj2);
    }
}
