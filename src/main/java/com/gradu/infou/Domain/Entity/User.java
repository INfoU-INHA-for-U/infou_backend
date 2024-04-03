package com.gradu.infou.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String authId;
    @Column(nullable = false)
    private String grade;
    @Column(nullable = false)
    private String major;
    @Column(nullable = false)
    @ElementCollection
    private List<String> selectNotice=new ArrayList<>();
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long reward;
    @Column(nullable = false)
    @ColumnDefault("0")
    private Long review;
    public void modifyReward(boolean status, Long value){
        if(status==true) reward += value;
        else reward -= value;
    }

}
