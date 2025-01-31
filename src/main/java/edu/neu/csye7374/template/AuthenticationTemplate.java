package edu.neu.csye7374.template;

import edu.neu.csye7374.dto.AuthenticationDto;

public abstract class AuthenticationTemplate {

    public boolean authenticateAndAuthorize(AuthenticationDto authenticationDto) {
        if (validateCredentials(authenticationDto)) {
            if (verifyCredentials(authenticationDto)) {
                return onSuccess( authenticationDto);
            } else {
                onFailure(authenticationDto);
                return false;
            }
        }else {
            onFailure(authenticationDto);
            return false;
        }
    }

    protected abstract boolean validateCredentials(AuthenticationDto authenticationDto);

    protected abstract boolean verifyCredentials(AuthenticationDto authenticationDto);

    protected abstract boolean onSuccess(AuthenticationDto authenticationDto);

    protected abstract void onFailure(AuthenticationDto authenticationDto);

}
