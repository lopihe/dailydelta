package uk.contribit.dailydelta.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String create(Principal principal) {
        AccountUtils.lookup(principal, accountService);
        return "redirect:/check";
    }

    @RequestMapping(path = "/unregister", method = RequestMethod.GET)
    public String delete(Principal principal) {
        Account account = AccountUtils.lookup(principal, accountService);

        if (account != null) {
            accountService.delete(account);
        }
        return "redirect:/";
    }
}
