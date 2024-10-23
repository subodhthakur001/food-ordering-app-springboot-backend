package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private Category foodCategory;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private boolean availaible;

    @ManyToOne
    private Restaurant restaurant;

    private boolean isVegetarian;

    private boolean isSeasonal;

    @ManyToMany
    private List<IngredientsItem> ingredients = new ArrayList<>();
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
