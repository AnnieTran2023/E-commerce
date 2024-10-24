package fi.haagahelia.ecommerce.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderProductRepository extends MongoRepository <OrderProduct, ObjectId>{
    List <OrderProduct> findByUserId(ObjectId userId);
}
