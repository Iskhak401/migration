package io.caden.transformers.shared.utils;

import org.eclipse.rdf4j.model.Statement;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

public class ExecuteStatements {
    private Collection<Statement> referenceGraphStatements;
    private Collection<Statement> userGraphStatements;

    public ExecuteStatements() {
        referenceGraphStatements = new ArrayList<>();
        userGraphStatements = new ArrayList<>();
    }

    public void addAllToReferenceGraph(Collection<Statement> statements) {
        if(!CollectionUtils.isEmpty(statements)) {
            referenceGraphStatements.addAll(statements);
        }
    }

    public void addAllToUserGraph(Collection<Statement> statements) {
        if(!CollectionUtils.isEmpty(statements)) {
            userGraphStatements.addAll(statements);
        }
    }

    public Collection<Statement> getReferenceGraphStatements() {
        return referenceGraphStatements;
    }

    public Collection<Statement> getUserGraphStatements() {
        return userGraphStatements;
    }
}
