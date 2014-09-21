package com.example.motel;

public class classSearchedRows {

	private String name;
    private String country;
    private String count;
    private String kod;
    private String type;
    private String bolge;
     
    public classSearchedRows(String name, String country, String count, String kod, String type,String bolge) {
        this.name=name;
        this.country = country;
        this.bolge = bolge;
        this.count=count;
        this.kod=kod;
        this.type=type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBolge() {
        return bolge;
    }
    public void setBolge(String bolge) {
        this.bolge = bolge;
    }
    public String getCount() {
        return count;
    }
    public void setStars(String count) {
        this.count = count;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getKod() {
        return kod;
    }
    public void setKod(String kod) {
        this.kod = kod;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country=country;
    }
    
}
