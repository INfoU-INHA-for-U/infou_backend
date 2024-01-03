package com.gradu.infou.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Professor extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;
}
