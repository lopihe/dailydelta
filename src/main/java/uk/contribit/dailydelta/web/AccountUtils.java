package uk.contribit.dailydelta.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;

import java.security.Principal;
import java.util.Map;

public class AccountUtils {
    public static Account lookup(Principal principal, AccountService accountService) {
        Authentication userAuthentication = ((OAuth2Authentication) principal).getUserAuthentication();
        Map<String, String> authenticationDetail = (Map<String, String>) userAuthentication.getDetails();
        String email = authenticationDetail.get("email");
        return accountService.ensureLoggedIn(email);
    }
}
