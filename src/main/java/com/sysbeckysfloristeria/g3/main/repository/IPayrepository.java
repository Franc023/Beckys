package com.sysbeckysfloristeria.g3.main.repository;

import com.sysbeckysfloristeria.g3.main.model.Pay;
import com.sysbeckysfloristeria.g3.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPayrepository extends JpaRepository<Pay,Long> {
    List<Pay> findByUser(User user);
}
