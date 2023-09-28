package io.caden.transformers.shared.repositories;

import com.google.common.collect.Lists;
import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.entities.BaseBatchObject;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseBatchGraphRepositoryTest {
    private final SimpleValueFactory svf = SimpleValueFactory.getInstance();
    @Mock
    private RDFConfiguration rdfConfiguration;
    @Mock
    private UserGraphRepository userGraphRepository;
    @Mock
    private TestBatchGraphRepository testBatchGraphRepository;
    @InjectMocks
    private TestBaseBatchGraphRepository testBaseBatchGraphRepository;

    @Test
    void queue() throws Exception {
        List<String> strings = generateData();
        testBaseBatchGraphRepository.queue(strings, generateTransformationMessage());
        verify(testBatchGraphRepository, atMostOnce()).saveAll(anyList());
    }

    @Test
    void flush() throws Exception {
        Page mockPage = mock(Page.class);
        when(mockPage.hasContent()).thenReturn(true);
        when(mockPage.getContent()).thenReturn(generateTestContent());
        when(testBatchGraphRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        testBaseBatchGraphRepository.flush(100);

        verify(userGraphRepository, atMostOnce()).save(any());
        verify(testBatchGraphRepository, atMostOnce()).deleteAllByIdInBatch(anyList());
    }

    private List<String> generateData() {
        List<String> data = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            data.add(UUID.randomUUID().toString());
        }
        return data;
    }

    private List<TestBaseBatchObject> generateTestContent() {
        List<TestBaseBatchObject> data = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            TestBaseBatchObject testBaseBatchObject = new TestBaseBatchObject();
            testBaseBatchObject.setCognitoId(UUID.randomUUID());
            testBaseBatchObject.setBatchObject(UUID.randomUUID().toString());
            testBaseBatchObject.setCreatedAt(new Date());
            data.add(testBaseBatchObject);
        }
        return data;
    }

    private TransformationMessageBodyDto generateTransformationMessage() {
        TransformationMessageBodyDto transformationMessageBodyDto = new TransformationMessageBodyDto();
        transformationMessageBodyDto.setPath(UUID.randomUUID()+"/whateva/"+UUID.randomUUID());
        transformationMessageBodyDto.setCadenAlias(UUID.randomUUID());
        return transformationMessageBodyDto;
    }
    interface TestBatchGraphRepository extends BaseBatchRepository<String, TestBaseBatchObject>{
    }

    static class TestBaseBatchObject extends BaseBatchObject<String> {
    }

    static class TestBaseBatchGraphRepository extends BaseBatchGraphRepository<String, TestBaseBatchObject>{

        protected TestBaseBatchGraphRepository(RDFConfiguration config, UserGraphRepository userGraphRepository, BaseBatchRepository<String, TestBaseBatchObject> baseBatchRepository) {
            super(config, userGraphRepository, baseBatchRepository);
        }

        @Override
        public List<TestBaseBatchObject> transform(List<String> batchObjects, UUID cognitoId, UUID extractionId, UUID cadenAlias) {

            return batchObjects.stream().map(a -> {
                TestBaseBatchObject testBaseBatchObject = new TestBaseBatchObject();
                testBaseBatchObject.setCognitoId(cognitoId);
                testBaseBatchObject.setBatchObject(a);
                testBaseBatchObject.setCreatedAt(new Date());
                return testBaseBatchObject;
            }).collect(Collectors.toList());
        }

        @Override
        public List<Statement> buildStatements(String batchObject) {
            Statement mock = mock(Statement.class);
            return Lists.newArrayList(mock);
        }
    }

}