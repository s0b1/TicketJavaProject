package brainacad.init;

import brainacad.dto.EventCreationDTO;
import brainacad.dto.PlaceDTO;
import brainacad.dto.TicketPackDTO;
import brainacad.entities.Customer;
import brainacad.entities.Event;
import brainacad.entities.Ticket;
import brainacad.services.CustomerService;
import brainacad.services.EventService;
import brainacad.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner
{

    private final EventService eventService;
    private final CustomerService customerService;
    private final TicketService ticketService;

    @Override
    public void run(String... args)
    {
        System.out.println("STARTING DATA INITIALIZATION");

        EventCreationDTO eventDto = new EventCreationDTO();
        eventDto.setName("Spring Rock Concert");
        eventDto.setEventDate(LocalDate.now().plusDays(5));

        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setName("Arena Central");
        placeDTO.setAddress("Saxon Avenue");
        eventDto.setPlace(placeDTO);

        eventDto.setTicketPacks(List.of(
                createPack(50.0, 3),
                createPack(80.0, 2)
        ));

        Event event = eventService.createEvent(eventDto);
        System.out.println("Event Created: " + event.getName() + " (tickets: " + event.getTickets().size() + ")");

        Customer customer = new Customer();
        customer.setName("Alice Springs");
        customer.setEmail("alice@example.com");
        customer.setPhone("+123456789");

        customer = customerService.createCustomer(customer);
        System.out.println("Customer Created: " + customer.getName());

        List<Ticket> freeTickets = ticketService.findFreeTicketsByEventId(event.getId());
        System.out.println("Free Tickets: " + freeTickets.size());

        if (!freeTickets.isEmpty())
        {
            Ticket chosen = freeTickets.get(0);
            Ticket sold = ticketService.assignTicketToCustomer(chosen.getId(), customer);
            System.out.println("Ticket #" + sold.getNumber() + " Sold to " + customer.getName());
        }

        List<Ticket> afterSale = ticketService.findFreeTicketsByEventId(event.getId());
        System.out.println("Free Tickets left: " + afterSale.size());

        System.out.println("DATA INITIALIZATION FINISHED");
    }

    private TicketPackDTO createPack(double cost, int count)
    {
        TicketPackDTO pack = new TicketPackDTO();
        pack.setCost(cost);
        pack.setCount(count);
        return pack;
    }
}
