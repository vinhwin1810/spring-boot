package com.example.lecture2.rooms;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String roomNumber;
    private RoomStatus roomStatus;
    enum RoomStatus {
        AVAILABLE,
        OCCUPIED
    }
    private int version;
}
