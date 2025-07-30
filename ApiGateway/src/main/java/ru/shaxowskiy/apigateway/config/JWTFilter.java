package ru.shaxowskiy.apigateway.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import ru.shaxowskiy.apigateway.exception.MissingOrInvalidAuthorizationHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ru.shaxowskiy.apigateway.service.JWTService;

import java.util.List;

@Component
public class JWTFilter implements WebFilter {
    private JWTService jwtService;

    private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwt = extractJwtToken(exchange);
        try {
            //TODO установить заголовок X-User-Name для микросервисов
            DecodedJWT decodedJWT = jwtService.validateToken(jwt);
            log.info("Request from user with username {}", getUsernameFromJwt(decodedJWT));
            exchange.getRequest().mutate().header("X-User-Name", getUsernameFromJwt(decodedJWT));
            //UserDetails userDetails = userService.loadUserByUsername(decodedJWT.getClaim("username").asString());

            String role = decodedJWT.getClaim("role").asString();
            //List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + jwtService.extractRole(jwt)));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    getUsernameFromJwt(decodedJWT),
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(usernamePasswordAuthenticationToken));
        } catch (JWTVerificationException e){

            log.error("JWT verification failed: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return null;
    }

    private String getUsernameFromJwt(DecodedJWT decodedJWT) {
        return jwtService.extractUsername(decodedJWT);
    }

    private String extractJwtToken(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Authorization header {}", authorizationHeader);
        if(!authorizationHeader.isEmpty() && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.substring(7);
        }
        throw new MissingOrInvalidAuthorizationHeaderException("Token is null or invalid");
    }
}
