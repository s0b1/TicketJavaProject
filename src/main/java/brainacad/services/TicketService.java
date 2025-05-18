package brainacad.services;


import brainacad.entities.Customer;
import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import brainacad.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService
{

    private final TicketRepository ticketRepository;

    public List<Ticket> findFreeTicketsByEventId(Long eventId)
    {
        return ticketRepository.findByEventIdAndStatus(eventId, TicketStatus.FREE);
    }

    public Ticket assignTicketToCustomer(Long ticketId, Customer customer)
    {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.FREE)
        {
            throw new IllegalStateException("Ticket is already sold");
        }

        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.SOLD);

        return ticketRepository.save(ticket);
    }
}