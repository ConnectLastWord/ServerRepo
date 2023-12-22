import org.example.RoomManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomManagerTest {
    RoomManager mgr = new RoomManager();

    @Test
    @DisplayName("빈 값이 들어오면 예외를 발생한다.")
    void emptyRoomName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> mgr.validateRoomName("   "));
    }

}
