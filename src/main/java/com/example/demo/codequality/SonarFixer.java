/*
package com.example.demo.codequality;

import org.sonarqube.ws.client.HttpConnector;
import org.sonarqube.ws.client.issue.IssueClient;
import org.sonarqube.ws.client.issue.SearchRequest;
import org.sonarqube.ws.client.issue.SearchResponse;

public class SonarQubeFixer {
    public static void main(String[] args) {
        HttpConnector connector = HttpConnector.newBuilder()
                .url("http://localhost:9000")
                .build();

        IssueClient issueClient = connector.issueClient();
        SearchRequest request = new SearchRequest();
        request.setProjectKeys("your-project-key");

        SearchResponse response = issueClient.search(request);
        response.getIssuesList().forEach(issue -> {
            // Process each issue and apply fixes using OpenRewrite
            System.out.println("Issue: " + issue.getMessage());
            // Add logic to apply fixes using OpenRewrite
        });
    }
}

*/
