package brainacad.controllers;

import brainacad.entities.Customer;
import brainacad.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController
{

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers/list";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/add";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable Long id, Model model)
    {
        model.addAttribute("customer", customerService.getById(id));
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer)
    {
        customer.setId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customers";
    }



    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }


    @GetMapping("/delete/confirm/{id}")
    public String confirmDeleteCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customers/delete";
    }

}
