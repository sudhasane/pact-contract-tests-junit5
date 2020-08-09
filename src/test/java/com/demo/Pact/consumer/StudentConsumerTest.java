package com.demo.Pact.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
public class StudentConsumerTest {

    @Pact(provider = "test-provider", consumer = "test-consumer")
    public RequestResponsePact executeStudentDetailsandget200Response(PactDslWithProvider builder) {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        DslPart etaResults = new PactDslJsonBody()
                .integerType("id", 1)
                .stringType("firstName","Vernon")
                .stringType("lastName","Harper")
                .stringType("email","egestas.rhoncus.Proin@massaQuisqueporttitor.org")
                .stringType("programme", "Financial Analysis")
                .array("courses")
                .stringValue("Accounting")
                .string("Statistics")
                . closeArray();


        return builder
                .given("There is a student with id 1")
                .uponReceiving("A request to get student details  with id 1")
                .path("/student/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(etaResults).toPact();

    }

    @Test
    @PactTestFor(pactMethod = "executeStudentDetailsandget200Response")
    public void should_get_studentDetails_details(MockServer mockServer) {
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockServer.getUrl() + "/student/1", String.class);
    }

}
