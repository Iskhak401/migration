package io.caden.transformers.shared.repositories;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Repository<T> {

  void save(Collection<Statement> statements, Resource... resources) throws Exception;

  T find(String filter) throws Exception;

  BindingSet executeQuery(String query, Map<String, Object> params) throws Exception;

  List<BindingSet> executeQueryList(String query, Map<String, Object> params) throws Exception;

  void executeUpdate(String query, Map<String, Object> params) throws Exception;

  boolean delete(String subject) throws Exception;

  boolean delete(String subject, String predicate) throws Exception;

  boolean delete(String subject, List<String> predicates) throws Exception;

  boolean delete(String subject, List<String> predicates, Resource... resources) throws Exception;

  void delete(Collection<Statement> statements, Resource... resources) throws Exception;

  Long count(String query, Map<String, Object> params) throws Exception;

  Long sum(String query, Map<String, Object> params) throws Exception;
}
