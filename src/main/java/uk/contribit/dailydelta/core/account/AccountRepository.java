package uk.contribit.dailydelta.core.account;

public interface AccountRepository {
    Account findByEmail(String email);

    Account save(Account account);

    void delete(Account account);
}
