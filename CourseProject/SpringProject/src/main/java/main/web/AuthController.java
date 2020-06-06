package main.web;

import main.entity.User;
import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bt/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRep;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/singin")
    public ResponseEntity singIn(@RequestBody AuthRequest request){
        try{
            String name = request.getUserName();
            User user = userRep.findUsersByUserName(name)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new UsernameNotFoundException("Invalid password");
            }

            String token = jwtTokenProvider.createToken(name, user.getRoles());
            List<String> role = user.getRoles();

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("role", role);
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
