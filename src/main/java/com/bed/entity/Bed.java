package com.bed.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bed")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = AuditingEntityListener.class)
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account",nullable = false)
    private String account;

    @Column(name = "url",nullable = false)
    private String url;

    @Column(name = "type",nullable = false)
    private String type;

    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
