package me.project.auth;

import lombok.AllArgsConstructor;
import me.project.entitiy.MyUser;
import me.project.repository.MyUserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Klasa CustomAuthenticationProvider implementuje interfejs AuthenticationProvider
 * i dostarcza niestandardową logikę uwierzytelniania użytkownika.
 */
@AllArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final MyUserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MyUser user = userRepository.findUserByUsername(authentication.getName().trim());
        String password = authentication.getCredentials().toString();

        if (user == null || !password.equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
