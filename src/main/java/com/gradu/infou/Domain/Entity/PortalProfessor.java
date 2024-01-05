package com.gradu.infou.Domain.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"portal"})
public class PortalProfessor extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ToStringExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portal_id")
    private Portal portal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
