package br.com.oneguy.votacao.test.services;

import br.com.oneguy.votacao.domain.persistence.AssociatePU;
import br.com.oneguy.votacao.services.IAssociateService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociateServiceTest {

    @Mock
    private IAssociateService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Insert a new Associate")
    @Test
    public void shouldInsertAssociate() throws Exception {
        AssociatePU obj = new AssociatePU("123", "12345678901");

        when(service.add(any(AssociatePU.class))).thenReturn(obj);

        AssociatePU saved = service.add(obj);

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getIdentification(), obj.getIdentification());
    }

    @DisplayName("Update a Associate")
    @Test
    public void shouldUpdateAssociate() throws Exception {
        AssociatePU obj = new AssociatePU("123", "12345678901");
        AssociatePU saved = new AssociatePU("123", "109876543221");

        when(service.update(any(AssociatePU.class))).thenReturn(saved);

        AssociatePU save = service.update(obj);


        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getIdentification(), save.getIdentification());
        Assertions.assertNotEquals(saved.getIdentification(), obj.getIdentification());
    }

    @DisplayName("Delete a Associate")
    @Test
    public void shouldDeleteAssociate() throws Exception {
        AssociatePU obj = new AssociatePU("123", "12345678901");

        when(service.remove(anyString())).thenReturn(true);

        Assertions.assertTrue(service.remove(obj.getId()));
    }
}
