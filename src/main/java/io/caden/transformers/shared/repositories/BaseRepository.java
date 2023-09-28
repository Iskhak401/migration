package io.caden.transformers.shared.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.shared.utils.RepositoryWrapper;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;
import static org.eclipse.rdf4j.model.util.Values.iri;

/**
 * Sets up the basic interactions with the remote database.
 *
 * @param <T>
 */
public abstract class BaseRepository<T> implements Repository<T> {

  private final RepositoryWrapper wrapper;

  protected BaseRepository(RDFConfiguration config) {
    this.wrapper = new RepositoryWrapper(config);
  }

  /**
   * Replaces parts of a SPARQL template with actual values.
   * e.g. SELECT * WHERE { ?id ?p ?o } becomes SELECT * WHERE { caden:value ?p ?o }.
   *
   * @param template - the template to modify.
   * @param params   - the parameters to replace.
   * @return {@link String} - the final query.
   */
  private String buildFinalQuery(final String template, Map<String, Object> params) {
    String query = template;
    for (Entry<String, Object> entry : params.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof IRI && ((IRI) value).isIRI()) {
        query = query.replaceAll("\\?" + key, "<" + value + ">");
      } else if (value instanceof String) {
        query = query.replaceAll("\\?" + key, '"' + value.toString() + '"');
      } else {
        query = query.replaceAll("\\?" + key, value.toString());
      }
    }
    return query;
  }

  /**
   * Saves statements to a specific context
   *
   * @param statements - statements to be saved.
   * @param resources  - the context to save the statements to.
   */
  @Override
  public void save(Collection<Statement> statements, Resource... resources) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      connection.add(statements, resources);
    }
  }

  /**
   * Executes a query from a template and returns the first binding set.
   *
   * @param template - the template to execute.
   * @param params   - replacement parameters for the template.
   * @return {@link BindingSet} - the first binding set of the result.
   */
  @Override
  public BindingSet executeQuery(String template, Map<String, Object> params) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      try (TupleQueryResult tqr = connection.prepareTupleQuery(buildFinalQuery(template, params))
          .evaluate()) {
        if (tqr.hasNext()) {
          return tqr.next();
        }
        return null;
      }
    }
  }

  /**
   * Executes a query from a template and returns all solutions.
   *
   * @param template - the template to execute.
   * @param params   - replacement parameters for the template.
   * @return {@link BindingSet} - the full result.
   */
  @Override
  public List<BindingSet> executeQueryList(String template, Map<String, Object> params) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      try (TupleQueryResult tqr = connection.prepareTupleQuery(buildFinalQuery(template, params))
          .evaluate()) {
        return stream(tqr.spliterator(), true).collect(Collectors.toList());
      }
    }
  }

  /**
   * Executes an update from a template.
   *
   * @param template - the template to execute.
   * @param params   - replacement parameters for the template.
   */
  @Override
  public void executeUpdate(String template, Map<String, Object> params) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      connection.prepareUpdate(buildFinalQuery(template, params)).execute();
    }
  }

  /**
   * Delete all triples starting with a specific subject
   *
   * @param subject - the subject to delete.
   * @return true.
   */
  @Override
  public boolean delete(String subject) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      connection.remove(iri(subject), null, null);
      return true;
    }
  }


  /**
   * Delete all triples with a specific subject-predicate pair.
   *
   * @param subject   - the subject to delete.
   * @param predicate - the predicate to delete.
   * @return true
   */
  @Override
  public boolean delete(String subject, String predicate) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      connection.remove(iri(subject), iri(predicate), null);
      return true;
    }
  }

  /**
   * Delete all triples with a specific subject and a list of predicates, e.g.
   * caden:subject ( caden:predicate1 caden:predicate2 ).
   *
   * @param subject    - the subject to delete data for.
   * @param predicates - the list of predicates to delete data for.
   * @return true.
   */
  @Override
  public boolean delete(String subject, List<String> predicates) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      predicates.forEach(
          predicate -> connection.remove(iri(subject), iri(predicate), null)
      );
      return true;
    }
  }

  /**
   * Delete all triples with a specific subject and a list of predicates in a specific context, e.g.
   * caden:subject ( caden:predicate1 caden:predicate2 ).
   *
   * @param subject    - the subject to delete data for.
   * @param predicates - the list of predicates to delete data for.
   * @param resources  - the contexts where the deletion should take effect.
   * @return true.
   */
  @Override
  public boolean delete(String subject, List<String> predicates, Resource... resources) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      predicates.forEach(
          predicate -> connection.remove(iri(subject), iri(predicate), null, resources)
      );
      return true;
    }
  }

  /**
   * Delete a collection of statements.
   * @param statements list of statements to delete
   * @param resources - the contexts where the deletion take effect, not needed if the statement itself has it using
   * @see org.eclipse.rdf4j.model.impl.ContextStatement
   * @throws Exception
   */
  @Override
  public void delete(Collection<Statement> statements, Resource... resources) throws Exception {
    try (RepositoryConnection connection = wrapper.getConnection()) {
      connection.remove(statements, resources);
    }
  }

  /**
   * Executes a query (WHICH SHOULD CONTAIN A `count` BINDING!) from a template and returns
   * the count.
   *
   * @param template - the template to execute.
   * @param params   - replacement parameters for the template.
   * @return {@link Long} - the count value, or null.
   */
  @Override
  public Long count(String template, Map<String, Object> params) throws Exception {
    BindingSet value = this.executeQuery(template, params);
    Value count = value.getValue("count");

    try {
      if (count != null) {
        return Long.valueOf(count.stringValue());
      }
    } catch (NumberFormatException nfe) {
      return null;
    }
    return null;
  }

  /**
   * Executes a query (WHICH SHOULD CONTAIN A `sum` BINDING!) from a template and returns
   * the sum.
   *
   * @param template - the template to execute.
   * @param params   - replacement parameters for the template.
   * @return {@link Long} - the sum value, or null.
   */
  @Override
  public Long sum(String template, Map<String, Object> params) throws Exception {
    BindingSet value = this.executeQuery(template, params);
    Value sum = value.getValue("sum");

    try {
      if (sum != null) {
        return Long.valueOf(sum.stringValue());
      }
    } catch (NumberFormatException nfe) {
      return null;
    }
    return null;
  }
}
