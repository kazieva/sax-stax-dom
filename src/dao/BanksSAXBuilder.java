package dao;

import entity.Bank;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import service.AbstractBankBuilder;
import service.BankHandler;

import java.io.IOException;
import java.util.Set;

public class BanksSAXBuilder extends AbstractBankBuilder {
    private static final Logger logger = Logger.getLogger(BanksSAXBuilder.class);
    private Set<Bank> banks;
    private BankHandler sh;
    private XMLReader reader;
    public BanksSAXBuilder() {
        logger.info("sax");
        sh = new BankHandler();
        try {
            reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(sh);
        } catch (SAXException e) {
            logger.error("ошибка SAX парсера: " + e);
        }
    }
    public Set<Bank> getBanks() {
        return banks;
    }

    public void buildSetBanks(String fileName) {
        try {
            reader.parse(fileName);
        } catch (SAXException e) {
            logger.error("ошибка SAX парсера: " + e);
        } catch (IOException e) {
            logger.error("ошибка I/О потока: " + e);
        }
        banks = sh.getBanks();
    }
}