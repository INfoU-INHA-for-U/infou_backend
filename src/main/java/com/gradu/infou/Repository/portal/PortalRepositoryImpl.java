package com.gradu.infou.Repository.portal;

import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import com.gradu.infou.Domain.Dto.Service.QPortalResponseDto;
import com.gradu.infou.Domain.Entity.Portal;
import com.gradu.infou.Domain.Entity.PortalProfessor;
import com.gradu.infou.Domain.Entity.QPortal;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.gradu.infou.Domain.Entity.QPortal.*;
import static com.gradu.infou.Domain.Entity.QPortalProfessor.portalProfessor;
import static com.gradu.infou.Domain.Entity.QProfessor.professor;

@RequiredArgsConstructor
public class PortalRepositoryImpl implements PortalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PortalResponseDto> findSliceByCondition(@RequestParam(required = true) SearchCondition condition, @RequestParam(required = false) Pageable pageable) {
        List<Portal> portals = queryFactory
                .selectFrom(portal)
                .where(
                        departmentEq(condition.getMajor()),
                        lectureNameIn(condition.getLectureNames())
                )
                .orderBy(portal.option_5.desc(), portal.option_4.desc(), portal.option_3.desc(), portal.option_2.desc(), portal.option_1.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        queryFactory
                .selectFrom(portalProfessor)
                .join(portalProfessor.professor, professor).fetchJoin()
                .where(portalProfessor.portal.in(portals))
                .fetch();

        List<PortalResponseDto> content = portals.stream()
                .map(PortalResponseDto::fromEntity)
                .toList();

        boolean hasNext = portals.size() > pageable.getPageSize();

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression departmentEq(String major) {
        return StringUtils.hasText(major) ? portal.department.eq(major) : null;
    }

    private BooleanExpression lectureNameIn(List<String> lectureNames) {
        return !lectureNames.isEmpty() ? portal.lectureName.in(lectureNames) : null;
    }
}
