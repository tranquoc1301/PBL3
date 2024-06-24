package com.example.myapplication.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {
    @SerializedName("cod")
    private String cod;
    @SerializedName("msg")
    private String message;
    @SerializedName("errors")
    private List<Errors> error;



    public class Errors {
        @SerializedName("msg")
        private String msg;

        public Errors(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public List<Errors> getError() {
        return error;
    }

    public Message(String cod, String message, List<Errors> error, String msg) {
        this.cod = cod;
        this.message = message;
        this.error = error;
    }

    public void setError(List<Errors> error) {
        this.error = error;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
