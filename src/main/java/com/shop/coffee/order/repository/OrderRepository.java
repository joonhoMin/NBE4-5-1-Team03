package com.shop.coffee.order.repository;

import com.shop.coffee.order.OrderStatus;
import com.shop.coffee.order.entity.Order;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 주문 상태에 따라 주문 조회
    List<Order> findByOrderStatus(OrderStatus orderStatus);
    //전체 주문 조회
    List<Order> findAll();

    // 주문 상태에 따라 주문 조회 (수정일 내림차순)
    List<Order> findByOrderStatusOrderByModifiedAtDesc(OrderStatus orderStatus);

    //전체 주문 조회 (수정일 내림차순)
    List<Order> findAllByOrderByModifiedAtDesc();

    // 이메일로 주문 유무 확인
    boolean existsByEmail(String email);


    // 주문 생성 시간 내림차순 정렬
    List<Order> findAllByOrderByCreatedAtDesc();
    Optional<Order> findByEmailAndOrderStatus(String email, OrderStatus orderStatus);
    Optional<Order> findByEmailAndOrderStatusAndAddressAndZipcode(String email, OrderStatus orderStatus, String address, String zipCode);
    Optional<Object> findFirstByEmailOrderByCreatedAtDesc(String email);

    // 이메일에 해당하는 주문목록 조회
    List<Order> findByEmail(String email);
    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.item WHERE o.id = :id")
    Optional<Order> findByIdFetchOrderItemsAndItems(@Param("id") Long orderId);

    // createdAt이 start와 end 사이에 포함되고, OrderStatus가 RECEIVED인 Order 조회
    List<Order> findByCreatedAtBetweenAndOrderStatus(LocalDateTime start, LocalDateTime end, OrderStatus orderStatus);
}