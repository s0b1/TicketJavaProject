package brainacad.services;

import brainacad.dto.EventCreationDTO;
import brainacad.dto.TicketPackDTO;
import brainacad.entities.Event;
import brainacad.entities.Place;
import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import brainacad.repositories.EventRepository;
import brainacad.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final PlaceService placeService;

    public Event createEvent(EventCreationDTO dto)
    {
        Place place = placeService.findOrCreatePlace(dto.getPlace());

        Event event = new Event();
        event.setName(dto.getName());
        event.setEventDate(dto.getEventDate());
        event.setPlace(place);

        List<Ticket> tickets = new ArrayList<>();
        int seatNumber = 1;
        for (TicketPackDTO pack : dto.getTicketPacks()) {
            for (int i = 0; i < pack.getCount(); i++) {
                Ticket ticket = new Ticket();
                ticket.setCost(pack.getCost());
                ticket.setNumber(seatNumber++);
                ticket.setStatus(TicketStatus.FREE);
                ticket.setEvent(event);
                tickets.add(ticket);
            }
        }

        event.setTickets(tickets);
        return eventRepository.save(event);
    }

    public List<Event> getUpcomingEvents()
    {
        return eventRepository.findByEventDateAfterOrderByEventDateAsc(LocalDate.now());
    }

    public Event getById(Long id)
    {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + id));
    }

    public void deleteById(Long id)
    {
        eventRepository.deleteById(id);
    }
}
