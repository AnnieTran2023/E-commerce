package fi.haagahelia.ecommerce.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

public interface ProductRepository extends MongoRepository <Product, ObjectId> {
    List <Product> findByName(String name);
    void deleteById(@NonNull ObjectId id);

    //find all products by category
    List<Product> findByCategoryName(String categoryName);
}
