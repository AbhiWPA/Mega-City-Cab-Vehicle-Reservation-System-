package lk.mcc.megacitycab.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.mcc.megacitycab.audit.HeaderHolder;
import lk.mcc.megacitycab.persistence.repo.UserRepo;
import lk.mcc.megacitycab.service.JwtService;
import lk.mcc.megacitycab.util.num.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    private final HeaderHolder headerHolder;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        final String userId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        jwt = authHeader.substring(7);
        userName = jwtService.extractUsername(jwt);
        userId = jwtService.extractUserId(jwt);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            // check token exists in database
//            var token = userRepo.findByToken(jwt).orElse(null);

            if (jwtService.isTokenValid(jwt, userDetails, userId)) {
                Role userRole = jwtService.extractRole(jwt);
                if (userRole == null) {

                    userRole = userDetails.getAuthorities().stream()
                            .map(authority -> Role.valueOf(authority.getAuthority()))
                            .findFirst()
                            .orElse(null);
                }
                headerHolder.setRole(userRole);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }

        filterChain.doFilter(request, response);
    }
}
