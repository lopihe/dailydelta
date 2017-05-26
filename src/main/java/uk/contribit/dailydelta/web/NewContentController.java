package uk.contribit.dailydelta.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.contribit.dailydelta.core.DeltaFinder;
import uk.contribit.dailydelta.core.HttpContentSource;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;
import uk.contribit.dailydelta.core.context.ContextRepository;
import uk.contribit.dailydelta.core.context.Contexts;
import uk.contribit.dailydelta.core.text.HtmlTextSource;
import uk.contribit.dailydelta.core.text.TextSource;
import uk.contribit.dailydelta.core.word.WordRepository;
import uk.contribit.dailydelta.core.word.Words;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class NewContentController {
    @Autowired
    private DeltaFinder deltaFinder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WordRepository wordRepo;

    @Autowired
    private ContextRepository contextRepo;

    @RequestMapping(path = "/check", method = RequestMethod.GET)
    public String addContent(ModelMap model, Principal principal) throws IOException {
        model.addAttribute("account", AccountUtils.lookup(principal, accountService));
        model.addAttribute("content", new NewContentModel());
        return "check";
    }

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public String processContent(@ModelAttribute("content") NewContentModel model, ModelMap m, Principal principal) throws IOException {
        String html = new HttpContentSource(model.getUrl()).get();
        TextSource textSource = new HtmlTextSource(html);

        Account account = AccountUtils.lookup(principal, accountService);
        m.addAttribute("account", account);

        Words words = wordRepo.findOne(account.getId());
        if (words == null) {
            words = new Words(account.getId());
        }

        Map<String, Contexts> delta = deltaFinder.identifyDelta(textSource, words);

        words.addAll(delta.keySet());

        wordRepo.save(words);

        updateContexts(words, delta);

        model.setDelta(delta);

        return "check";
    }

    private void updateContexts(Words words, Map<String, Contexts> delta) {
        for (String word : delta.keySet()) {
            Contexts oldContexts = contextRepo.findOne(word);
            Contexts newContexts = delta.get(word);
            if (oldContexts != null) {
                oldContexts.addAll(newContexts);
            } else {
                oldContexts = newContexts;
            }
            contextRepo.save(oldContexts);
        }
    }
}
