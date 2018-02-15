package controller;

import dao.ValidatorXML;
import entity.Bank;
import service.AbstractBankBuilder;
import service.BankBuilderFactory;
import service.Redirector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/")
public class Controller extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action=request.getParameter("command");
        if("getPage".equals(action)) {
            Redirector.redirectShow(request,response,Integer.parseInt(request.getParameter("number")));
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        BankBuilderFactory sFactory = new BankBuilderFactory();
        AbstractBankBuilder builder = sFactory.createBankBuilder(request.getParameter("command"));
        String path=getServletContext().getRealPath("/data/");
        if(ValidatorXML.validatorXML(path+"bank.xml",path+"bank.xsd")){
            builder.buildSetBanks(path+"bank.xml");

            Set<Bank> cards=builder.getBanks();
            Redirector.redirectShow(request,response,cards);
        }

    }

}
