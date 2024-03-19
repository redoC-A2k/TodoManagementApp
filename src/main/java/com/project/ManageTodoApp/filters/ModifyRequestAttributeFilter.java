package com.project.ManageTodoApp.filters;

import java.io.IOException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ModifyRequestAttributeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();
            // print all attributes of authentication
            request.setAttribute("userId", (Long) authentication.getTokenAttributes().get("uId"));
        } catch (Exception e) {
            System.out.println("Error in ModifyRequestAttributeFilter: " + e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

}
