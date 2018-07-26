package com.auth0.samples.authapi.user;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    public UserDetailServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

}
