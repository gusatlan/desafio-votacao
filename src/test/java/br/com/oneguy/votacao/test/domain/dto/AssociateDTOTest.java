package br.com.oneguy.votacao.test.domain.dto;


import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssociateDTOTest {
    /**
     * Test if valid
     */
    @Test
    public void shouldBeIdentificationEquals() {
        String cpf = "123.456.789-01      ";
        String cpfDigits = "12345678901";
        AssociateDTO obj = new AssociateDTO();

        obj.setIdentification(cpf);
        Assertions.assertEquals(cpfDigits, obj.getIdentification());
    }

    /**
     * Test if valid
     */
    @Test
    public void shouldBeIdentificationNotEquals() {
        String cpf = "123.456.789-01A";
        String cpfDigits = "12345678901";
        AssociateDTO obj = new AssociateDTO();

        obj.setIdentification(cpf);
        Assertions.assertEquals(cpfDigits, obj.getIdentification());
    }

}
