package io.caden.transformers.amazon.models;

import org.eclipse.rdf4j.model.Statement;

import java.util.ArrayList;
import java.util.Collection;

public class ExecuteStatements {
    private Collection<Statement> amazonReferenceGraphStatements;
    private Collection<Statement> amazonUserGraphStatements;

    public ExecuteStatements() {
        amazonReferenceGraphStatements = new ArrayList<>();
        amazonUserGraphStatements = new ArrayList<>();
    }

    public void addAllToReferenceGraph(Collection<Statement> statements) {
        amazonReferenceGraphStatements.addAll(statements);
    }

    public void addAllToUserGraph(Collection<Statement> statements) {
        amazonUserGraphStatements.addAll(statements);
    }

    public Collection<Statement> getAmazonReferenceGraphStatements() {
        return amazonReferenceGraphStatements;
    }

    public Collection<Statement> getAmazonUserGraphStatements() {
        return amazonUserGraphStatements;
    }
}
