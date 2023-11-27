package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.model.UserAccountDetails;
import com.bajidan.supermarketms.model.User;
import com.bajidan.supermarketms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserServiceDetail implements UserDetailsService {

    private final UserRepository userRepository;

    private User user;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     user = userRepository.findAllByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

     return new UserAccountDetails(user);
    }

    public User getUser() {
        return user;
    }
}

