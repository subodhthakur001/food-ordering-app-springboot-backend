package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private Long totalAmount;

    private String orderStatus;

    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    private Long totalItems;

    private Long totalPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
