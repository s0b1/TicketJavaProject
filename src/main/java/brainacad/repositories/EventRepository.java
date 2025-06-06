package brainacad.repositories;

import brainacad.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>
{
    List<Event> findByEventDateAfterOrderByEventDateAsc(LocalDate date);


}