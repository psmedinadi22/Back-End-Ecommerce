package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.usecase.repository.ProductRepository;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Productdb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class GetProductUseCase {

    private ProductRepository productRepository;

    public Optional<Productdb> findById(Integer productId){

        return productRepository.findById(productId);
    }

}
