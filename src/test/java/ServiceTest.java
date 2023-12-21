import org.example.ApplicationService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServiceTest {

    ApplicationService service;

    @Before
    void init() {
        service = new ApplicationService();
    }

    @DisplayName("생성되지 않으면 예외를 발생한다.")
    @Test
    void createTest() {
        ApplicationService service = new ApplicationService();


    }
}
