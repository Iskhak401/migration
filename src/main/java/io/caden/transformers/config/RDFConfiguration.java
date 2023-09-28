package io.caden.transformers.config;

import static org.eclipse.rdf4j.model.util.Values.iri;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.util.Values;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RDFConfiguration {

  @Value("${rdf4j.url}")
  private String url;

  @Value("${rdf4j.username}")
  private String userName;

  @Value("${rdf4j.password}")
  private String password;

  @Value("${rdf4j.repository.name}")
  private String database;

  @Value("${rdf4j.caden.base.namespace}")
  private String cadenBaseNamespace;

  @Value("${rdf4j.caden.base.data.namespace}")
  private String cadenBaseDataNamespace;

  @Value("${rdf4j.caden.reference.namespace}")
  private String cadenReferenceNamespace;

  @Value("${rdf4j.caden.reference.data.namespace}")
  private String cadenReferenceDataNamespace;

  @Value("${rdf4j.schema.namespace}")
  private String schemaNamespace;

  @Value("${rdf4j.owl.namespace}")
  private String owlNamespace;

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(final String database) {
    this.database = database;
  }

  public Literal getLiteralValue(final Object value) {
    return Values.literal(value);
  }

  //#region cadenBase
  public String getCadenBaseNamespace() {
    return cadenBaseNamespace;
  }

  public IRI getCadenBaseIRI(String uri) {
    return iri(this.cadenBaseNamespace + uri);
  }

  public String getCadenBase(String uri) {
    return this.cadenBaseNamespace + uri;
  }

  //#region cadenBaseData
  public String getCadenBaseDataNamespace() {
    return cadenBaseDataNamespace;
  }

  public IRI getCadenBaseDataIRI(String uri) {
    return iri(this.cadenBaseDataNamespace + uri);
  }

  public String getCadenBaseDataBase(String uri) {
    return this.cadenBaseDataNamespace + uri;
  }

  //#region cadenReference
  public String getCadenReferenceNamespace() {
    return cadenReferenceNamespace;
  }

  public IRI getCadenReferenceIRI(String uri) {
    return iri(this.cadenReferenceNamespace + uri);
  }

  public String getCadenReferenceBase(String uri) {
    return this.cadenReferenceNamespace + uri;
  }

  //#region cadenReferenceData
  public String getCadenReferenceDataNamespace() {
    return cadenReferenceDataNamespace;
  }

  public IRI getCadenReferenceDataIRI(String uri) {
    return iri(this.cadenReferenceDataNamespace + uri);
  }

  public String getCadenReferenceDataBase(String uri) {
    return this.cadenReferenceDataNamespace + uri;
  }

  //#region schema
  public String getSchemaNamespace() {
    return this.schemaNamespace;
  }

  public IRI getSchemaIRI(final String uri) {
    return iri(this.schemaNamespace + uri);
  }

  public String getSchemaBase(final String uri) {
    return this.schemaNamespace + uri;
  }

  //#region owl
  public String getOwlNamespace() {
    return this.owlNamespace;
  }

  public IRI getOwlIRI(final String uri) {
    return iri(this.owlNamespace + uri);
  }

  public String getOwlBase(final String uri) {
    return this.owlNamespace + uri;
  }
}
