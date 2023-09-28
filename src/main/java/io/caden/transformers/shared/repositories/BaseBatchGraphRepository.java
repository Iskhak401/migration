package io.caden.transformers.shared.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.entities.BaseBatchObject;
import io.caden.transformers.shared.utils.ExecuteStatements;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public abstract class BaseBatchGraphRepository<A, T extends BaseBatchObject<A>> extends BaseRepository<ExecuteStatements>{
    private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

    private final RDFConfiguration config;
    private final UserGraphRepository userGraphRepository;
    private final BaseBatchRepository<A, T> baseBatchRepository;

    protected BaseBatchGraphRepository(RDFConfiguration config,
                                    UserGraphRepository userGraphRepository,
                                    BaseBatchRepository<A, T> baseBatchRepository) {
        super(config);
        this.config = config;
        this.userGraphRepository = userGraphRepository;
        this.baseBatchRepository = baseBatchRepository;
    }

    @Override
    public ExecuteStatements find(String filter) throws Exception {
        return null;
    }

    @Transactional
    public void queue(List<A> batch, TransformationMessageBodyDto transformationMessageBodyDto) throws Exception{
        String path = transformationMessageBodyDto.getPath();
        String[] pathParts = path.split("/");
        UUID cognitoId = UUID.fromString(pathParts[0]);
        UUID extractionId = UUID.fromString(pathParts[2]);
        List<T> batches = transform(batch, cognitoId, extractionId, transformationMessageBodyDto.getCadenAlias());
        baseBatchRepository.saveAll(batches);
    }

    public abstract List<T> transform(List<A> batchObjects, UUID cognitoId, UUID extractionId, UUID cadenAlias);

    public List<A> cleanUp(List<A> batchObjects) {
        return batchObjects;
    }

    public void flush(int batchSize) throws Exception {
        Pageable pageable = Pageable.ofSize(batchSize);
        Page<T> batch = baseBatchRepository.findAll(pageable);

        if(batch.hasContent()) {
            log.info("starting flushing of records at {}", new DateTime());
            long startTime = System.currentTimeMillis();
            List<T> records = batch.getContent();
            List<Statement> statements = new LinkedList<>();
            List<A> batchObjects = records.stream().map(T::getBatchObject).collect(Collectors.toList());
            batchObjects = cleanUp(batchObjects);

            for (A batchObject:
                    batchObjects) {
                List<Statement> statementsPerBatchObject = buildStatements(batchObject);
                statements.addAll(statementsPerBatchObject);
            }

            List<Long> ids = records.stream().map(T::getId).collect(Collectors.toList());

            userGraphRepository.save(statements);
            baseBatchRepository.deleteAllByIdInBatch(ids);
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("flushed {} statements in {}ms", statements.size(), elapsedTime,
                    kv("batch_size", statements.size()),
                    kv("elapsed_Time", elapsedTime));
        }
    }

    public abstract List<Statement> buildStatements(A batchObject);

    public List<Statement> toUserGraphStatements(Collection<Statement> statements, String cadenAlias) {
        IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(cadenAlias));
        return statements.stream()
                .map(st ->
                        svf.createStatement(st.getSubject(),
                                st.getPredicate(),
                                st.getObject(),
                                userInstance))
                .collect(Collectors.toList());
    }

    public List<Statement> toReferenceGraphStatements(Collection<Statement> statements) {
        IRI referenceInstance = config.getCadenReferenceDataIRI("");
        return statements.stream()
                .map(st ->
                        svf.createStatement(st.getSubject(),
                                st.getPredicate(),
                                st.getObject(),
                                referenceInstance))
                .collect(Collectors.toList());

    }
}
