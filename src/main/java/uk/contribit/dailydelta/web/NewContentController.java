package uk.contribit.dailydelta.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.contribit.dailydelta.core.HttpContentSource;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.account.AccountService;
import uk.contribit.dailydelta.core.text.HtmlTextSource;
import uk.contribit.dailydelta.core.text.Text;
import uk.contribit.dailydelta.core.text.TextSegmenter;
import uk.contribit.dailydelta.core.text.processor.DeltaProcessor;
import uk.contribit.dailydelta.core.text.processor.WordDetailsProcessor;
import uk.contribit.dailydelta.core.word.WordDetails;
import uk.contribit.dailydelta.core.word.WordService;
import uk.contribit.dailydelta.core.word.Words;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

@Controller
public class NewContentController {
    private static final Logger LOG = LoggerFactory.getLogger(NewContentController.class);

    @Autowired
    private DeltaProcessor deltaFinder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WordService wordService;

    @RequestMapping(path = "/check", method = RequestMethod.GET)
    public String addContent(ModelMap model, Principal principal) throws IOException {
        model.addAttribute("account", AccountUtils.lookup(principal, accountService));
        model.addAttribute("content", new NewContentModel());
        return "check";
    }

    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public String processContent(@ModelAttribute("content") NewContentModel model, ModelMap m, Principal principal) throws IOException {
        Account account = AccountUtils.lookup(principal, accountService);
        m.addAttribute("account", account);

        String html = new HttpContentSource(model.getUrl()).get();
        Text text = new HtmlTextSource(html).getText();

        Words words = wordService.getWords(account, text.getLocale());


        DeltaProcessor deltaProcessor = new DeltaProcessor();
        WordDetailsProcessor detailsProcessor = new WordDetailsProcessor();
        TextSegmenter textSegmenter = new TextSegmenter(deltaProcessor, detailsProcessor);

        textSegmenter.segment(text, words);

        Words delta = deltaProcessor.getDelta();

        LOG.info("Known words in source: {}", detailsProcessor.getKnownDetails().size());

        Map<String, WordDetails> details = new HashMap<>();
        details.putAll(detailsProcessor.getPendingDetails());
        details.putAll(detailsProcessor.getKnownDetails());

        LOG.info("Total words in source: {}", details.size());

        wordService.addPending(account, text.getLocale(), words, delta);
        wordService.synchDetails(details, text.getLocale());

        model.setDelta(new TreeSet<>(detailsProcessor.getPendingDetails().values()));

        return "check";
    }
}
