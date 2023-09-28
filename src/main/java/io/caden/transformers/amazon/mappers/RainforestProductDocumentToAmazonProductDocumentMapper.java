package io.caden.transformers.amazon.mappers;

import org.springframework.stereotype.Component;

import io.caden.transformers.amazon.dtos.rainforest.RainforestProductDocumentDto;
import io.caden.transformers.amazon.entities.AmazonProductDocument;

import io.caden.transformers.shared.mappers.Mapper;

@Component
public class RainforestProductDocumentToAmazonProductDocumentMapper extends Mapper<RainforestProductDocumentDto, AmazonProductDocument> {

  @Override
  public AmazonProductDocument map(RainforestProductDocumentDto value) {
    if (value == null) {
      return null;
    }

    AmazonProductDocument amazonProductDocument = new AmazonProductDocument();
    amazonProductDocument.setName(value.getName());
    amazonProductDocument.setLink(value.getLink());

    return amazonProductDocument;
  }

  @Override
  public RainforestProductDocumentDto reverseMap(AmazonProductDocument value) {
    throw new UnsupportedOperationException("Not implemented exception");
  }
}
