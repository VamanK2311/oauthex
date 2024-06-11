package com.black.response;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomResponse {
    private long id;
    private String roomType;
    private BigDecimal roomPrice;
    private boolean isBooked;

    private String photo; // actualy we getting from database is string of byte we need to convert

    private List<BookingResponse> bookings;

    public RoomResponse(long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse(long id, String roomType, BigDecimal roomPrice, boolean isBooked, byte[] photoBytes) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes):null;
//        this.bookings = bookings;
    }
}
