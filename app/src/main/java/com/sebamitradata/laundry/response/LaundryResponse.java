package com.sebamitradata.laundry.response;

import com.sebamitradata.laundry.model.LaundryModel;

import java.util.List;

public class LaundryResponse {
    private String success, message;
    private List<LaundryModel> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LaundryModel> getData() {
        return data;
    }

    public void setData(List<LaundryModel> data) {
        this.data = data;
    }
}
