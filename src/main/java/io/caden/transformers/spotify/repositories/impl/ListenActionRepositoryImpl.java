package io.caden.transformers.spotify.repositories.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.repositories.BaseRepository;
import io.caden.transformers.shared.utils.RDFNamingConventionUtil;
import io.caden.transformers.shared.utils.SchemaBaseConstants;
import io.caden.transformers.spotify.entities.ListenAction;
import io.caden.transformers.spotify.mappers.ListenActionToStatements;
import io.caden.transformers.spotify.repositories.ListenActionRepository;

@Repository("listenActionRepo")
public class ListenActionRepositoryImpl extends BaseRepository<ListenAction> implements ListenActionRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(ListenActionRepositoryImpl.class);

  private final RDFConfiguration config;
  private final ListenActionToStatements listenActionToStatements;

  public ListenActionRepositoryImpl(
    @Autowired final RDFConfiguration config,
    @Autowired final ListenActionToStatements listenActionToStatements
  ) {
    super(config);
    this.config = config;
    this.listenActionToStatements = listenActionToStatements;
  }

  @Override
  public ListenAction find(final String filter) throws Exception {
    return null;
  }

  @Override
  public void update(final ListenAction listenAction, final Collection<Statement> statements) throws Exception {
    List<String> predicateWithNamespaces = Arrays.asList(
      config.getSchemaBase(SchemaBaseConstants.Property.START_TIME)
    );

    String instance = ListenActionToStatements.getInstance(listenAction);
    IRI userInstance = config.getCadenBaseDataIRI(RDFNamingConventionUtil.generateUserGraphName(listenAction.getCadenAlias()));

    super.delete(config.getCadenBaseDataBase(instance), predicateWithNamespaces, userInstance);
    super.save(statements, userInstance);
  }

  @Override
  public void update(final ListenAction listenAction) throws Exception {
    if (listenAction.getCreatedAt() == null) {
      listenAction.setCreatedAt(new Date());
    }

    if (listenAction.getUuid() == null) {
      listenAction.setUuid(UUID.randomUUID().toString());
    }

    listenAction.setUpdatedAt(new Date());
    this.update(listenAction, this.listenActionToStatements.map(listenAction));
  }
}
