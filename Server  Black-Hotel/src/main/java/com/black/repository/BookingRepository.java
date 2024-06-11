package com.black.repository;

import com.black.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom, Long> {

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByRoomId(Long roomId);
}
