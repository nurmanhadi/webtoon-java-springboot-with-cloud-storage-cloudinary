package com.nurman.webtoon.helper;

import java.io.IOException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        var request = (HttpServletRequest) arg0;
        var respose = (HttpServletResponse) arg1;
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            respose.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            respose.getWriter().write("missing or invalid Authorization header");
            return;
        }
        String token = authHeader.substring(7);
        try {
            var verifier = JWT.require(Algorithm.HMAC256("nurman")).withIssuer("auth0").build();
            DecodedJWT decode = verifier.verify(token);
            request.setAttribute("role", decode.getClaim("role"));
        } catch (Exception e) {
            respose.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            respose.getWriter().write("invalid token: " + e.getMessage());
            return;
        }
        arg2.doFilter(arg0, arg1);
    }

}
