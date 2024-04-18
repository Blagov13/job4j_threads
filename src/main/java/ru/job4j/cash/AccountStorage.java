package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        if (fromAccount.isEmpty() || toAccount.isEmpty() || fromAccount.get().amount() < amount) {
            return false;
        }
        update(new Account(fromAccount.get().id(), fromAccount.get().amount() - amount));
        update(new Account(toAccount.get().id(), toAccount.get().amount() + amount));
        return true;
    }
}
