package br.com.oneguy.votacao.test.services;

import br.com.oneguy.votacao.domain.persistence.MinuteMeetingPU;
import br.com.oneguy.votacao.services.IMinuteMeetingService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MinuteMeetingServiceTest {

    @Mock
    private IMinuteMeetingService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Insert a new MinuteMeeting")
    @Test
    public void shouldInsertMinuteMeeting() throws Exception {
        MinuteMeetingPU obj = new MinuteMeetingPU("1", "Description 01", "Resume 01");

        when(service.add(any(MinuteMeetingPU.class))).thenReturn(obj);

        MinuteMeetingPU saved = service.add(new MinuteMeetingPU("Description 01", "Resume 01"));

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertEquals(saved.getDescription(), obj.getDescription());
        Assertions.assertEquals(saved.getResume(), obj.getResume());
    }

    @DisplayName("Update a MinuteMeeting")
    @Test
    public void shouldUpdateMinuteMeeting() throws Exception {
        MinuteMeetingPU original = new MinuteMeetingPU("1", "Description 01", "Resume 01");
        MinuteMeetingPU obj = new MinuteMeetingPU("1", "Description 02", "Resume 02");

        when(service.update(any(MinuteMeetingPU.class))).thenReturn(obj);

        MinuteMeetingPU saved = service.update(new MinuteMeetingPU("1", "Description 02", "Resume 02"));

        Assertions.assertEquals(saved.getId(), obj.getId());
        Assertions.assertNotEquals(saved.getDescription(), original.getDescription());
        Assertions.assertNotEquals(saved.getResume(), original.getResume());
        Assertions.assertEquals(saved.getDescription(), obj.getDescription());
        Assertions.assertEquals(saved.getResume(), obj.getResume());
    }

    @DisplayName("Delete a MinuteMeeting")
    @Test
    public void shouldDeleteMinuteMeeting() throws Exception {
        MinuteMeetingPU obj = new MinuteMeetingPU("1", "", "");

        when(service.remove(anyString())).thenReturn(true);
        when(service.remove("2")).thenReturn(false);

        Assertions.assertTrue(service.remove(obj.getId()));
        Assertions.assertFalse(service.remove("2"));
    }
}
