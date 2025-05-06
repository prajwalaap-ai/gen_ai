package com.example.demo.codequality;

import org.openrewrite.*;
import org.openrewrite.internal.InMemoryLargeSourceSet;
import org.openrewrite.java.JavaParser;
import org.openrewrite.java.RemoveUnusedImports;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SonarAutoFixerWithOpenRewrite {

    public static void main(String[] args) {
        Path projectRoot = Paths.get("src/main/java");

        JavaParser parser = JavaParser.fromJavaVersion()
                .build();

        List<SourceFile> parsed = (List<SourceFile>) parser.parse(
                projectRoot.resolve("src/main/java/com/example/demo/Calculator.java").toString()
        );

        List<Recipe> recipes = List.of(
                new RemoveUnusedImports()

        // You can add more recipes here
        );

        InMemoryExecutionContext ctx = new InMemoryExecutionContext(Throwable::printStackTrace);
        LargeSourceSet sourceSet = new InMemoryLargeSourceSet(parsed);
        for (Recipe recipe : recipes) {
            List<Result> results = recipe.run(sourceSet, ctx).getChangeset().getAllResults();
            for (Result result : results) {
                System.out.println("Before:\n" + result.getBefore());
                System.out.println("After:\n" + result.getAfter());
                // Optionally overwrite the file with result.getAfter().printAll()
            }
        }
    }
}
