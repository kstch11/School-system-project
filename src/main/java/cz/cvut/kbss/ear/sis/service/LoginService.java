package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.exception.AlreadyLoggedInUserException;
import cz.cvut.kbss.ear.sis.security.DefaultAuthenticationProvider;
import cz.cvut.kbss.ear.sis.security.SecurityUtils;
import cz.cvut.kbss.ear.sis.security.model.UserDetails;
import cz.cvut.kbss.ear.sis.security.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    private DefaultAuthenticationProvider provider;
    private final UserDetailsService userDetailsService;

    @Autowired
    public LoginService(DefaultAuthenticationProvider provider, UserDetailsService userDetailsService) {
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }

    @Transactional
    public void loginUser (String username, String password) throws AlreadyLoggedInUserException {
        if (SecurityUtils.getCurrentUserDetails() != null) {
            throw new AlreadyLoggedInUserException("You are already login.");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        provider.authenticate(authentication);

        final UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);
        SecurityUtils.setCurrentUser(userDetails);
        System.out.println(SecurityUtils.getCurrentUser().getRole());
    }
}
