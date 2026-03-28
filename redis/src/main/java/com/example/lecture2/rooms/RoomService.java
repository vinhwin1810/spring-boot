package com.example.lecture2.rooms;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lecture2.exception.RoomNotFoundException;
import com.example.lecture2.exception.RoomOccupiedException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    @Transactional
    public void book(Long roomId) throws InterruptedException {
        bookWithPessimisticLocking(roomId);
    }

    private void bookWithPessimisticLocking(Long roomId) throws InterruptedException {
        log.info("booking room with id: {}", roomId);
        Room room = roomRepository.findByIdForUpdate(roomId); // ← lock ngay từ đầu
        Thread.sleep(5000); // giả lập xử lý chậm
        if (room == null) {
            log.error("Room with id {} not found", roomId);
            throw new RoomNotFoundException("Room not found");
        }
        if (room.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
            log.error("Room with id {} is already occupied", roomId);
            throw new RoomOccupiedException("Room is already occupied");
        }
        room.setRoomStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.update(room);
        log.info("Room with id {} booked successfully", roomId);
    }


    private void bookWithOptimisticLocking(Long roomId) throws InterruptedException {
         log.info("booking room with id: {}", roomId);
        Room room = roomRepository.findById(roomId);
        Thread.sleep(5000); // giả lập xử lý chậm
        if (room == null) {
            log.error("Room with id {} not found", roomId);
            throw new RoomNotFoundException("Room not found");
        }
        if (room.getRoomStatus() == Room.RoomStatus.OCCUPIED) {
            log.error("Room with id {} is already occupied", roomId);
            throw new RoomOccupiedException("Room is already occupied");
        }
        if (room.getVersion() == 0) {
            log.error("Room with id {} has been updated by another transaction", roomId);
        }
        room.setRoomStatus(Room.RoomStatus.OCCUPIED);
        roomRepository.update(room);
    }
}
