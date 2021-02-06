package com.moneylion.feature.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents User table in database
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Long id ;

    @Column(name = "user_email",
            nullable = false)
    private String userEmail ;

    @CreationTimestamp
    @Column(name="creation_time")
    private LocalDateTime creationTime ;

    @UpdateTimestamp
    @Column(name="update_time")
    private LocalDateTime updateTime ;

}
