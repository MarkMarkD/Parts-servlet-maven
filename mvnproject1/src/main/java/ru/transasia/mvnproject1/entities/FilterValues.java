/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author banashko.dv
 */
public class FilterValues {
    private Map<String,String> filters= new HashMap<>();
    private String sortBy;          //sort by column (1 to 6)
    private String sortingOrder;    //ASC or DESC

    public FilterValues (){}

    public void addNewValue(String key, String value){
        filters.put(key, value);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }
}
