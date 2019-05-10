package com.mj.tutorial.jpa_lock.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "record")

@Data
@ToString
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "redeemd")
    private Integer redeemed;

    @Column(name = "created_datetime")
    private Timestamp createdDatetime;

}
