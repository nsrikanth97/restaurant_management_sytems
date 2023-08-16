package edu.neu.csye7374.services;


import edu.neu.csye7374.dto.AuthenticationDto;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.User;
import edu.neu.csye7374.repository.UserRepository;
import edu.neu.csye7374.template.AuthenticationTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationTemplateFactory authenticationTemplateFactory;

    public ResponseEntity<User> createUser(AuthenticationDto authenticationDto) {
        User user = new User();
        user.setUsername(authenticationDto.getUsername());
        user.setPassword(authenticationDto.getPassword());
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


    public ResponseEntity<Boolean> authenticate(AuthenticationDto authenticationDto) {
        if(authenticationDto.getUsername() == null && authenticationDto.getEmail() == null){
            return new ResponseEntity<>("Username and Email cannot be null", false, ReturnType.FAILURE);
        }
        User user = userRepository.findByUsernameOrEmail(authenticationDto.getUsername(), authenticationDto.getEmail());
        if (user == null) {
            return new ResponseEntity<>("User does not exist", false, ReturnType.FAILURE);
        }
        boolean result = authenticationTemplateFactory
                        .createAuthenticationTemplate(authenticationDto.getUserInput())
                        .authenticateAndAuthorize(authenticationDto);
        if (result) {
            return new ResponseEntity<>("User authenticated", true, ReturnType.SUCCESS);
        }
        return new ResponseEntity<>("User not authenticated", false, ReturnType.FAILURE);
    }

}
