package com.dialog.utilities;

import java.math.BigDecimal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GeoPoint {

    private final BigDecimal x;
    private final BigDecimal y;
    private int rounding = 7; // default maximum decimal places - to 1 cm


    public BigDecimal getX() {
        return x;
    }
    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getXRounded(int places) {
        return NumericUtility.roundBD(x, places);
    }
    public BigDecimal getYRounded(int places) {
        return NumericUtility.roundBD(y, places);
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")"); 
    }
    public JSONObject toJson() {
        JSONObject jLocation = new JSONObject();
        jLocation.put("lat", y);
        jLocation.put("lng", x);
        
        return jLocation; 
    }

    public GeoPoint() {
        this.x = BigDecimal.ZERO;
        this.y = BigDecimal.ZERO;
    }
    public GeoPoint(BigDecimal x, BigDecimal y) {
        // make a copy
        this.x = NumericUtility.roundBD(x.add(BigDecimal.ZERO), rounding);
        this.y = NumericUtility.roundBD(y.add(BigDecimal.ZERO), rounding);
    }
    public GeoPoint(BigDecimal x, BigDecimal y, int rounding) {
        // make a copy
        this.rounding = rounding;
        this.x = NumericUtility.roundBD(x.add(BigDecimal.ZERO), rounding);
        this.y = NumericUtility.roundBD(y.add(BigDecimal.ZERO), rounding);
    }
    public GeoPoint(double x, double y) {
        this.x = NumericUtility.roundBD(new BigDecimal(x), rounding);
        this.y = NumericUtility.roundBD(new BigDecimal(y), rounding);
    }
    public GeoPoint(long x, long y) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
    }
    public GeoPoint(int x, int y) {
        this.x = new BigDecimal(x);
        this.y = new BigDecimal(y);
    }


}
