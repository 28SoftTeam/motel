package com.example.motel;

public class RowItem {

		private String imageId;
	    private String otel;
	    private String bolge;
	    private String kod;
	    private String stars;
	    private String country;
	     
	    public RowItem(String imageId, String otel, String bolge, String stars, String kod,String country) {
	        this.imageId = imageId;
	        this.otel = otel;
	        this.bolge = bolge;
	        this.stars = stars;
	        this.kod=kod;
	        this.country=country;
	    }
	    public String getImageId() {
	        return imageId;
	    }
	    public void setImageId(String imageId) {
	        this.imageId = imageId;
	    }
	    public String getBolge() {
	        return bolge;
	    }
	    public void setBolge(String bolge) {
	        this.bolge = bolge;
	    }
	    public String getStars() {
	        return stars;
	    }
	    public void setStars(String stars) {
	        this.stars = stars;
	    }
	    public String getOtel() {
	        return otel;
	    }
	    public void setOtel(String otel) {
	        this.otel = otel;
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
	    /*@Override
	    public String toString() {
	        return title + "\n" + desc;
	    }*/   
	}