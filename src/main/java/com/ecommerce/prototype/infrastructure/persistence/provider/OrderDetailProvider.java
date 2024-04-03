package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.OrderDetail;
import com.ecommerce.prototype.application.usecase.ProcessPaymentUseCase;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.OrderDetailRepository;
import com.ecommerce.prototype.application.usecase.repository.OrderRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrder;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperOrderDetail;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.OrderDetaildb;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Orderdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderDetailJPARepository;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.OrderJPARepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;
@Component
@AllArgsConstructor
public class OrderDetailProvider implements OrderDetailRepository {

    private final OrderDetailJPARepository orderDetailJPARepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailProvider.class);


    /**
     * Saves an order detail.
     *
     * @param orderDetail The order detail to be saved.
     * @return The saved order detail.
     */
    @Override
    public OrderDetail save(OrderDetail orderDetail) {

        OrderDetaildb orderDetaildb = MapperOrderDetail.mapToModel(orderDetail);
        orderDetailJPARepository.save(orderDetaildb);
        return orderDetail;
    }

    /**
     * Retrieves an order detail by its ID.
     *
     * @param orderDetailId The ID of the order detail to retrieve.
     * @return The order detail with the specified ID.
     * @throws OrderNotFoundException If the order detail with the specified ID does not exist.
     */
    @Override
    public Optional<OrderDetail> findById(Integer orderDetailId) {

        OrderDetaildb orderDetaildb = orderDetailJPARepository.findById(orderDetailId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderDetailId));
        return Optional.of(MapperOrderDetail.mapToDomain(orderDetaildb));
    }

    /**
     * Creates an order detail based on the provided order and order detail information.
     *
     * @param orderDetail The order detail information to be used for creating the order detail.
     * @return The created order detail.
     * @throws RuntimeException If an error occurs while creating the order detail.
     */
    @Override
    public OrderDetaildb createOrderDetail(OrderDetail orderDetail) {

        logger.info("start saving createOrderDetail");
        OrderDetaildb orderDetaildb = MapperOrderDetail.mapToModel(orderDetail);
        logger.info("create orderDetaildb {}: ", orderDetaildb);

        return orderDetailJPARepository.save(orderDetaildb);
    }

}
