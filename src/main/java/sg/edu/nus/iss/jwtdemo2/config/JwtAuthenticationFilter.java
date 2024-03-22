package sg.edu.nus.iss.jwtdemo2.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sg.edu.nus.iss.jwtdemo2.service.JwtService;
import sg.edu.nus.iss.jwtdemo2.service.UserService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // userEmail should be userName
    // if it works, rename to userName
    // unless the usernname is userEmail (previously)
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt = parseJwt(request);
        final String userEmail;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader,
                "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if (StringUtils.isNotEmpty(userEmail)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);


        // try {
        //     logger.info("Request: " + request.toString());
        //     String jwt = parseJwt(request);
        //     logger.info("JWT:" + jwt);
        //     if (jwt != null && jwtService.validateJwtToken(jwt)) {
        //         String username = jwtService.getUserNameFromJwtToken(jwt);

        //         logger.info("Username: " + username);

        //         UserDetails userDetails = userService.loadUserByUsername(username);
        //         logger.info("UserDetails: " + userDetails.toString());

        //         UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        //                 userDetails,
        //                 null,
        //                 userDetails.getAuthorities());

        //         authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //         SecurityContextHolder.getContext().setAuthentication(authentication);
        //     }
        // } catch (Exception e) {
        //     logger.error("Cannot set user authentication: {}", e);
        // }

        // filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtService.getJwtFromCookies(request);
        return jwt;
    }
}
