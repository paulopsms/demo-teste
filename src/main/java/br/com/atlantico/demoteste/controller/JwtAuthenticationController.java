package br.com.atlantico.demoteste.controller;

import br.com.atlantico.demoteste.configuration.security.jwt.JwtTokenUtil;
import br.com.atlantico.demoteste.configuration.security.jwt.JwtUserDetailsService;
import br.com.atlantico.demoteste.model.jwt.JwtRequest;
import br.com.atlantico.demoteste.model.jwt.JwtResponse;
import br.com.atlantico.demoteste.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(HttpSession session, HttpServletRequest request,
                                                       @RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        User user = (User) session.getAttribute("User:" + authenticationRequest.getUsername());

        if (user == null) {
            user = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            request.getSession().setAttribute("User:" + user.getLogin(), user);
        }

        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
