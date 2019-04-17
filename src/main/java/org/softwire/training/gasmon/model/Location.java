package org.softwire.training.gasmon.model;

import com.google.common.base.MoreObjects;


public class Location {

    private double x;
    private double y;
    private String id;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location(double x, double y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    //private string tostring use moreobjects.toStringhelper this.getClass .add name "ID", id etc ...toString;
    private String tostring(){
        return MoreObjects.toStringHelper(this.getClass())
            .add("ID", id)
            .add("x", x)
            .add("y", y)
            .toString();
    }
}
