/*
package com.example.demo.codequality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openrewrite.*;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.tree.J;
import org.openrewrite.marker.SourceSet;

public class Main {
    private static final String SONARQUBE_URL = "your_sonarqube_url";
    private static final String TOKEN = "your_token";

    public static void main(String[] args) {
        try {
            String apiUrl = SONARQUBE_URL + "/api/issues/search";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((TOKEN + ":").getBytes()));

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.toString());
                if (jsonResponse.has("issues") && jsonResponse.get("issues").isArray()) {

                    Recipe recipe = new Recipe() {
                        @Override
                        public String getDisplayName() {
                            return "";
                        }

                        @Override
                        public String getDescription() {
                            return "";
                        }
                        ExecutionContext ctx = new InMemoryExecutionContext();

                        JsonNode issues = jsonResponse.get("issues");
                        for(JsonNode issue : issues) {
                            System.out.println("Issue: " + issue.toString());
                            System.out.println("Processing issue: " + issue.get("message").asText());

                            String filePath = issue.get("component").asText().split(":")[1];
                            int line = issue.get("line").asInt();
                            String message = issue.get("message").asText();

                            System.out.println("Processing issue: " + message + " at line " + line + " in file " + filePath);

                            // Read the actual file content
                            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

                            // Parse the file content
                            J.CompilationUnit sourceFile = (J.CompilationUnit) JavaParser.fromJavaVersion()
                                    .build()
                                    .parse(fileContent)
                                    .collect(Collectors.toList()).get(0);

                            // Apply the recipe


                            LargeSourceSet sourceSet = LargeSourceSet.from(List.of(sourceFile));
                            RecipeRun results = recipe.run(sourceSet, ctx);




                            // Print the results
                            results.getResults().forEach(result -> {
                                System.out.println("Fixed issue in file: " + result.getAfter().getSourcePath());
                            });

                            // Optionally, write the fixed content back to the file
                            String fixedContent = results.getResults().get(0).getAfter().print();
                            Files.write(Paths.get(filePath), fixedContent.getBytes());
                        }

                    } else {
                        System.out.println("No issues found in the response.");
                    }
                } else {
                    System.out.println("GET request failed. Response Code: " + responseCode);
                }
            } catch (ProtocolException ex) {
                throw new RuntimeException(ex);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

*/



