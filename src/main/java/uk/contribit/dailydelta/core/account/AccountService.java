package uk.contribit.dailydelta.core.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.contribit.dailydelta.core.word.WordRepository;

import java.util.UUID;

@Service
public class AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    private AccountRepository accountRepository;
    private WordRepository wordRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, WordRepository wordRepository) {
        this.accountRepository = accountRepository;
        this.wordRepository = wordRepository;
    }

    public Account register(String email) {
        Account account = new Account(UUID.randomUUID(), email);
        LOG.debug("Registering account {} with id {}", account.getEmail(), account.getId());
        return accountRepository.save(account);
    }

    public Account ensureLoggedIn(String email) {
        LOG.debug("Logging in {}", email);
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            LOG.debug("No account found for {}, automatically registering", email);
            account = register(email);
        }
        return account;
    }

    public void delete(Account account) {
        LOG.debug("Deleting account and words for {} with id {}", account.getEmail(), account.getId());
        accountRepository.delete(account);
        wordRepository.delete(account.getId());
    }
}
