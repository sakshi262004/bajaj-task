package com.example.demo;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class WorkflowService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void startProcess() {
        try {
            // Step 1: Register yourself
            String registerUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

            Map<String, String> requestBody = Map.of(
                    "name", "Sakshi Ladda",
                    "regNo", "REG12347", // <-- replace with your reg no
                    "email", "sakshi@example.com");

            ResponseEntity<Map> response = restTemplate.postForEntity(registerUrl, requestBody, Map.class);

            String webhookUrl = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            System.out.println("Webhook URL: " + webhookUrl);
            System.out.println("Access Token: " + accessToken);

            // Step 2: Replace this with actual SQL answer
            String finalQuery = "SELECT * FROM students;";

            // Step 3: Submit query
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            Map<String, String> answerBody = Map.of("finalQuery", finalQuery);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(answerBody, headers);

            ResponseEntity<String> submitResponse = restTemplate.postForEntity(webhookUrl, entity, String.class);

            System.out.println("Submission response: " + submitResponse.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
