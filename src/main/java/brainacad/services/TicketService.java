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
    private final CustomerService customerService;

    public List<Ticket> getAllAvailableTickets()
    {
        return ticketRepository.findByStatus(TicketStatus.FREE);
    }

    public Ticket getById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }

    public List<Ticket> findFreeTicketsByEventId(Long eventId) {
        return ticketRepository.findByEventIdAndStatus(eventId, TicketStatus.FREE);
    }

    public Ticket assignTicketToCustomer(Long ticketId, Customer customer) {
        Ticket ticket = getById(ticketId);

        if (ticket.getStatus() != TicketStatus.FREE) {
            throw new IllegalStateException("Ticket is already sold");
        }

        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.SOLD);
        return ticketRepository.save(ticket);
    }

    public void purchaseTicket(Long ticketId, Long customerId)
    {
        Ticket ticket = getById(ticketId);
        Customer customer = customerService.getById(customerId);
        ticket.setCustomer(customer);
        ticket.setStatus(TicketStatus.SOLD);
        ticketRepository.save(ticket);
    }
}
