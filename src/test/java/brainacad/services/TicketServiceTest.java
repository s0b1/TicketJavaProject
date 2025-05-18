package brainacad.services;

import brainacad.entities.Customer;
import brainacad.entities.Event;
import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import brainacad.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest
{

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buyTicket_updatesTicketStatusAndCustomer()
    {

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatus(TicketStatus.FREE);
        ticket.setCustomer(null);

        Customer customer = new Customer();
        customer.setId(100L);
        customer.setName("Alice");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket updatedTicket = ticketService.assignTicketToCustomer(1L, customer);

        assertNotNull(updatedTicket);
        assertEquals(TicketStatus.SOLD, updatedTicket.getStatus());
        assertEquals(customer, updatedTicket.getCustomer());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void buyTicket_ticketNotFound_throwsException()
    {

        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ticketService.assignTicketToCustomer(999L, new Customer()));
    }
}
