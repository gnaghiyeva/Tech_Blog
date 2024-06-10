package org.example.techblog.config.auth;

import org.example.techblog.models.UserEntity;
import org.example.techblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user != null) {
            List<String> userRoles = user.getRoles().stream().map(x -> x.getName()).collect(Collectors.toList());
            Set<GrantedAuthority> roles = new HashSet<>();
            for(String role : userRoles) {
                roles.add(new SimpleGrantedAuthority(role));
            }
            User loginUser = new User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getEmailConfirmed(),
                    true,
                    true,
                    true,
                    user.getRoles().stream().map(m->new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList())
            );
            return loginUser;
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
