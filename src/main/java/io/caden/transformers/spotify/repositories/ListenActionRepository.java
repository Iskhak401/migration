package io.caden.transformers.spotify.repositories;

import java.util.Collection;

import io.caden.transformers.shared.repositories.Repository;
import io.caden.transformers.spotify.entities.ListenAction;
import org.eclipse.rdf4j.model.Statement;

public interface ListenActionRepository extends Repository<ListenAction> {
  void update(ListenAction listenAction, Collection<Statement> statements) throws Exception;
  void update(ListenAction listenAction) throws Exception;
}
