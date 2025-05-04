package com.example.demo.codequality;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.openrewrite.*;
import org.openrewrite.config.Environment;
import org.openrewrite.config.RecipeDescriptor;
import org.openrewrite.internal.InMemoryLargeSourceSet;
import org.openrewrite.java.tree.J;
import org.openrewrite.staticanalysis.RemoveUnusedLocalVariables;
import org.openrewrite.tools.checkstyle.JavaParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class SonarQubeFixer {



    public static void main(String[] args) throws IOException {


        JsonNode issues = fetchSonarIssues();
        Set<String> filesNamesWithIssues = new HashSet<>();

        for (JsonNode issue : issues) {
            File sourceFile = new File(issue.get("component").asText().split(":")[1]);
            if (!String.valueOf(sourceFile).endsWith("pom.xml")) {
                filesNamesWithIssues.add(sourceFile.getPath());
            }

        }
        List<SourceFile> sourceFiles = getSourceFiles(filesNamesWithIssues);
        Recipe recipe = loadTheRecipe();
        List<Result> results = getResults(sourceFiles, recipe);

        for (Result result : results) {
            SourceFile before = result.getBefore();
            SourceFile after = result.getAfter();


            List<RecipeDescriptor> descriptors = result.getRecipeDescriptorsThatMadeChanges();
            for (RecipeDescriptor descriptor : descriptors) {
                System.out.println("🧪 Applied Recipe: " + descriptor.getName() +
                        (descriptor.getDisplayName() != null ? " - " + descriptor.getDisplayName() : ""));
            }

            Path sourceRoot = Paths.get("src/main/java");
            if (after != null && !after.printAll().equals(before.printAll())) {
                Path filePath = sourceRoot.resolve(after.getSourcePath());
                System.out.println("Fixing: " + filePath);
                Files.writeString(filePath, after.printAll());
            }
        }

    }




    private static JsonNode fetchSonarIssues() throws IOException {
            String SONARQUBE_URL = "http://localhost:9000";
            String TOKEN = "sqp_36dade72f677202de270d9667fe798907694aeca";
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
                    return jsonResponse.get("issues");
                }

            }
            return null;
        }

    public static List<SourceFile> getSourceFiles(Set<String> filesNamesWithIssues) throws IOException {
        org.openrewrite.java.JavaParser parser = org.openrewrite.java.JavaParser.fromJavaVersion().build();
        List<SourceFile> sourceFiles = new ArrayList<>();

        for (String fileName : filesNamesWithIssues) {
            File file = new File(fileName);
            if (file.exists() && file.isFile()) {
                sourceFiles.addAll(parser.parse(new String(Files.readAllBytes(Paths.get(fileName)))).toList());
            }
        }

        return sourceFiles;
    }

    private static Recipe loadTheRecipe() {
        // Load the environment and recipe
        Environment environment = Environment.builder()
                .scanRuntimeClasspath()
                .build();
        // Create a recipe to apply
        Recipe recipe = environment.activateRecipes("org.openrewrite.staticanalysis.CommonStaticAnalysis");
        System.out.println("Recipe activated: " + recipe.getName());
        return recipe;
    }

    private static @NotNull List<Result> getResults(List<SourceFile> sourceFiles, Recipe recipe) {
        ExecutionContext ctx = new InMemoryExecutionContext();
        LargeSourceSet sourceSet = new InMemoryLargeSourceSet(sourceFiles);
        List<Result> results = recipe.run(sourceSet, ctx).getChangeset().getAllResults();
        return results;
    }

    private static void writeFixedCode(File file, String newCode) throws IOException {
        Files.write(file.toPath(), newCode.getBytes(StandardCharsets.UTF_8));
    }

    List<Recipe> mapSonarToRewrite(JsonNode sonarIssues) {
        Set<String> rules = new HashSet<>();
        for (JsonNode issue : sonarIssues) {
            rules.add(String.valueOf(issue.get("rule")));
        }


        List<Recipe> recipes = new ArrayList<>();

        if (rules.contains("java:S1481")) {
//            recipes.add(new RemoveUnusedLocalVariables());
        }
        // ... add more mappings here

        return recipes;
    }


}

