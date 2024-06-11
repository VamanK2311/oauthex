package com.black.controller;

import com.black.exception.PhotoRetrievalException;
import com.black.exception.ResourceNotFoundException;
import com.black.model.BookedRoom;
import com.black.model.Room;
import com.black.response.RoomResponse;
import com.black.service.BookingService;
import com.black.service.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@Slf4j
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private BookingService bookingsService;

    @PostMapping("/add/newroom")
    public ResponseEntity<RoomResponse> addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice")BigDecimal roomPrice
            ) throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);



        RoomResponse response =  new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(),savedRoom.getRoomPrice());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/roomtype")
    public ResponseEntity<List<String>> getRoomTypes(){
        return ResponseEntity.ok(roomService.getAllRoomTypes());
    }

    @GetMapping("/allrooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {


        List<Room>  rooms = roomService.getAllRooms();

        List<RoomResponse> roomResponses = new ArrayList<>();

        for(Room room: rooms){
            byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length >0){
                String base64Photo = Base64.encodeBase64String(photoBytes);

                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);

            }
        }

        return ResponseEntity.ok(roomResponses);
    }

    @DeleteMapping("/deleteroom/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        byte[] photoBytes = photo != null && !photo.isEmpty()?
        photo.getBytes() : roomService.getRoomPhotoByRoomId(roomId);

        Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) :null;
        Room theRoom = roomService.updateRoom(roomId,roomType,roomPrice,photoBytes);
        theRoom.setPhoto(photoBlob);

        RoomResponse roomResponse = getRoomResponse(theRoom);
        return  ResponseEntity.ok(roomResponse);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.getRoomById(roomId);

        return  theRoom.map(room ->{
            RoomResponse roomResponse = getRoomResponse(room);
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
    }













    private RoomResponse getRoomResponse(Room room) {
        // geting booking history and photo alomg with rooms
        List<BookedRoom> bookings =getAllBookingByRoomId(room.getId());
        /*List<BookingResponse> bookingInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingId()
                        ,booking.getCheckInDate()
                        ,booking.getCheckOutDate(),
                        booking.getBookingConfirmationCode())).toList();
*/
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null){
            try{
                photoBytes = photoBlob.getBytes(1,(int) photoBlob.length());
            }catch (SQLException e){
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }

        return  new RoomResponse(
                room.getId(),
                room.getRoomType(),
                room.getRoomPrice(),
                room.isBooked(),
                photoBytes);
    }

    private List<BookedRoom> getAllBookingByRoomId(Long roomId) {
        return  bookingsService.getAllBookingsByRoomId(roomId);
    }

}
