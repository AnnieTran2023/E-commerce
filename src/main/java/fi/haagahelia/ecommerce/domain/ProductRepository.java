package fi.haagahelia.ecommerce.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    List<Product> findByName(String name);

    void deleteById(@NonNull ObjectId id);

    List<Product> findByCategoryId(ObjectId categoryId);
}