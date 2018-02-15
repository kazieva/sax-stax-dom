package entity;

public enum BankEnum {
    BANKS("banks"),
    TYPE("type"),
    BANK("bank"),
    NAME("name"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    ACCOUNTID("accountId"),
    AMOUNTONDEPOSIT("amountOnDeposit"),
    PROFITABILITY("profitability");
    private String value;
    private BankEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}