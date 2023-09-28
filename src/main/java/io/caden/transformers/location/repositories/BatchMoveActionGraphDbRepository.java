package io.caden.transformers.location.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.location.entities.BatchMoveAction;
import io.caden.transformers.location.entities.MoveAction;
import io.caden.transformers.location.mappers.MoveActionToStatements;
import io.caden.transformers.shared.repositories.BaseBatchGraphRepository;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BatchMoveActionGraphDbRepository extends BaseBatchGraphRepository<MoveAction, BatchMoveAction> {

    private final MoveActionToStatements moveActionToStatements;

    protected BatchMoveActionGraphDbRepository(@Autowired RDFConfiguration config,
                                               @Autowired UserGraphRepository userGraphRepository,
                                               @Autowired BaseBatchRepository<MoveAction, BatchMoveAction> batchMoveActionRepository,
                                               @Autowired MoveActionToStatements moveActionToStatements) {
        super(config, userGraphRepository, batchMoveActionRepository);
        this.moveActionToStatements = moveActionToStatements;
    }

    @Override
    public List<BatchMoveAction> transform(List<MoveAction> batchObjects, UUID cognitoId, UUID extractionId, UUID cadenAlias) {
        return batchObjects.stream().map(moveAction -> {
            BatchMoveAction batchMoveAction = new BatchMoveAction();
            batchMoveAction.setCognitoId(cognitoId);
            batchMoveAction.setBatchObject(moveAction);
            batchMoveAction.setExtractionId(extractionId);
            return batchMoveAction;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Statement> buildStatements(MoveAction batchObject) {
        return toUserGraphStatements(moveActionToStatements.map(batchObject), batchObject.getCadenAlias().toString());
    }
}
