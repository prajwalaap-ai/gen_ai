package com.example.demo.codequality;

import com.example.demo.DemoApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openrewrite.*;
import org.openrewrite.config.Environment;
import org.openrewrite.internal.InMemoryLargeSourceSet;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.tree.J;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SonarQubeClient2 {
    private static final String SONARQUBE_URL = "http://localhost:9000";
    private static final String TOKEN = "sqp_36dade72f677202de270d9667fe798907694aeca";

    public static void main(String[] args) {
    try {
        String apiUrl = SONARQUBE_URL + "/api/issues/search";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((TOKEN + ":").getBytes()));

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


                // Load the environment and recipe
                 Environment environment = Environment.builder()
                .scanRuntimeClasspath()
                 .build();
                // Create a recipe to apply
                Recipe recipe = environment.activateRecipes("org.openrewrite.staticanalysis.CommonStaticAnalysis");
                System.out.println("Recipe activated: " + recipe.getName());
/*
                Recipe recipe = new Recipe() {
                    @Override
                    public @NlsRewrite.DisplayName String getDisplayName() {
                        return "";
                    }

                    @Override
                    public @NlsRewrite.Description String getDescription() {
                        return "";
                    }
                };*/
                org.openrewrite.ExecutionContext ctx = new org.openrewrite.InMemoryExecutionContext();

                JsonNode issues = jsonResponse.get("issues");
                for (JsonNode issue : issues) {
                    System.out.println("Issue: " + issue.toString());
                    System.out.println("Processing issue: " + issue.get("message").asText());

                    String filePath = issue.get("component").asText().split(":")[1];


                   /* Set<String> filesToSkip = Set.of(
                        "pom.xml", "DemoApplication.java",
                            "src/main/resources/*");

                        if (filesToSkip.stream().anyMatch(filePath::startsWith)) {
                            System.out.println("Skipping file: " + filePath);
                            continue;
                        }*/
                    if ("src/main/java/com/example/demo/Calculator.java".equalsIgnoreCase(filePath)) {

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
                        System.out.println("Parsed source file: " + sourceFile.print());

                        // Apply the recipe
                        LargeSourceSet sourceSet = new InMemoryLargeSourceSet(List.of(sourceFile));
                        RecipeRun results = recipe.run(sourceSet, ctx);


                        // Print the results
                            /*for (Result result : results.getResults()) {
                            System.out.println("Original file: " + result.getBefore().print());
                            System.out.println("Modified file: " + result.getAfter().print());
                            }*/

                        // Print the results
                        results.getChangeset().getAllResults().forEach(result ->
                            System.out.println("Fixed issue in file: " + result.getAfter().getSourcePath()));


                        // Optionally, write the fixed content back to the file
                        if (!results.getChangeset().getAllResults().isEmpty()) {
                        String fixedContent = results.getChangeset().getAllResults().get(0).getAfter().printAll();
                         Files.write(Paths.get(filePath), fixedContent.getBytes());
                         } else {
                        System.out.println("No changes detected by the recipe.");
                         }


                        /*
                        org.openrewrite.java.tree.J.CompilationUnit sourceFile = org.openrewrite.java.JavaParser.fromJavaVersion()
                            .build()
                            .parse("class Example { void method() {} }")
                            .get(0);
                    org.openrewrite.LargeSourceSet sourceSet = org.openrewrite.LargeSourceSet.from(List.of(sourceFile));
                    RecipeRun results = recipe.run(sourceSet, ctx);*/
                    }
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
}


}

