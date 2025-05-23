package brainacad.services;


import brainacad.entities.Customer;
import brainacad.entities.Ticket;
import brainacad.entities.TicketStatus;
import brainacad.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import brainacad.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService
{

    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public Customer createCustomer(Customer customer)
    {
        Optional<Customer> existing = customerRepository.findByEmail(customer.getEmail());
        if (existing.isPresent())
        {
            return existing.get();
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Customer getById(Long id)
    {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }

    public Customer updateCustomer(Customer updated)
    {
        Customer existing = customerRepository.findById(updated.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());

        return customerRepository.save(existing);
    }

    @Transactional
    public void deleteCustomer(Long id)
    {
        Customer customer = getById(id);
        List<Ticket> tickets = ticketRepository.findByCustomer(customer);
        for (Ticket ticket : tickets) {
            ticket.setCustomer(null);
            ticket.setStatus(TicketStatus.FREE);
            ticketRepository.save(ticket);
        }
        customerRepository.deleteById(id);
    }


}
