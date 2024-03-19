// package com.project.ManageTodoApp.filters;

// import java.io.IOException;
// import java.nio.charset.StandardCharsets;
// import java.util.Date;

// import javax.crypto.SecretKey;

// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.project.ManageTodoApp.entity.User;
// import com.project.ManageTodoApp.security.SecurityConstants;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class JWTTokenGeneratorFilter extends OncePerRequestFilter{
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if(authentication != null){
//             User user = (User) authentication.getPrincipal();
//             SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//             String jwt = Jwts.builder()
//                     .setIssuer("ManageTodoApp")
//                     .setSubject("JWT Token")
//                     .claim("username",authentication.getName())
//                     .claim("userId", user.getId())
//                     .setIssuedAt(new Date())
//                     .setExpiration(new Date((new Date()).getTime()+30000000))
//                     .signWith(key).compact();
//             response.setHeader(SecurityConstants.JWT_HEADER,jwt);
//         }
//         filterChain.doFilter(request,response);
//     }
    
//     @Override
//     protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//         return !request.getServletPath().equals("/api/users/signin");
//     }
// }
