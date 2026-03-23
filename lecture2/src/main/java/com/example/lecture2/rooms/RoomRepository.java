package com.example.lecture2.rooms;

import lombok.*;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
// import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final JdbcTemplate jdbcTemplate;

    // Map 1 row từ DB → Room object
    private final RowMapper<Room> rowMapper = (rs, rowNum) -> {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setRoomStatus(Room.RoomStatus.valueOf(rs.getString("status")));
        room.setVersion(rs.getInt("version"));
        return room;
    };

    public List<Room> findAll() {
        String sql = "SELECT * FROM Rooms";
        return jdbcTemplate.query(sql, rowMapper);
    }
    // SQL injection là khi attacker chèn code SQL độc hại vào input để thay đổi hành vi của câu SQL. 
    // Sử dụng PreparedStatement (hoặc JdbcTemplate với placeholder) giúp tránh điều này bằng cách tách biệt code và data.
    public Room findById(Long id) {
        String sql = "SELECT * FROM Rooms WHERE id = ?"; // ? là placeholder cho parameter, giúp tránh SQL injection
        List<Room> room = jdbcTemplate.query(sql, rowMapper, id);
        if (room.isEmpty()) {
            return null;
        } else {
            return room.get(0);
        }
    }
    // 
    public void update(Room room) {
        // updateWithOptimisticLocking(room);
        updateWithPessimisticLocking(room);
    }

    private void updateWithPessimisticLocking(Room room) {
        String sql = "SELECT * FROM Rooms WHERE id = ? FOR UPDATE"; // FOR UPDATE sẽ khóa dòng dữ liệu này cho đến khi transaction kết thúc
        jdbcTemplate.query(sql, rowMapper, room.getId()); // thực hiện câu SQL để khóa dòng dữ liệu
        sql = "UPDATE Rooms SET room_number = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, room.getRoomNumber(), room.getRoomStatus().name(), room.getId());
    }
    private void updateWithOptimisticLocking(Room room) {
        String sql = "UPDATE Rooms SET room_number = ?, status = ?, version = version + 1 WHERE id = ? AND version = ?";
        int rowsAffected = jdbcTemplate.update(sql, room.getRoomNumber(), room.getRoomStatus().name(), room.getId(), room.getVersion());
        if (rowsAffected == 0) {
            throw new RuntimeException("Room was already booked by another request");
        }
    }

    public Room findByIdForUpdate(Long id) {
    String sql = "SELECT * FROM Rooms WHERE id = ? FOR UPDATE";
    List<Room> rooms = jdbcTemplate.query(sql, rowMapper, id);
    return rooms.isEmpty() ? null : rooms.get(0);
}


}
