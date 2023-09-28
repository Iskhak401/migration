package io.caden.transformers.shared.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.utils.ExecuteStatements;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Repository
@Slf4j
public class BatchSaveRepository extends BaseRepository<ExecuteStatements> {
    private final RDFConfiguration config;
    @Autowired
    private ReferenceGraphRepository referenceGraphRepository;
    @Autowired
    private UserGraphRepository userGraphRepository;

    public BatchSaveRepository(@Autowired RDFConfiguration config) {
        super(config);
        this.config = config;
    }

    public void saveAll(ExecuteStatements statements, String cadenAlias) throws Exception {
        Collection<Statement> referenceGraphStatements = statements.getReferenceGraphStatements();
        Collection<Statement> userGraphStatements = statements.getUserGraphStatements();
        int statementsSize = 0;

        long beforeBatchSave = System.currentTimeMillis();

        if(!CollectionUtils.isEmpty(userGraphStatements)) {
            userGraphRepository.saveAll(userGraphStatements, cadenAlias);
            statementsSize+=userGraphStatements.size();
        }

        if(!CollectionUtils.isEmpty(referenceGraphStatements)) {
            referenceGraphRepository.saveAll(referenceGraphStatements);
            statementsSize+=referenceGraphStatements.size();
        }

        long totalBatchSaveTime = System.currentTimeMillis() - beforeBatchSave;

        log.info("batchSave completed size={}, took={}ms", statementsSize, totalBatchSaveTime,
                kv("size", statementsSize),
                kv("time_ms", totalBatchSaveTime));

    }


    @Override
    public ExecuteStatements find(String filter) throws Exception {
        return null;
    }
}
