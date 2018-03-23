/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.servlets;

import ru.transasia.mvnproject1.entities.FilterValues;
import ru.transasia.mvnproject1.entities.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.transasia.mvnproject1.model.Model;

/**
 *
 * @author banashko.dv
 */
@ApplicationScoped
@WebServlet(name = "ListServlet", urlPatterns = {"/ListServlet"})
public class ListServlet extends HttpServlet {
    
    @Inject
    Model model;
    
    Date myTestDate;

    private List<Part> parts = null;
    private String lastSortBy;              //last sorting by column
    private String sortingOrder = "ASC";
    //FilterValues filter = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        FilterValues filterMap = new FilterValues();
        
        //Get entity parameters from POST request
        String partName = req.getParameter("pname");
        if (!partName.equals("")) filterMap.addNewValue("partName", partName);
        
        String partNumber = req.getParameter("pnumber");
        if (!partNumber.equals("")) filterMap.addNewValue("partNumber", partNumber);
        
        String vendor = req.getParameter("vendor");
        if (!vendor.equals("")) filterMap.addNewValue("vendor", vendor);
        
        String quantity = req.getParameter("qty");
        if (!quantity.equals("")) filterMap.addNewValue("qty", quantity);
        
        //TODO Change date format into string h2 accepts
        String shpdAfter = req.getParameter("shippedAfter");
        if (!shpdAfter.equals("")) filterMap.addNewValue("shpdAfter", shpdAfter);
        Date shippedAfter = null;
        
        String shpdBefore = req.getParameter("shippedBefore");
        if (!shpdBefore.equals("")) filterMap.addNewValue("shpdBefore", shpdBefore);
        Date shippedBefore = null;
        
        String rcvdAfter = req.getParameter("receivedAfter");
        if (!rcvdAfter.equals("")) filterMap.addNewValue("rcvdAfter", rcvdAfter);
        Date receivedAfter = null;
        
        String rcvdBefore = req.getParameter("receivedBefore");
        if (!rcvdBefore.equals("")) filterMap.addNewValue("rcvdBefore", rcvdBefore);
        Date receivedBefore = null;

        //get sorting parameter from POST request and define order
        String sortBy = req.getParameter("sortByColumn");
        if (sortBy != null) {
            if (sortBy.equals(lastSortBy) && sortingOrder.equals("ASC")) {
                sortingOrder = "DESC";
            }
            else if (sortBy.equals(lastSortBy) && sortingOrder.equals("DESC")) {
                sortingOrder = "ASC";
            }
            else if (!sortBy.equals(lastSortBy)){
                sortingOrder = "ASC";
                lastSortBy = sortBy;
            }
        }
        else sortingOrder = "ASC";

        //parse from String into Integer and Date
        try {
            if (!quantity.equals("")) qty = Integer.parseInt(quantity);
            if (!shpdAfter.equals("")) shippedAfter = parseMyDate(shpdAfter);
            if (!shpdBefore.equals("")) shippedBefore = parseMyDate(shpdBefore);
            if (!rcvdAfter.equals("")) receivedAfter = parseMyDate(rcvdAfter);
            if (!rcvdBefore.equals("")) receivedBefore = parseMyDate(rcvdBefore);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //creates new entity FilterValues with all the filter values entered by user
//        FilterValues fv = new FilterValues (partName, partNumber, vendor, qty, shippedAfter, shippedBefore,
//                receivedAfter, receivedBefore, sortBy, sortingOrder);

        //send created filter entity to Model and get list of filtered parts
        //Model model = Model.getInstance();
        //parts = null;
        System.out.println("check1--------------------------");
        try {
            parts = model.getListOfPartsFromDB(fv);
            System.out.println("check2--------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //set request attributes so that filter will still be applied
        req.setAttribute("listOfParts", parts);
        req.setAttribute("pnumber", partNumber);
        req.setAttribute("pname", partName);
        req.setAttribute("vendor", vendor);
        req.setAttribute("qty", quantity);
        req.setAttribute("shippedAfter", shpdAfter);
        req.setAttribute("shippedBefore", shpdBefore);
        req.setAttribute("receivedAfter", rcvdAfter);
        req.setAttribute("receivedBefore", rcvdBefore);
        req.setAttribute("sortingOrder", sortingOrder);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(req, resp);
    }
    
    
    //method creates and returns Date object out of String value entered by user
    private Date parseMyDate(String someDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        try {
            return sdf.parse(someDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect date entered");
        return null;
    }
}
