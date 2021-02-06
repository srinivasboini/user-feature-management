package com.moneylion.feature.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents Feature table in database
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Data
@Entity
public class Feature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id ;

    @Column(name = "feature_Name",
            nullable = false)
    private String featureName ;

    @CreationTimestamp
    @Column(name="creation_time")
    private LocalDateTime creationTime ;

    @UpdateTimestamp
    @Column(name="update_time")
    private LocalDateTime updateTime ;

}
