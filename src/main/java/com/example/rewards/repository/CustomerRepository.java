package com.example.rewards.repository;

import com.example.rewards.model.Customer;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class CustomerRepository {
    private final Map<Long, Customer> customers = new HashMap<Long, Customer>();

    public CustomerRepository() {
        customers.put(1L, new Customer(1L, "Alice Johnson", "alice@example.com"));
        customers.put(2L, new Customer(2L, "Bob Smith", "bob@example.com"));
        customers.put(3L, new Customer(3L, "Carol Davis", "carol@example.com"));
    }
    public Optional<Customer> findById(Long id) { return Optional.ofNullable(customers.get(id)); }
}
