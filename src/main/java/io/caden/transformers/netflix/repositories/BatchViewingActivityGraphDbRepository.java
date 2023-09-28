package io.caden.transformers.netflix.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.netflix.entities.BatchNetflixViewingActivity;
import io.caden.transformers.netflix.entities.NetflixViewingActivity;
import io.caden.transformers.netflix.mappers.NetflixViewingActivityToStatements;
import io.caden.transformers.shared.repositories.BaseBatchGraphRepository;
import io.caden.transformers.shared.repositories.BaseBatchRepository;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.rdf4j.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Repository
@Slf4j
public class BatchViewingActivityGraphDbRepository extends BaseBatchGraphRepository<NetflixViewingActivity, BatchNetflixViewingActivity> {

    private final NetflixViewingActivityToStatements netflixViewingActivityToStatements;
    private final NetflixViewingActivityRepository netflixViewingActivityRepo;

    protected BatchViewingActivityGraphDbRepository(@Autowired RDFConfiguration config,
                                                    @Autowired UserGraphRepository userGraphRepository,
                                                    @Autowired BaseBatchRepository<NetflixViewingActivity, BatchNetflixViewingActivity> baseBatchRepository,
                                                    @Autowired NetflixViewingActivityToStatements netflixViewingActivityToStatements,
                                                    @Autowired NetflixViewingActivityRepository netflixViewingActivityRepo){
        super(config, userGraphRepository, baseBatchRepository);
        this.netflixViewingActivityToStatements = netflixViewingActivityToStatements;
        this.netflixViewingActivityRepo = netflixViewingActivityRepo;
    }

    @Override
    public List<BatchNetflixViewingActivity> transform(List<NetflixViewingActivity> batchObjects, UUID cognitoId, UUID extractionId, UUID cadenAlias) {
        return batchObjects.stream().map(netflixViewingActivity -> {
            BatchNetflixViewingActivity batchNetflixViewingActivity  = new BatchNetflixViewingActivity();
            batchNetflixViewingActivity.setCognitoId(cognitoId);
            batchNetflixViewingActivity.setExtractionId(extractionId);
            batchNetflixViewingActivity.setBatchObject(netflixViewingActivity);
            return batchNetflixViewingActivity;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Statement> buildStatements(NetflixViewingActivity batchObject) {
        List<Statement> statements = new LinkedList<>();
        Date startTime = batchObject.getStartTime();
        Long netflixId = Long.valueOf(batchObject.getNetflixId());
        String cadenAlias = String.valueOf(batchObject.getCadenAlias());
        long checkExistQueryStartTime = System.currentTimeMillis();
        Boolean watchActionExists = netflixViewingActivityRepo.existsByCadenAliasAndIdentifierAndStartTime(cadenAlias, netflixId, startTime);
        long checkExistQueryElapsedTime = System.currentTimeMillis() - checkExistQueryStartTime;
        log.info("checking for ViewingActivity took {}ms", checkExistQueryElapsedTime, kv("elapsed_time", checkExistQueryElapsedTime));
        if(BooleanUtils.isTrue(watchActionExists)) {
            return statements;
        }

        statements.addAll(toUserGraphStatements(toWatchActionStatements(batchObject), batchObject.getCadenAlias()));

        if (Boolean.TRUE.equals(batchObject.isSerie())) {
            statements.addAll(toReferenceGraphStatements(toTVEpisodeStatements(batchObject)));
            statements.addAll(toReferenceGraphStatements(toTVSeriesStatements(batchObject)));
        }else {
            statements.addAll(toReferenceGraphStatements(toMovieStatements(batchObject)));
        }

        return statements;
    }

    private Collection<Statement> toWatchActionStatements(NetflixViewingActivity netflixViewingActivity) {
        return this.netflixViewingActivityToStatements.mapToWatchAction(netflixViewingActivity);
    }

    private Collection<Statement> toTVEpisodeStatements(NetflixViewingActivity netflixViewingActivity) {
        return netflixViewingActivityToStatements.mapToTVEpisode(netflixViewingActivity);
    }

    private Collection<Statement> toTVSeriesStatements(NetflixViewingActivity netflixViewingActivity) {
        return netflixViewingActivityToStatements.mapToTVSeries(netflixViewingActivity);
    }

    private Collection<Statement> toMovieStatements(NetflixViewingActivity netflixViewingActivity) {
        return netflixViewingActivityToStatements.mapToMovie(netflixViewingActivity);
    }
}
