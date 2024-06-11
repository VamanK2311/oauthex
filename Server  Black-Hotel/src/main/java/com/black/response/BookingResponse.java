package com.black.response;

import com.black.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;


    private LocalDate checkInDate;


    private LocalDate checkOutDate;

    private String guestFullName;

    private String guestEmail;

    private int NumOfAdults;

    private int NumOfChidren;

    private int totalNumOfGuest;

    private String bookingConfirmationCode;

    private RoomResponse room;

    public BookingResponse(){}

    public BookingResponse(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
