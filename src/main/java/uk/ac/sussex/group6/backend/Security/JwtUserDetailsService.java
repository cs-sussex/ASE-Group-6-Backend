package uk.ac.sussex.group6.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new BadRequestException("User not found"));
        return UserDetailsImpl.build(user);
    }
}
