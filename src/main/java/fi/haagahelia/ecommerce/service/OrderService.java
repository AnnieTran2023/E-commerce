package fi.haagahelia.ecommerce.service;

import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByUserId(ObjectId userId) {
        return orderRepository.findByUserId(userId);
    }
}