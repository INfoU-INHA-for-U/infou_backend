package com.gradu.infou.Service;

import com.gradu.infou.Domain.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
    private final UserService userService;
    public void addLog(HttpServletRequest request, String type, String id){
        User user = userService.findUserByRequest(request);

        MDC.put("grade",user.getGrade());
        MDC.put("department",user.getMajor());
        MDC.put("id", id);
        log.info(type);
        MDC.clear();
    }
}
