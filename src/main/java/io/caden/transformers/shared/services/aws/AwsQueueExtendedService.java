package io.caden.transformers.shared.services.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

@Service
public class AwsQueueExtendedService {
  // MEGRAPH
  public static String megraphTransformationsQueueUrl;
  // SPOTIFY
  public static String spotifyTransformationsQueueUrl;
  // UBER
  public static String uberTransformationsQueueUrl;
  // AIRBNB
  public static String airbnbTransformationsQueueUrl;
  // AHK
  public static String ahkTransformationQueueUrl;
  private final AmazonSQS client;

  public AwsQueueExtendedService(
    @Value("${aws.access.key.id}") final String accessKey,
    @Value("${aws.secret.access.key}") final String secretAccessKey,
    @Value("${aws.sqs.region}") final String queueRegion,
    @Value("${aws.sqs.megraph.transformations}") final String megraphTransformationsQueueUrl,
    @Value("${aws.sqs.spotify.transformations}") final String spotifyTransformationsQueueUrl,
    @Value("${aws.sqs.uber.transformations}") final String uberTransformationsQueueUrl,
    @Value("${aws.sqs.airbnb.transformations}") final String airbnbTransformationsQueueUrl,
    @Value("${aws.sqs.ahk.transformations}") final String ahkTransformationQueueUrl
  ) {
    this.client = AmazonSQSClientBuilder.standard().withRegion(queueRegion)
      .withCredentials(
        new AWSStaticCredentialsProvider(
          new BasicAWSCredentials(accessKey, secretAccessKey)
        )
      ).build();

    AwsQueueExtendedService.megraphTransformationsQueueUrl = megraphTransformationsQueueUrl;
    AwsQueueExtendedService.spotifyTransformationsQueueUrl = spotifyTransformationsQueueUrl;
    AwsQueueExtendedService.uberTransformationsQueueUrl = uberTransformationsQueueUrl;
    AwsQueueExtendedService.airbnbTransformationsQueueUrl = airbnbTransformationsQueueUrl;
    AwsQueueExtendedService.ahkTransformationQueueUrl = ahkTransformationQueueUrl;
    
  }

  public ReceiveMessageResult receiveMessage(final String queueUrl) {
    return this.receiveMessage(queueUrl, 10);
  }

  public ReceiveMessageResult receiveMessage(final String queueUrl, final int maxNumberOfMessages) {
    ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest();

    receiveMessageRequest.setQueueUrl(queueUrl);
    receiveMessageRequest.setMaxNumberOfMessages(maxNumberOfMessages);

    return this.client.receiveMessage(receiveMessageRequest);
  }

  public DeleteMessageResult deleteMessage(final String queueUrl, final String receiptHandle) {
    return this.client.deleteMessage(queueUrl, receiptHandle);
  }
}
