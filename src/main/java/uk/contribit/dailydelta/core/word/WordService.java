package uk.contribit.dailydelta.core.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.contribit.dailydelta.core.account.Account;
import uk.contribit.dailydelta.core.word.persistence.PersistentWordDetails;
import uk.contribit.dailydelta.core.word.persistence.PersistentWords;
import uk.contribit.dailydelta.core.word.persistence.WordDetailsRepository;
import uk.contribit.dailydelta.core.word.persistence.WordRepository;

import java.util.Locale;
import java.util.Map;

@Service
public class WordService {
    private static final Logger LOG = LoggerFactory.getLogger(WordService.class);

    @Autowired
    private WordRepository userWordRepository;
    @Autowired
    private WordDetailsRepository globalWordRepository;

    public Words getWords(Account account, Locale locale) {
        PersistentWords pw = userWordRepository.findOne(PersistentWords.getId(account.getId(), locale));
        Words words = pw != null ? pw.getWords() : new Words();
        LOG.info("Words for account {}/{}: {}/{}/{}", account.getEmail(), locale, words.getAllPermanent().size(),
                words.getAllPending().size(), words.getAllIgnored().size());
        return words;
    }

    public void addPending(Account account, Locale locale, Words words, Words delta) {
        LOG.info("Old/new pending words for account {}: {}/{}", account.getEmail(), words.getAllPending().size(), delta.getAllPending().size());
        words.addAllPending(delta.getAllPending());
        save(account, words, locale);
    }

    private void save(Account account, Words words, Locale locale) {
        PersistentWords persistentWords = new PersistentWords(account.getId(), locale, words);
        userWordRepository.save(persistentWords);
    }

    public void synchDetails(Map<String, WordDetails> details, Locale locale) {
        for (String word : details.keySet()) {
            WordDetails oldWordDetails = getWordDetails(word, locale);
            WordDetails newWordDetails = details.get(word);
            if (oldWordDetails != null) {
                newWordDetails.addAll(oldWordDetails);
            }
            PersistentWordDetails pwd = new PersistentWordDetails(newWordDetails, locale);
            globalWordRepository.save(pwd);
        }
    }

    private WordDetails getWordDetails(String word, Locale locale) {
        PersistentWordDetails pwd = globalWordRepository.findOne(PersistentWordDetails.getId(word, locale));
        return pwd != null ? pwd.getDetails() : null;
    }
}
