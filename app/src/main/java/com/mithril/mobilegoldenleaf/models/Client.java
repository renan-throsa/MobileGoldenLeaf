package com.mithril.mobilegoldenleaf.models;

public class Client extends User {

    private String identification;
    private int address_id;
    private Boolean status;
    private Boolean notifiable;

    public Client(String name, String phoneNumber, String identification, int address_id, Boolean notifiable) {
        super(name, phoneNumber);
        this.identification = identification;
        this.address_id = address_id;
        this.notifiable = notifiable;
        this.status = true;
    }

    public Client(int id, String name, String phoneNumber, String identification, int address_id, Boolean status, Boolean notifiable) {
        super(id, name, phoneNumber);
        this.identification = identification;
        this.address_id = address_id;
        this.status = status;
        this.notifiable = notifiable;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(Boolean notifiable) {
        this.notifiable = notifiable;
    }


}
