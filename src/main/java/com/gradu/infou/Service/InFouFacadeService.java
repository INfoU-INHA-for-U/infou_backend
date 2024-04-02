package com.gradu.infou.Service;

import com.gradu.infou.Domain.Dto.Controller.AddInfouReqDto;
import com.gradu.infou.Domain.Entity.InfouDocument;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
@RequiredArgsConstructor
public class InFouFacadeService {

    private final InfouService infouService;

    @Transactional
    public void addFacadeInfou(HttpServletRequest request, AddInfouReqDto addInfouReqDto){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        InfouDocument infouDocument = infouService.addInfou(request, addInfouReqDto);
        infouService.addInfouProcess(infouDocument);
        //포인트

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println("infou add 코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }
}
