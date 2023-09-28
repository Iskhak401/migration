package io.caden.transformers.shared.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserGraphRepository extends BaseRepository<Collection<Statement>> {
    private final RDFConfiguration config;

    public UserGraphRepository(@Autowired RDFConfiguration config) {
        super(config);
        this.config = config;
    }

    public void saveAll(Collection<Statement> statements, String cadenAlias) throws Exception {
        IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias));
        save(statements, userInstance);
    }

    @Override
    public Collection<Statement> find(String filter) throws Exception {
        return null;
    }
}
