package net.langlet.batch.model;

import java.util.List;

public class ReponseAPI {
    private List<AdresseSociete> data;
    private int count;
    private String status;

    public ReponseAPI() {
    }

    public ReponseAPI(List<AdresseSociete> data, int count, String status) {
        this.data = data;
        this.count = count;
        this.status = status;
    }

    public List<AdresseSociete> getData() {
        return data;
    }

    public void setData(List<AdresseSociete> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
