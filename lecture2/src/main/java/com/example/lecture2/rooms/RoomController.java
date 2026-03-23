package com.example.lecture2.rooms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/")
    // ResponseEntity là một wrapper cho HTTP response, giúp chúng ta dễ dàng trả về status code, headers và body.
    // Khi sử dụng ResponseEntity.ok(), nó sẽ trả về status code 200 OK và body chứa dữ liệu. Nếu có lỗi, chúng ta có thể trả về ResponseEntity.badRequest() với message lỗi.
    // List<Room> sẽ được tự động chuyển đổi thành JSON khi trả về response, nhờ vào sự hỗ trợ của Spring Boot và Jackson (thư viện mặc định để xử lý JSON).
    // Khi client gửi request đến endpoint này, Spring Boot sẽ gọi phương thức getRooms(), phương thức này sẽ gọi RoomService để lấy danh sách phòng và trả về cho client dưới dạng JSON.
    // Ví dụ response JSON:
    // [
    //   {
    //     "id": 1,
    //     "roomNumber": "101",
    //     "roomStatus": "AVAILABLE",
    //     "version": 0
    //   }
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/book/{id}")
    // Khi client gửi POST request đến /api/rooms/book với parameter roomId, phương thức bookRoom() sẽ được gọi. 
    // Phương thức này sẽ gọi RoomService để thực hiện việc đặt phòng. 
    // Nếu thành công, nó sẽ trả về message "Room booked successfully". 
    // Nếu có lỗi (ví dụ: phòng không tồn tại hoặc đã bị đặt), nó sẽ trả về status code 400 Bad Request cùng với message lỗi.
    public ResponseEntity<String> bookRoom(@PathVariable Long id) throws InterruptedException {
        try {
            roomService.book(id);
            return ResponseEntity.ok("Room booked successfully");
        } catch (RuntimeException e) {
            log.error("Error booking room: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
