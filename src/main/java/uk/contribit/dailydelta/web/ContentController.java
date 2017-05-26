package uk.contribit.dailydelta.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;
import uk.contribit.dailydelta.core.context.ContextRepository;
import uk.contribit.dailydelta.core.word.WordRepository;
import uk.contribit.dailydelta.core.word.Words;

import java.security.Principal;

@Controller
public class ContentController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ContextRepository contextRepository;

    @RequestMapping(path = "/browse", method = RequestMethod.GET)
    public String browse(ModelMap modelMap, Principal principal) {
        Account account = AccountUtils.lookup(principal, accountService);

        modelMap.addAttribute("account", account);

        Words words = wordRepository.findOne(account.getId());
//        Iterable<Contexts> contexts = contextRepository.findAll(words.getAll());

        modelMap.addAttribute("words", words);
  //      modelMap.addAttribute("contexts", contexts);
        return "browse";
    }
}
