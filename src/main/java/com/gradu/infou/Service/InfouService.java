package com.gradu.infou.Service;

import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import com.gradu.infou.Domain.Entity.InfouDocument;
import com.gradu.infou.Repository.InfouRepository;
import com.gradu.infou.converter.InfouConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class InfouService {
    private final InfouRepository infouRepository;
    @Transactional(readOnly = true)
    public void addInfou(AddInfouReqDto addInfouReqDto){
        //강의평 작성 xss 보안 추가 필요
        InfouDocument infouDocument = InfouConverter.toInfouDocument(addInfouReqDto);
        infouRepository.save(infouDocument);
    }

    public Slice<InfouDocument> searchInfou(String keyword, Condition condition, Pageable pageable){
        log.info(pageable.getSort().toString());
        Slice<InfouDocument> sliceByLectureName = infouRepository.findSliceByLectureName(keyword, pageable);
        log.info("개수: "+ sliceByLectureName.getSize());

        return sliceByLectureName;
    }
}
