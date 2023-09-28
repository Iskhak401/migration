package io.caden.transformers.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;

import java.util.Collections;

@Configuration
@EnableSqs
public class AwsSqsConfig {
    @Value("${aws.access.key.id}")
    private String accessKey;
    @Value("${aws.secret.access.key}")
    private String secretAccessKey;
    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretAccessKey)))
                .build();
    }
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(
            AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(
            final ObjectMapper mapper, final AmazonSQSAsync amazonSQSAsync){

        final QueueMessageHandlerFactory queueHandlerFactory =
                new QueueMessageHandlerFactory();
        queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
        queueHandlerFactory.setArgumentResolvers(Collections.singletonList(
                new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))
        ));
        return queueHandlerFactory;
    }

    private MessageConverter jackson2MessageConverter(final ObjectMapper mapper){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(mapper);
        return converter;
    }
}

