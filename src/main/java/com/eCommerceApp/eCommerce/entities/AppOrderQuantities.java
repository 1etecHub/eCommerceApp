package com.eCommerceApp.eCommerce.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AppOrderQuantities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /** The product being ordered. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** The quantity being ordered. */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /** The order itself. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private AppOrder order;
}
