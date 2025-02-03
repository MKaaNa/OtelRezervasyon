package com.MKaaN.OtelBackend.enums;

import java.util.Date;

public class PriceCalculationRequest {
    private Long roomId;
    private Date startDate;
    private Date endDate;

    // Getter ve Setter'lar
    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}