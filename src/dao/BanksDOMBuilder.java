package dao;

import entity.Bank;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import service.AbstractBankBuilder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class BanksDOMBuilder extends AbstractBankBuilder {

    private static final Logger logger = Logger.getLogger(BanksDOMBuilder.class);
    private Set<Bank> bank;
    private DocumentBuilder docBuilder;
    public BanksDOMBuilder() {
        logger.info("dom");
        this.bank = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try { docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Ошибка конфигурации парсера: " + e);
        }

    }
    public Set<Bank> getBanks() {
        return bank;
    }
    public void buildSetBanks(String fileName) {
        Document doc;
        try{
        doc = docBuilder.parse(fileName);
        Element root = doc.getDocumentElement();
        NodeList banksList = root.getElementsByTagName("bank");
        for (int i = 0; i < banksList.getLength(); i++) {
            Element bankElement = (Element) banksList.item(i);
            Bank newBank = buildCard(bankElement);
            bank.add(newBank);
        }
    }
        catch (IOException e) {
            logger.error("File error or I/O error: " + e);
        } catch (SAXException e) {
            logger.error("Parsing failure: " + e);
          }
    }
    private Bank buildCard(Element bankElement) {
        Bank bank = new Bank();
        bank.setType(bankElement.getAttribute("type"));
        bank.setName(getElementTextContent(bankElement, "name"));
        bank.setCountry(getElementTextContent(bankElement, "country"));
        bank.setDepositor(getElementTextContent(bankElement, "depositor"));
        bank.setAccountId(new BigInteger((getElementTextContent(bankElement, "accountId"))));
        bank.setAmountOnDeposit(new BigInteger((getElementTextContent(bankElement, "amountOnDeposit"))));
        bank.setProfitability(new BigInteger((getElementTextContent(bankElement, "profitability"))));
        return bank;
    }
     private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        String text = node.getTextContent();
        return text;
    }
}