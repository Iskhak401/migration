package io.caden.transformers.mx.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.mx.entities.BatchMxTransaction;
import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import io.caden.transformers.shared.utils.ExecuteStatements;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Repository
@Slf4j
public class BatchStagingSaveRepository extends BaseRepository<ExecuteStatements> {

    private SimpleValueFactory svf = SimpleValueFactory.getInstance();

    private final RDFConfiguration config;
    private final BatchMxTransactionsRepository batchStageRepository;
    private final UserGraphRepository userGraphRepository;
    private final MxTransactionRepository mxTransactionRepo;

    public BatchStagingSaveRepository(RDFConfiguration config,
                                      BatchMxTransactionsRepository batchStageRepository,
                                      UserGraphRepository userGraphRepository,
                                      MxTransactionRepository mxTransactionRepo) {
        super(config);
        this.config = config;
        this.batchStageRepository = batchStageRepository;
        this.userGraphRepository = userGraphRepository;
        this.mxTransactionRepo = mxTransactionRepo;
    }

    @Override
    public ExecuteStatements find(String filter) throws Exception {
        return null;
    }

    @Transactional
    public void queue(List<MxTransaction> mxTransactions, UUID cadenAlias, UUID cognitoId, UUID extractionId) throws Exception {
        List<BatchMxTransaction> batchMxStatements = mxTransactions.stream().map(b -> {
            BatchMxTransaction batchMxStatement = new BatchMxTransaction();
            batchMxStatement.setCadenAlias(cadenAlias);
            batchMxStatement.setTransaction(b);
            batchMxStatement.setCognitoId(cognitoId);
            batchMxStatement.setExtractionId(extractionId);
            return batchMxStatement;
        }).collect(Collectors.toList());

        batchStageRepository.saveAll(batchMxStatements);
    }

    @Transactional
    public void flush(int batchSize) throws Exception {
        Pageable pageable = Pageable.ofSize(batchSize);
        Page<BatchMxTransaction> records = batchStageRepository.findAll(pageable);

        if (records.hasContent()) {
            log.info("starting flushing of records at {}", new DateTime());
            long startTime = System.currentTimeMillis();
            List<BatchMxTransaction> batchMxStatements = records.getContent();
            List<Statement> statementList = new LinkedList<>();
            List<MxTransaction> mxTransactions = filterDuplicated(batchMxStatements);
            List<Long> ids = batchMxStatements.stream().map(BatchMxTransaction::getId).collect(Collectors.toList());

            var startTransformation = System.currentTimeMillis();

            for (MxTransaction mxTransaction :
                    mxTransactions) {
                var startExists  = System.currentTimeMillis();
                Boolean exists = mxTransactionRepo.exists(mxTransaction.getCadenAlias(), mxTransaction.getMxGuid());
                log.info("checking if the record exists took {} milliseconds. transactionGuid = {}", System.currentTimeMillis() - startExists, mxTransaction.getMxGuid());
                if (BooleanUtils.isFalse(exists)) {
                    ExecuteStatements statementsPerTransaction = mxTransactionRepo.buildStatements(mxTransaction);
                    statementList.addAll(toUserGraphStatements(statementsPerTransaction.getUserGraphStatements(), mxTransaction.getCadenAlias()));
                    statementList.addAll(toReferenceGraphStatements(statementsPerTransaction.getReferenceGraphStatements()));
                }else {
                    log.info("record exists. Skipping... transactionGuid = {}", mxTransaction.getMxGuid());
                }
            }

            log.info("statements built in {}ms", System.currentTimeMillis() - startTransformation);
            log.info("saving to the graph at={} ", new DateTime());
            userGraphRepository.save(statementList);
            log.info("deleting batches at={} ", new DateTime());
            batchStageRepository.deleteAllByIdInBatch(ids);
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("flushed {} statements in {}ms", statementList.size(), elapsedTime,
                    kv("batch_size", statementList.size()),
                    kv("elapsed_Time", elapsedTime));
        }
    }

    private List<MxTransaction> filterDuplicated(List<BatchMxTransaction> batchMxStatements) {
        List<MxTransaction> allMxTransactions = batchMxStatements.stream().map(BatchMxTransaction::getTransaction).collect(Collectors.toList());
        Map<String, MxTransaction> uniqueMxTransactionsMap = allMxTransactions.stream()
                .collect(Collectors.toMap(MxTransaction::getMxGuid, Function.identity(), (og, rep) -> og));
        return new LinkedList<>(uniqueMxTransactionsMap.values());
    }

    List<Statement> toUserGraphStatements(Collection<Statement> statements, String cadenAlias) {
        IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias));
        return statements.stream()
                .map(st ->
                        svf.createStatement(st.getSubject(),
                                st.getPredicate(),
                                st.getObject(),
                                userInstance))
                .collect(Collectors.toList());
    }

    List<Statement> toReferenceGraphStatements(Collection<Statement> statements) {
        IRI referenceInstance = config.getCadenReferenceDataIRI("");
        return statements.stream()
                .map(st ->
                        svf.createStatement(st.getSubject(),
                                st.getPredicate(),
                                st.getObject(),
                                referenceInstance))
                .collect(Collectors.toList());

    }

    protected void setSvf(SimpleValueFactory svf) {
        this.svf = svf;
    }
}
