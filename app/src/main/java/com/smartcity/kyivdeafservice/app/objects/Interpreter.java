package com.smartcity.kyivdeafservice.app.objects;

/**
 * Created by andrii on 06.09.15.
 */
public class Interpreter {

    private String name;
    private String photoUrl;
    private String bigPhotoUrl;
    private String pricePerHour;
    private String workSchedule;
    private boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getBigPhotoUrl() {
        return bigPhotoUrl;
    }

    public void setBigPhotoUrl(String bigPhotoUrl) {
        this.bigPhotoUrl = bigPhotoUrl;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
