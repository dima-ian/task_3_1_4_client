package dmz.crud.service;

import dmz.crud.model.Role;
import dmz.crud.model.User;
import dmz.crud.rest.RestTemplateAuth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User userFromDB = (User) RestTemplateAuth.getUserByUsername(name);

        if (userFromDB == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        if (userFromDB.getStrRole().equalsIgnoreCase("ROLE_ADMIN")) {
            userFromDB.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            userFromDB.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }

        return userFromDB;
    }
}
