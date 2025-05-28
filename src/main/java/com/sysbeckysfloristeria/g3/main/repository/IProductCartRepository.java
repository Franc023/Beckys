package com.sysbeckysfloristeria.g3.main.repository;

import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCartRepository extends JpaRepository<ProductCart, Long> {
}
