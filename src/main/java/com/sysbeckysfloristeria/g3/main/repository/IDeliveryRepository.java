package com.sysbeckysfloristeria.g3.main.repository;

import com.sysbeckysfloristeria.g3.main.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeliveryRepository extends JpaRepository<Delivery, Long> {
}
