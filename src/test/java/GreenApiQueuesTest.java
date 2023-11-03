import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Log4j2
class GreenApiQueuesTest extends GreenApiTest {

    @Test
    void showMessagesQueue() {
        var response = greenApi.queues.showMessagesQueue();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void clearMessagesQueue() {
        var response = greenApi.queues.clearMessagesQueue();
        log.info(response);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}