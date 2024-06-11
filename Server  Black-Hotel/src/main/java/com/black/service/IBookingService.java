package com.black.service;

import com.black.model.BookedRoom;

import java.util.List;

public interface IBookingService {
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId);
    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();
}
