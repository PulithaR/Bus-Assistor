package com.example.busp;

public class routes {
    public String routeto,routefrom,fare;

    public routes(){

    }

    public routes(String routeto, String routefrom,String fare){
        this.routeto = routeto;
        this.routefrom = routefrom;
        this.fare = fare;
    }

    public String getRouteto() {
        return routeto;
    }

    public void setRouteto(String routeto) {
        this.routeto = routeto;
    }

    public String getRoutefrom() {
        return routefrom;
    }

    public void setRoutefrom(String routefrom) {
        this.routefrom = routefrom;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
