package com.gradu.infou.logstashTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradu.infou.Config.exception.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.gradu.infou.Config.BaseResponseStatus.RESPONSE_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogController {

    private final ElasticSearchItemsRepository elasticSearchItemsRepository;
    @GetMapping("/log")
    public String hello(){
        //ObjectMapper mapper=new ObjectMapper();
        //LogInfo logInfo = new LogInfo("aaa", "bbb");
        //Log log1 = new Log(logInfo);

        MDC.put("student","홍길동");
        MDC.put("professor","강상길");
        log.info("강의평 데이터");
        MDC.clear();

        return "ok";
    }

    // @GetMapping("/test")
    // public Iterable<ElasticSearchItems> test(){
    //     Iterable<ElasticSearchItems> all = elasticSearchItemsRepository.findAll();

    //     return all;
    // }
}
