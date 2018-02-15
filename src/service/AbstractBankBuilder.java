package service;

import entity.Bank;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBankBuilder {
    protected Set<Bank> bank;

    public AbstractBankBuilder() {
        bank = new HashSet<Bank>();
    }

    public Set<Bank> getBanks() {
        return bank;
    }

    abstract public void buildSetBanks(String fileName);
}