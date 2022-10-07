package com.vttp2.miniproject01.models;

import jakarta.json.JsonObject;

public class Quote {
    private double[] close;
    private double[] low;
    private long[] volume;
    private double[] open;
    private double[] high;

    public double[] getClose() { return close; }
    public void setClose(double[] value) { this.close = value; }

    public double[] getLow() { return low; }
    public void setLow(double[] value) { this.low = value; }

    public long[] getVolume() { return volume; }
    public void setVolume(long[] value) { this.volume = value; }

    public double[] getOpen() { return open; }
    public void setOpen(double[] value) { this.open = value; }

    public double[] getHigh() { return high; }
    public void setHigh(double[] value) { this.high = value; }

    
}
