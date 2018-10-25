/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softech.shop.repository;

import com.softech.shop.model.OrderDetails;
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
public interface OrderDetailRepository extends CrudRepository<OrderDetails, Integer> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "Select top 4 sum(Quantity) as total,ProductId from OrderDetails  group by ProductId  order by  sum(Quantity) desc")
    List<OrderDetails> findStatics();
    
    @Query(nativeQuery = true, value = "Select OrderDetails.* from Orders join OrderDetails on Orders.orderId = OrderDetails.orderId "
            + "where Orders.orderId = :orderId")
    List<OrderDetails> findAllOrderDetail(@Param("orderId") Integer orderId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "Delete from OrderDetails where orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Integer orderId);

 @Query(nativeQuery = true,value = "SELECT * FROM OrderDetails WHERE OrderId = :orderId")
    List<OrderDetails> findByOrderId(@Param("orderId")Integer orderId);
}
