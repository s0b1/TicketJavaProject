package brainacad.repositories;

import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long>
{
    List<Ticket> findByEventIdAndStatus(Long eventId, TicketStatus status);
}