package io.caden.transformers.shared.utils;

import io.caden.transformers.config.RDFConfiguration;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.manager.RemoteRepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class which sets up the RDF4J Repository configuration for setting up connections.
 *
 * @author Radostin Nanov
 */
public class RepositoryWrapper {

  private final RDFConfiguration config;
  private final RemoteRepositoryManager manager;

  public RepositoryWrapper(@Autowired RDFConfiguration config) {
    this.config = config;
    manager = new RemoteRepositoryManager(config.getUrl());
    manager.setUsernameAndPassword(config.getUserName(), config.getPassword());
    manager.init();
  }

  public RepositoryConnection getConnection() {
    Repository repo = manager.getRepository(config.getDatabase());
    return repo.getConnection();
  }
}
