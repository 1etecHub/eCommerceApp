package com.eCommerceApp.eCommerce.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.eCommerceApp.eCommerce.dao.AppUserDAO;
import com.eCommerceApp.eCommerce.entities.AppUser;
import com.eCommerceApp.eCommerce.service.serviceImpl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
/*import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;*/
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Filter for decoding a JWT in the Authorization header and loading the user
 * object into the authentication context.
 */
@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

  /** The JWT Service. */
  private JwtService jwtService;
  /** The Local User DAO. */
  private AppUserDAO localUserDAO;


  /**
   * {@inheritDoc}
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String tokenHeader = request.getHeader("Authorization");
    UsernamePasswordAuthenticationToken token = checkToken(tokenHeader);
    if (token != null) {
      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Method to authenticate a token and return the Authentication object
   * written to the spring security context.
   * @param token The token to test.
   * @return The Authentication object if set.
   */
  private UsernamePasswordAuthenticationToken checkToken(String token) {
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
      try {
        String username = jwtService.getUsername(token);
        Optional<AppUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);
        if (opUser.isPresent()) {
          AppUser user = opUser.get();
          if (user.isEmailVerified()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
          }
        }
      } catch (JWTDecodeException ex) {
      }
    }
    SecurityContextHolder.getContext().setAuthentication(null);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  /*@Override
  public Message<?> preSend(Messag<?> message, MessageChannel channel) {
    SimpMessageType messageType =
        (SimpMessageType) message.getHeaders().get("simpMessageType");
    if (messageType.equals(SimpMessageType.SUBSCRIBE)
        || messageType.equals(SimpMessageType.MESSAGE)) {
      Map nativeHeaders = (Map) message.getHeaders().get("nativeHeaders");
      if (nativeHeaders != null) {
        List authTokenList = (List) nativeHeaders.get("Authorization");
        if (authTokenList != null) {
          String tokenHeader = (String) authTokenList.get(0);
          checkToken(tokenHeader);
        }
      }
    }
    return message;
  }*/
}
