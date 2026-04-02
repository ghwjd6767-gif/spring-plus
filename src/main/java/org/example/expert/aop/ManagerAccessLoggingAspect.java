package org.example.expert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.entity.Status;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerAccessLoggingAspect {

    private final LogService logService;

    @Around("execution(* org.example.expert.domain.manager.controller.ManagerController.saveManager(..))")
    public Object logManagerSave(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        AuthUser authUser = (AuthUser) args[0];
        Long todoId = (Long) args[1];
        ManagerSaveRequest request = (ManagerSaveRequest) args[2];

        try {
            Object result = joinPoint.proceed();

            logService.saveLog(todoId, authUser.getId(), Status.SUCCESS, null
            );
            return result;
        } catch (Exception e) {
            logService.saveLog(todoId, authUser.getId(), Status.FAIL, e.getMessage()
            );
            throw e;
        }
    }
}

