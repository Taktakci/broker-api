package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.repository.CustomerRepository;
import com.taktakci.brokerapi.repository.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {

    private CustomerRepository repository;

    @Autowired
    public CustomerDetailService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> userOptional = repository.findByUsername(username);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(getRole(user))
                    .build();

        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRole(Customer user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
