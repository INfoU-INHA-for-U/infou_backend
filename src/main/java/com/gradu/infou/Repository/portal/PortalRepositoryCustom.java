package com.gradu.infou.Repository.portal;

import com.gradu.infou.Domain.Dto.Controller.SearchCondition;
import com.gradu.infou.Domain.Dto.Service.PortalResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PortalRepositoryCustom {
    Slice<PortalResponseDto> findSliceByCondition(SearchCondition condition, Pageable pageable);
}
