package edu.neu.csye7374.template;

import edu.neu.csye7374.dto.AuthenticationDto;
import edu.neu.csye7374.entity.User;
import edu.neu.csye7374.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BioMetricAuthentiacation extends AuthenticationTemplate {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected boolean validateCredentials(AuthenticationDto authenticationDto) {
        return authenticationDto.getFingerPrint() != null &&
                authenticationDto.getFingerPrint().length() >= 30 &&
                authenticationDto.getFingerPrint().matches(".*[0-9].*");
    }

    @Override
    protected boolean verifyCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        User user = userRepository.findByUsername(authenticationDto.getUsername());
        return user != null && user.getFingerPrint().equals(authenticationDto.getFingerPrint());
    }

    @Override
    protected boolean onSuccess(AuthenticationDto authenticationDto) {
        // Send email to the user

        return true;
    }

    @Override
    protected void onFailure(AuthenticationDto authenticationDto) {
        // Send email to the user
    }
}
