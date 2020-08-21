package sample;


import javafx.beans.property.BooleanProperty;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private Vehicle vehicle;
    private Client client;
    private Card card;
    private LocalDate pickUpDate;
    private LocalDate returnDate;
    private String pickupTime;
    private String returnTime;

    public Reservation(int id, Vehicle vehicle, Client client, LocalDate pickUpDate, LocalDate returnDate, String pickupTime, String returnTime, Card card) {
        this.id = id;
        this.vehicle = vehicle;
        this.client = client;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.card=card;
    }
    public  boolean isOnline(){
        if(card!=null)return true;
        return false;
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }


    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public LocalDate getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDate pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
