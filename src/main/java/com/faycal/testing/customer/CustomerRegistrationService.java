package com.faycal.testing.customer;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request) {
//        1. PhoneNumber is taken
//        2. if taken lets check if belongs to same customer
//        - 2.1 if yes return
//        - 2.2 throw an exception
//        3. Save customer
        String phoneNumber = request.getCustomer().getPhoneNumber();
        Optional<Customer> customerOptional = customerRepository.selectCustomerByPhoneNumber(phoneNumber);
        if (customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            if(customer.getName().equals(request.getCustomer().getName())) {
                return;
            }
            throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
        }
        customerRepository.save(request.getCustomer());

    }
}
