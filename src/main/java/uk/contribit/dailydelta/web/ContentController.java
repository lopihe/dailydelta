package uk.contribit.dailydelta.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;
import uk.contribit.dailydelta.core.word.WordService;
import uk.contribit.dailydelta.core.word.Words;

import java.security.Principal;
import java.util.Locale;

@Controller
public class ContentController {
    private static final Logger LOG = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private WordService wordService;

    @RequestMapping(path = "/browse", method = RequestMethod.GET)
    public String browse(ModelMap modelMap, @RequestParam(name = "locale", required = false) Locale locale, Principal principal) {
        Account account = AccountUtils.lookup(principal, accountService);
        modelMap.addAttribute("account", account);

        if (locale == null) {
            locale = Locale.ENGLISH;
        }

        LOG.info("Locale: {}", locale);

        Words words = wordService.getWords(account, locale);
//        Iterable<WordDetails> contexts = contextRepository.findAll(words.getAll());

        modelMap.addAttribute("words", words);
  //      modelMap.addAttribute("contexts", contexts);
        return "browse";
    }
}
