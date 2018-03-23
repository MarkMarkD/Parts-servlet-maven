/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.model;

import ru.transasia.mvnproject1.entities.FilterValues;
import ru.transasia.mvnproject1.entities.Part;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author banashko.dv
 */
@ApplicationScoped
public class Model {
    static int counter = 0;
    //private static Model instance = new Model();

    private List<Part> listOfParts;   //РїСЂРѕРјРµР¶СѓС‚РѕС‡РЅС‹Р№ РјР°СЃСЃРёРІ РјРµР¶РґСѓ Р”Р‘ Рё view

//    public static Model getInstance() {
//        return instance;
//    }

    public Model() {
        listOfParts = new ArrayList<Part>();
        System.out.println("counter = " + counter);
        counter++;
    }

    //Get filtered positions from DB (if filter == null then get all positions)
    public List<Part> getListOfPartsFromDB(FilterValues filter) throws SQLException, ClassNotFoundException {
        listOfParts.clear();
        ResultSet resultSet = null;
        Connection conn = DBConnUtils.getConnection();
        Statement statement = conn.createStatement();;
        String query = null;
        System.out.println("Connection to db established from method getListOfPartsFromDB");

        if (filter == null) {
            query = "SELECT * FROM public.tableofparts";
        }
        else {
            //create query string and apply all the filter fields entered by user
            query = "SELECT * FROM public.tableofparts WHERE qty >= '0'";
            if ((filter.getPartName() != null) && (!filter.getPartName().equals("null"))) {
                query = query + " AND pname LIKE '%" + filter.getPartName() + "%'";
            }
            if ((filter.getPartNumber() != null) && (!filter.getPartNumber().equals("null"))) {
                query = query + " AND pnumber LIKE '%" + filter.getPartNumber() + "%'";
            }
            if ((filter.getVendor() != null) && (!filter.getVendor().equals("null"))) {
                query = query + " AND vendor LIKE '%" + filter.getVendor() + "%'";
            }
            if (filter.getQty() >= 0) {
                query = query + " AND qty >= '" + filter.getQty() + "'";
            }
//            if ((filter.getShippedAfter() != null) && (!filter.getShippedAfter().equals("null"))) {
//                query = query + " AND shipped < PARSEDATETIME('20160101','yyyyMMdd')";
//                System.out.println("--------------------------------------" + filter.getShippedAfter());
//            }
            
            if ((filter.getShippedBefore() != null) && (!filter.getShippedBefore().equals("null"))) {
                query = query + " AND shipped < PARSEDATETIME('" + parseMyDate(filter.getShippedBefore()) + "','yyyyMMdd')";
                System.out.println("--------------------------------------" + parseMyDate(filter.getShippedBefore()));
            }
            
            if ((filter.getReceivedAfter() != null) && (!filter.getReceivedAfter().equals("null"))) {
                query = query + " AND received > PARSEDATETIME('" + parseMyDate(filter.getReceivedAfter()) + "','yyyyMMdd')";
                System.out.println("--------------------------------------" + parseMyDate(filter.getReceivedAfter()));
            }
            if ((filter.getShippedAfter() != null) && (!filter.getShippedAfter().equals("null"))) {
                query = query + " AND shipped > PARSEDATETIME('" + parseMyDate(filter.getShippedAfter()) + "','yyyyMMdd')";
                System.out.println("--------------------------------------" + parseMyDate(filter.getShippedAfter()));
            }
            if ((filter.getReceivedBefore() != null) && (!filter.getReceivedBefore().equals("null"))) {
                query = query + " AND received < PARSEDATETIME('" + parseMyDate(filter.getReceivedBefore()) + "','yyyyMMdd')";
                System.out.println("--------------------------------------" + parseMyDate(filter.getReceivedBefore()));
            }
            if (filter.getSortBy() != null) {
                query = query + " ORDER BY " + filter.getSortBy() + " " + filter.getSortingOrder();
            }
            //query = "SELECT * FROM public.tableofparts WHERE pnumber LIKE '%01%' AND pname LIKE '%LCD%'";
        }

        resultSet = statement.executeQuery(query);
        //fill list of parts with filtered entities
        while (resultSet.next()) {
            String partName = resultSet.getString("Pname");
            String partNumber = resultSet.getString("Pnumber");
            String vendor = resultSet.getString("Vendor");
            int qty = resultSet.getInt("Qty");
            Date shipped = resultSet.getDate("Shipped");
            Date received = resultSet.getDate("Received");
            Part part = new Part(partName, partNumber, vendor, qty, shipped, received);
            listOfParts.add(part);
        }

        DBConnUtils.closeQuietly(conn);
        System.out.println("Connection to db closed from method getListOfPartsFromDB");
        return listOfParts;
    }
//    public Date parseIntoH2Date(Date date) {
//            String preparedDate = date.toString();
//            System.out.println("parseIntoH2Date: converted date " + date + " into String " + preparedDate);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        try {
//            return sdf.parse(preparedDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Error from parseIntoH2Date");
//            return null;
//        }
    
    private String parseMyDate(Date someDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        try {
            String newDate = sdf.format(someDate);
            return newDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect date entered");
        return null;
    }
}
