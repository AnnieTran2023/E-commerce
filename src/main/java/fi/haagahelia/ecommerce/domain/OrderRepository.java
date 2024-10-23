package fi.haagahelia.ecommerce.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository <Order, ObjectId>{
    List <Order> findByUserId(ObjectId userId);
    
}
