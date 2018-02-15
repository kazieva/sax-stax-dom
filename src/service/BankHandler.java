package service;

import entity.Bank;
import entity.BankEnum;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigInteger;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class BankHandler extends DefaultHandler {
    private Set<Bank> banks;
    private Bank current = null;
    private BankEnum currentEnum = null;
    private EnumSet<BankEnum> withText;
    public BankHandler() {
        banks = new HashSet<>();
        withText = EnumSet.range(BankEnum.NAME, BankEnum.PROFITABILITY);
    }
    public Set<Bank> getBanks() {
        return banks;
    }
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if ("bank".equals(localName)) {
            current = new Bank();
            if (attrs.getLength() == 1) {
                current.setType(attrs.getValue(0));
            }
        } else {
            BankEnum temp = BankEnum.valueOf(localName.toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }
     public void endElement(String uri, String localName, String qName) {
        if ("bank".equals(localName)) {
            banks.add(current);
        }
    }
    public void characters(char[] ch, int start, int length) {
        String s = new String(ch, start, length).trim();
     if (currentEnum != null) {
         switch (currentEnum) {
             case NAME:
                 current.setName(s);
                 break;
             case COUNTRY:
                 current.setCountry(s);
                 break;
             case DEPOSITOR:
                 current.setDepositor(s);
                 break;
             case ACCOUNTID:
                 current.setAccountId(new BigInteger(s));
                 break;
             case AMOUNTONDEPOSIT:
                 current.setAmountOnDeposit(new BigInteger(s));
                 break;
             case PROFITABILITY:
                 current.setProfitability(new BigInteger(s));
                 break;
             default:
                 throw new EnumConstantNotPresentException(
                         currentEnum.getDeclaringClass(), currentEnum.name());
         }
     }
     currentEnum = null;
    }
}