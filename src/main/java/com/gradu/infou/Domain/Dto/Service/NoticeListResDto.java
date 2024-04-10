package com.gradu.infou.Domain.Dto.Service;

import com.gradu.infou.Domain.Entity.Mapping.NoticeMapping;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class NoticeListResDto {
    private List<String> noticeList;
    private List<NoticeList> noticeDocumentList;

    public static NoticeListResDto toNoticeListResDto(List<String> noticeList, List<NoticeList> noticeDocumentList){
        return NoticeListResDto.builder()
                .noticeList(noticeList)
                .noticeDocumentList(noticeDocumentList)
                .build();
    }
}
