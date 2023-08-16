package edu.neu.csye7374.template;

import edu.neu.csye7374.dto.AuthenticationDto;
import edu.neu.csye7374.entity.User;
import edu.neu.csye7374.logger.Logger;
import edu.neu.csye7374.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserNameAndPasswordAuthentation extends AuthenticationTemplate{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Logger log;

    @Override
    protected boolean verifyCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        User user = userRepository.findByUsername(authenticationDto.getUsername());
        return user != null && user.getPassword().equals(authenticationDto.getPassword());
    }

    @Override
    protected boolean validateCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        String userName = authenticationDto.getUsername();
        String password = authenticationDto.getPassword();
        boolean validUserName = StringUtils.hasLength(userName) && userName.length() >= 8
                && userName.matches(".*[A-Z].*") && userName.matches(".*[a-z].*");
        boolean validPassword =
                StringUtils.hasLength(password) && password.length() >= 8
                        && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
        return validUserName && validPassword;
    }

    @Override
    protected boolean onSuccess(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with userName  " + authenticationDto.getUsername() + " logged in successfully");
        return true;
    }

    @Override
    protected void onFailure(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with userName " + authenticationDto.getUsername() + " failed to log in");
    }
}
