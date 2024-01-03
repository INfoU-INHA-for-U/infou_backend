package com.gradu.infou.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class Portal extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String lectureName;

    @Column(length = 30, nullable = false)
    private String department;

    @Column(length = 20, nullable = false)
    private String academicNumber;

    @Column(length = 10, nullable = false)
    private String semester;

    @Column(length = 10)
    private String surveyCnt;

    @Column(length = 10)
    private String totalCnt;

    @Column(length = 10)
    private String option_1;

    @Column(length = 10)
    private String option_2;

    @Column(length = 10)
    private String option_3;

    @Column(length = 10)
    private String option_4;

    @Column(length = 10)
    private String option_5;

    @Column(length = 10, nullable = false)
    private Long detailUk;

}
