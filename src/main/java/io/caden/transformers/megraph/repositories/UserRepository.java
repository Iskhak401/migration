package io.caden.transformers.megraph.repositories;

import io.caden.transformers.megraph.entities.User;
import io.caden.transformers.shared.repositories.Repository;

public interface UserRepository extends Repository<User> {
  void update(User user) throws Exception;

  void save(User user) throws Exception;
}
