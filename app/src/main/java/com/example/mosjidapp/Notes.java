package com.example.mosjidapp;

public class Notes {
    int id;
    String startTime,entTime,StartTimeMilli,endTimeMilli,AudioMode;
    int switchStatus;

    public Notes() {
    }

    public Notes(int id,String startTime, String entTime, String startTimeMilli, String endTimeMilli, String audioMode, int switchStatus) {
        this.id = id;
        this.startTime = startTime;
        this.entTime = entTime;
        StartTimeMilli = startTimeMilli;
        this.endTimeMilli = endTimeMilli;
        AudioMode = audioMode;
        this.switchStatus = switchStatus;
    }
    public Notes(String startTime, String entTime, String startTimeMilli, String endTimeMilli, String audioMode, int switchStatus) {
        this.startTime = startTime;
        this.entTime = entTime;
        StartTimeMilli = startTimeMilli;
        this.endTimeMilli = endTimeMilli;
        AudioMode = audioMode;
        this.switchStatus = switchStatus;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEntTime() {
        return entTime;
    }

    public void setEntTime(String entTime) {
        this.entTime = entTime;
    }

    public String getStartTimeMilli() {
        return StartTimeMilli;
    }

    public void setStartTimeMilli(String startTimeMilli) {
        StartTimeMilli = startTimeMilli;
    }

    public String getEndTimeMilli() {
        return endTimeMilli;
    }

    public void setEndTimeMilli(String endTimeMilli) {
        this.endTimeMilli = endTimeMilli;
    }

    public String getAudioMode() {
        return AudioMode;
    }

    public void setAudioMode(String audioMode) {
        AudioMode = audioMode;
    }

    public int getSwitchStatus() {
        return switchStatus;
    }

    public void setSwitchStatus(int switchStatus) {
        this.switchStatus = switchStatus;
    }
}
