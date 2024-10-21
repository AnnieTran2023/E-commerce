package fi.haagahelia.ecommerce.domain;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {
    Optional <User> findByUsername(String username);
}
