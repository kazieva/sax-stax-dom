package service;

import entity.Bank;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Redirector {
    private static final String showRedirectPage = "showUploadedPage.jsp";
    private static final int pageSize = 4;

    public static void redirectShow(HttpServletRequest req, HttpServletResponse resp, Set<Bank> banks) throws ServletException, IOException {

        ArrayList<Bank> bankList = new ArrayList<>(banks);
        req.getSession().setAttribute("entities", bankList);

        List<Integer> pages = getPagesList(bankList);

        List<Bank> tempList = getTempList(bankList,1);

        req.setAttribute("pageNumber", 1);
        req.setAttribute("entities", tempList);
        req.setAttribute("pages", pages);

        req.getRequestDispatcher(showRedirectPage).forward(req, resp);
    }

    public static void redirectShow(HttpServletRequest req, HttpServletResponse resp, int page) throws ServletException, IOException{

        ArrayList<Bank> bankList = (ArrayList<Bank>) req.getSession().getAttribute("entities");

        List<Integer> pages = getPagesList(bankList);

        List<Bank> tempList = getTempList(bankList,page);

        req.setAttribute("pageNumber", page);
        req.setAttribute("entities", tempList);
        req.setAttribute("pages", pages);

        req.getRequestDispatcher(showRedirectPage).forward(req, resp);
    }
    private static List<Integer> getPagesList(ArrayList<Bank> bankList){
        List<Integer> pages = new ArrayList<>();
        int pagesNumber = (int) Math.ceil(bankList.size()/pageSize);
        for (int i = 0; i < pagesNumber;i++){
            pages.add(i+1);
        }
        return pages;
    }

    private static List<Bank> getTempList(ArrayList<Bank> bankList, int page){
        List<Bank> tempList = new ArrayList<>();
        for (int i = (page-1)*pageSize; i < (page)*pageSize; i++){
            tempList.add(bankList.get(i));
        }
        return tempList;
    }

}
