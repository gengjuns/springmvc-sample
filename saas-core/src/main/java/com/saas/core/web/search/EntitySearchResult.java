package com.saas.core.web.search;

import com.saas.core.entity.Entity;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @since 17/01/2013 6:04 PM
 */
public class EntitySearchResult<T extends Entity> {
	
    private long iTotalRecords;  
    private long iTotalDisplayRecords;  
    private String sEcho;  

    private long total;

    private List<T> aaData;



    public EntitySearchResult() {
    }


    public EntitySearchResult(long total, List<T> data) {
    	this.iTotalRecords = total;
    	this.iTotalDisplayRecords = total;
        this.total = total;
        this.aaData = data;
    }


    public long getTotal() {
        return total;
    }


    public void setTotal(long total) {
        this.total = total;
    }

	public List<T> getAaData() {
		return aaData;
	}


	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}


	public long getiTotalRecords() {
		return iTotalRecords;
	}


	public void setiTotalRecords(long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}


	public long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}


	public void setiTotalDisplayRecords(long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}


	public String getsEcho() {
		return sEcho;
	}


	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}




}
