package service;

import dao.BanksDOMBuilder;
import dao.BanksSAXBuilder;
import dao.BanksStAXBuilder;

public class BankBuilderFactory {
    private enum TypeParser {
        SAX,
        STAX,
        DOM
    }
    public AbstractBankBuilder createBankBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new BanksDOMBuilder();
            case STAX:
                return new BanksStAXBuilder();
            case SAX:
                return new BanksSAXBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
