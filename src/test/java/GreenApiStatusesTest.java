import com.greenapi.client.pkg.models.request.SendMediaStatusResq;
import com.greenapi.client.pkg.models.request.SendTextStatusResq;
import com.greenapi.client.pkg.models.request.SendVoiceStatusResq;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Log4j2
public class GreenApiStatusesTest extends GreenApiTest {

    @Test
    void sendTextStatus()  {
        SendTextStatusResq request = new SendTextStatusResq(
                "Hello from Green-API!",
                "#FF0000",
                "SERIF",
                Arrays.asList("79777055473@c.us")
        );

        var textStatusResult = greenApi.statuses.sendTextStatus(request);
        System.out.println("Text Status Result:");
        System.out.println(textStatusResult);
        System.out.println();
    }

    @Test
    void sendVoiceStatus()  {
        SendVoiceStatusResq request = new SendVoiceStatusResq(
                "https://example.com/voice.mp3",
                "voice_message.mp3",
                "#00FF00",
                Arrays.asList("79777055473@c.us")
        );

        var voiceStatusResult = greenApi.statuses.sendVoiceStatus(request);
        System.out.println("Voice Status Result:");
        System.out.println(voiceStatusResult);
        System.out.println();
    }

    @Test
    void sendMediaStatus() {
        SendMediaStatusResq request = new SendMediaStatusResq(
                "https://example.com/image.jpg",
                "sunset.jpg",
                "Beautiful sunset!",
                Arrays.asList("79777055473@c.us")
        );

        var mediaStatusResult = greenApi.statuses.sendMediaStatus(request);
        System.out.println("Media Status Result:");
        System.out.println(mediaStatusResult);
        System.out.println();
    }

    @Test
    void getIncomingStatuses24h()  {
        var incomingStatuses = greenApi.statuses.getIncomingStatuses();

        System.out.println("Incoming Statuses (24h):");
        System.out.println(incomingStatuses);
        System.out.println();
    }

    @Test
    void getIncomingStatuses60min() {
        var incomingStatuses = greenApi.statuses.getIncomingStatuses(60);

        System.out.println("Incoming Statuses (60 min):");
        System.out.println(incomingStatuses);
        System.out.println();
    }

    @Test
    void getOutgoingStatuses24h(){
        var outgoingStatuses = greenApi.statuses.getOutgoingStatuses();

        System.out.println("Outgoing Statuses (24h):");
        System.out.println(outgoingStatuses);
        System.out.println();
    }

    @Test
    void getOutgoingStatuses60min() {
        var outgoingStatuses = greenApi.statuses.getOutgoingStatuses(60);

        System.out.println("Outgoing Statuses (60 min):");
        System.out.println(outgoingStatuses);
        System.out.println();
    }

    @Test
    void getStatusStatistics() {
        var statusStatistics = greenApi.statuses.getStatusStatistic("3EB0C767D097B7C7C81");

        System.out.println("GetStatusStatistic Statistics:");
        System.out.println(statusStatistics);
        System.out.println();
    }
}
