package brainacad.controllers;

import brainacad.entities.Customer;
import brainacad.entities.Ticket;
import brainacad.services.CustomerService;
import brainacad.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController
{

    private final TicketService ticketService;
    private final CustomerService customerService;

    @GetMapping
    public String listTickets(Model model)
    {
        List<Ticket> tickets = ticketService.getAllAvailableTickets();
        model.addAttribute("tickets", tickets);
        return "tickets/list";
    }

    @GetMapping("/confirm/{ticketId}")
    public String confirmPurchase(@PathVariable Long ticketId, Model model)
    {
        model.addAttribute("ticket", ticketService.getById(ticketId));
        model.addAttribute("customers", customerService.getAllCustomers());
        return "tickets/purchase";
    }


    @PostMapping("/purchase")
    public String purchaseTicket(@RequestParam Long ticketId, @RequestParam Long customerId)
    {
        ticketService.purchaseTicket(ticketId, customerId);
        return "redirect:/tickets";
    }
}
