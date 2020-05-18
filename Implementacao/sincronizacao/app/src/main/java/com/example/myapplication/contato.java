package com.example.myapplication;

public class contato {

    private String Name;
    private int syncStatus;

    contato(String Name, int syncStatus){
        this.Name = Name;
        this.syncStatus =syncStatus;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }
}
