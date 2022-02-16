package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static Map<String, Customer> customerMap = new HashMap<>();

    public static void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customerMap.put(customer.getEmail(), customer);
    }

    public static Customer getCustomer(String customerEmail) {
        return customerMap.get(customerEmail);
    }

    public static Collection<Customer> getAllCustomers() {
        return customerMap.values();
    }
}

