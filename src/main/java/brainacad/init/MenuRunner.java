package brainacad.init;

import brainacad.entities.Customer;
import brainacad.entities.Event;
import brainacad.entities.Ticket;
import brainacad.services.CustomerService;
import brainacad.services.EventService;
import brainacad.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class MenuRunner implements CommandLineRunner
{

    private final EventService eventService;
    private final CustomerService customerService;
    private final TicketService ticketService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args)
    {
        System.out.println("\nWelcome to Ticket Console App!");

        while (true)
        {
            System.out.println("""
                    \nChoose an action:
                    1 - View upcoming events
                    2 - Register new customer
                    3 - Find available tickets by event ID
                    4 - Buy a ticket
                    0 - Exit
                    """);

            System.out.print("Your choice: ");
            String input = scanner.nextLine();

            switch (input)
            {
                case "1" -> showUpcomingEvents();
                case "2" -> registerCustomer();
                case "3" -> findFreeTickets();
                case "4" -> buyTicket();
                case "0" ->
                {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showUpcomingEvents()
    {
        List<Event> events = eventService.getUpcomingEvents();
        if (events.isEmpty())
        {
            System.out.println("No upcoming events.");
        }
        else
        {
            events.forEach(e ->
                    System.out.println("ID: " + e.getId() + " | " + e.getName() + " | Date: " + e.getEventDate())
            );
        }
    }

    private void registerCustomer()
    {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        try
        {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer = customerService.createCustomer(customer);
            System.out.println("Registered with ID: " + customer.getId());
        }
        catch (Exception e)
        {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    private void findFreeTickets()
    {
        System.out.print("Enter event ID: ");
        Long eventId = Long.parseLong(scanner.nextLine());

        List<Ticket> tickets = ticketService.findFreeTicketsByEventId(eventId);
        if (tickets.isEmpty())
        {
            System.out.println("No free tickets for this event.");
        }
        else
        {
            tickets.forEach(t ->
                    System.out.println("Ticket ID: " + t.getId() + " | Seat: " + t.getNumber() + " | Price: $" + t.getCost())
            );
        }
    }

    private void buyTicket()
    {
        System.out.print("Enter customer ID: ");
        Long customerId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter ticket ID: ");
        Long ticketId = Long.parseLong(scanner.nextLine());

        try
        {
            Customer customer = customerService.getById(customerId);
            Ticket ticket = ticketService.assignTicketToCustomer(ticketId, customer);
            System.out.println("Ticket " + ticket.getNumber() + " successfully bought by " + customer.getName());
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
