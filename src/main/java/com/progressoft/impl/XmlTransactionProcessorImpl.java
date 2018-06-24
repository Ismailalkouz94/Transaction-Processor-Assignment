package com.progressoft.impl;

import com.progressoft.induction.tp.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class XmlTransactionProcessorImpl extends BaseTransactionProcessor {

    @Override
    public void importTransactions(InputStream is) {

        String type;
        BigDecimal amount;
        String narration;

        Transaction transactionObj;
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Transaction");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                int order = temp + 1;
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    //for "type" intialize and validation
                    type = eElement.getAttribute("type");
                    checkType(type, order);

                    //for "amount" intialize and validation
                    amount = checkAmount(eElement.getAttribute("amount"), order);

                    //for "narration" intialize and validation
                    narration = eElement.getAttribute("narration");
                    checkNarration(narration, order);

                    transactionObj = new Transaction(type, amount, narration);
                    transactionsList.add(transactionObj);
                }
            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

}
