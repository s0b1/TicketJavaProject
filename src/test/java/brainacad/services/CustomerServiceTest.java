package brainacad.services;

import brainacad.entities.Customer;
import brainacad.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest
{

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceTest()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_savesAndReturnsCustomer()
    {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");

        when(customerRepository.save(any())).thenReturn(customer);

        Customer saved = customerService.createCustomer(customer);

        assertNotNull(saved);
        assertEquals("test@example.com", saved.getEmail());
        verify(customerRepository).save(customer);
    }
}
