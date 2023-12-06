package me.project.auth.formLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import me.project.entitiy.MyUser;
import me.project.repository.MyUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static me.project.enums.JwtExpire.ACCESS_TOKEN;

@AllArgsConstructor
public class FormLoginHelper implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final MyUserRepository myUserRepository;
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            String key = "someStringHashToHaveReallyGoodSecurityOverHereSoNoOneWithAmateurSkillsWouldn'tHackThis";
            String username = authentication.getName();
            MyUser user = myUserRepository.findUserByUsername(username);

            String accessToken = Jwts.builder()
                    .setSubject(authentication.getName())
                    .claim("authorities", authentication.getAuthorities())
                    .claim("userId", user.getMyUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN.getAmount()))
                    .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .compact();

            response.addHeader("Authorization", "Bearer " + accessToken);
            response.getWriter().write(
                    "{ " +
                    "\"userId\": \"" +
                    user.getMyUserId() +
                    "\", " +
                    "\"role\": \"" +
                    user.getAppUserRole() +
                    "\", " +
                    "\"username\": \"" +
                    user.getUsername() +
                    "\"" +
                    "} "
            );
        } catch (Exception e) {
            if (e.equals(new IOException(e.getMessage()))) {
                throw new IOException(e.getMessage());
            }
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Calendar.getInstance().getTime());
        data.put("exception", exception.getMessage());

        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}
