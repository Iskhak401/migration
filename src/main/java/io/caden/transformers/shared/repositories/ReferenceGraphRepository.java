package io.caden.transformers.shared.repositories;

import io.caden.transformers.config.RDFConfiguration;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ReferenceGraphRepository extends BaseRepository<Collection<Statement>> {
    private final RDFConfiguration config;

    public ReferenceGraphRepository(@Autowired RDFConfiguration config) {
        super(config);
        this.config = config;
    }

    public void saveAll(Collection<Statement> statements) throws Exception {
        save(statements, config.getCadenReferenceDataIRI(""));
    }

    @Override
    public Collection<Statement> find(String filter) throws Exception {
        return null;
    }
}
