package brainacad.services;

import brainacad.dto.EventCreationDTO;
import brainacad.dto.PlaceDTO;
import brainacad.dto.TicketPackDTO;
import brainacad.entities.Event;
import brainacad.entities.Place;
import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import brainacad.repositories.EventRepository;
import brainacad.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest
{

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEvent_createsEventWithTickets()
    {

        EventCreationDTO dto = new EventCreationDTO();
        dto.setName("Test Event");
        dto.setEventDate(LocalDate.now().plusDays(5));
        dto.setTicketPacks(List.of(new TicketPackDTO(100.0, 2)));
        dto.setPlace(new PlaceDTO("Stadium", "Main St"));

        Place place = new Place();
        when(placeService.findOrCreatePlace(any())).thenReturn(place);

        Event savedEvent = new Event();
        savedEvent.setId(1L);
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event result = eventService.createEvent(dto);

        assertNotNull(result);
        verify(eventRepository).save(any(Event.class));
        verify(placeService).findOrCreatePlace(any());
        verifyNoInteractions(ticketRepository);
    }
}
