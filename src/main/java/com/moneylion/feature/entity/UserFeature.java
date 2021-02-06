package com.moneylion.feature.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents UserFeature table in database
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

@Data
@Entity
public class UserFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",
            nullable = false)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature ;

    @Column(name = "enabled",
            nullable = false)
    private Boolean enabled ;

    @CreationTimestamp
    @Column(name="creation_time")
    private LocalDateTime creationTime ;

    @UpdateTimestamp
    @Column(name="update_time")
    private LocalDateTime updateTime ;
}
