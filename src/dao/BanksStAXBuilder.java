package dao;

import entity.Bank;
import entity.BankEnum;
import org.apache.log4j.Logger;
import service.AbstractBankBuilder;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class BanksStAXBuilder extends AbstractBankBuilder {
    private static final Logger logger = Logger.getLogger(BanksStAXBuilder.class);
    private HashSet<Bank> banks = new HashSet<>();
    private XMLInputFactory inputFactory;

    public BanksStAXBuilder() {
        logger.info("stax");
        inputFactory = XMLInputFactory.newInstance();
    }

    public Set<Bank> getBanks() {
        return banks;
    }

    public void buildSetBanks(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (BankEnum.valueOf(name.toUpperCase()) == BankEnum.BANK) {
                        Bank st = buildCard(reader);
                        banks.add(st);
                    }
                }
            }
        } catch (XMLStreamException ex) {
           logger.error("StAX parsing error! " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            logger.error("File " + fileName + " not found! " + ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("Impossible close file " + fileName + " : " + e);
            }
        }
    }

    private Bank buildCard(XMLStreamReader reader) throws XMLStreamException {
        Bank st = new Bank();
        st.setType(reader.getAttributeValue(null, BankEnum.TYPE.getValue()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (BankEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            st.setName(getXMLText(reader));
                            break;
                        case COUNTRY:
                            st.setCountry(getXMLText(reader));
                            break;
                        case DEPOSITOR:
                            st.setDepositor(getXMLText(reader));
                            break;
                        case ACCOUNTID:
                            st.setAccountId(new BigInteger(getXMLText(reader)));
                            break;
                        case AMOUNTONDEPOSIT:
                            st.setAmountOnDeposit(new BigInteger(getXMLText(reader)));
                            break;
                        case PROFITABILITY:
                            st.setProfitability(new BigInteger(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (BankEnum.valueOf(name.toUpperCase()) == BankEnum.BANK) {
                        return st;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Card");
    }
    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}