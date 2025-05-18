package brainacad.repositories;

import brainacad.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    boolean existsByEmail(String email);
}
