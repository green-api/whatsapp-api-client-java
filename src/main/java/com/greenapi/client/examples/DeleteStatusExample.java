package com.greenapi.client.examples;

import com.greenapi.client.pkg.api.GreenApi;
import com.greenapi.client.pkg.models.request.DeleteStatusReq;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class DeleteStatusExample {

    public static void main(String[] args) {
        var restTemplate = new RestTemplate();
        var greenApi = new GreenApi(
            restTemplate,
            "https://media.green-api.com",
            "https://api.green-api.com",
            "YOUR_INSTANCE_ID",
            "YOUR_TOKEN");

        new DeleteStatusExample().deleteStatusExample(greenApi);
    }

    /**
     * Deletes a previously sent WhatsApp status by its message id.
     * Beta feature - requires access granted by support@green-api.com.
     */
    private void deleteStatusExample(GreenApi greenApi) {
        var response = greenApi.statuses.deleteStatus(
            DeleteStatusReq.builder()
                .idMessage("BAE5F4886F6F2D05")
                .build());

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Status deleted successfully");
        } else {
            log.warn("Failed to delete status, status: " + response.getStatusCode());
        }
    }
}
