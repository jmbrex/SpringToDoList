/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AulaRocketseat.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.AulaRocketseat.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FiltesTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
            var servletPath = request.getServletPath();
        
        if (servletPath.startsWith("/task/")) {
                //Pergar autenticação
            var authorization = request.getHeader("Authorization");
            //Decodificando user
            var authEncoded= authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            //Transformando de byte para string
            String authString = new String(authDecode);
            // separando user e password
            String [] credential = authString.split(":");
            String username = credential[0];
            String password = credential[1];
            System.out.println(username+"\n"+password);
        
            //validação user
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401,"Usuario sem autorização");
            }else{
                //validação password
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401,"Usuario sem autorização");
                }
            
            }
            
        }else{
            filterChain.doFilter(request, response);
        }
        
    }

    
}
