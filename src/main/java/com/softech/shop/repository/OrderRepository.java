package com.softech.shop.repository;

import com.softech.shop.model.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nguyen Tri
 */
@Repository
public interface OrderRepository extends CrudRepository<Orders, Integer> {
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT o.* FROM Orders o WHERE o.status like N'Xác%' or o.status like N'%đang%'")
    List<Orders> findAllOrder();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT o.* FROM Orders o WHERE o.status like N'Xác%' or o.status like N'%đang%' or o.status like N'%xong%'")
    List<Orders> findAllOrderFinish();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT o.* FROM Orders o WHERE o.status like N'Chờ%'")
    List<Orders> findAllOrderCho();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT count(*) FROM Orders o WHERE o.status like N'Chờ%'")
    int countFindAllOrderCho();

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "Delete from Orders where orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Integer orderId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM Orders WHERE Orders.CustomerId = :customerId")
    List<Orders> findByCustomerId(@Param("customerId") int customerId);

    @Query(nativeQuery = true, value = "SELECT * FROM Orders WHERE Orders.OrderCode = :orderCode")
    Orders findByOrderCode(@Param("orderCode") String orderCode);
}
