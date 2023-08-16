package edu.neu.csye7374.services;


import edu.neu.csye7374.config.JwtService;
import edu.neu.csye7374.dto.AuthenticationDto;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.User;
import edu.neu.csye7374.repository.UserRepository;
import edu.neu.csye7374.template.AuthenticationTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationTemplateFactory authenticationTemplateFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<User> createUser(AuthenticationDto authenticationDto) {
        User user = new User();
        user.setUsername(authenticationDto.getUsername());
        user.setPassword(passwordEncoder.encode(authenticationDto.getPassword()));
        user.setEmail(authenticationDto.getEmail());
        user.setFingerPrint(authenticationDto.getFingerPrint());
        user.setRole(authenticationDto.getRole());
        user.setPhone(authenticationDto.getPhone());
        User temp = userRepository.findByUsername(authenticationDto.getUsername());
        if (temp != null) {
            return new ResponseEntity<>("User already exists", temp, ReturnType.FAILURE);
        }
        return userRepository.saveAndCatch(user);
    }


    public ResponseEntity<AuthenticationDto> authenticate(AuthenticationDto authenticationDto) {
        if(authenticationDto.getUsername() == null && authenticationDto.getEmail() == null){
            return new ResponseEntity<>("Username and Email cannot be null", null, ReturnType.FAILURE);
        }
        User user = userRepository.findByUsernameOrEmail(authenticationDto.getUsername(), authenticationDto.getEmail());
        if (user == null) {
            return new ResponseEntity<>("User does not exist", null, ReturnType.FAILURE);
        }
        boolean result = authenticationTemplateFactory
                        .createAuthenticationTemplate(authenticationDto.getUserInput())
                        .authenticateAndAuthorize(authenticationDto);
        if (result) {
            authenticationDto.setToken(jwtService.generateToken(user));
            authenticationDto.setPassword(null);
            return new ResponseEntity<>("User authenticated", authenticationDto , ReturnType.SUCCESS);
        }

        return new ResponseEntity<>("User not authenticated", null, ReturnType.FAILURE);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
