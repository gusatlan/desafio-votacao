package br.com.oneguy.votacao.utils;

import br.com.oneguy.votacao.domain.dto.v1.AssociateDTO;
import br.com.oneguy.votacao.domain.persistence.AssociatePU;

public class ConverterUtil {

    /**
     * Convert AssociatePU to AssociateDTO
     * @param value
     * @return associate
     * @throws NullPointerException
     */
    public static final AssociateDTO convert(final AssociatePU value) throws NullPointerException {
        AssociateDTO obj = new AssociateDTO();

        obj.setId(value.getId());
        obj.setIdentification(value.getIdentification());

        return obj;
    }


}
