package com.shaw.model;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public class TokenModel implements Delayed {

    private String value;
    private long time;

    public TokenModel(String value, long time) {
        this.value = value;
        this.time = time * 1000 + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "value='" + value + '\'' +
                ", time=" + time +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time * 1000 + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        TokenModel tokenModel = (TokenModel) o;
        return this.getDelay(TimeUnit.MINUTES) - tokenModel.getDelay(TimeUnit.MINUTES) > 0 ? 1 : 0;
    }

}
