package org.example.expert.domain.todo.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SearchCondition(

        // 제목 키워드 (부분 일치)
        String title,

        // 생성일 범위 시작
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createStartAt,

        // 생성일 범위 끝
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate createEndAt,

        // 담당자 닉네임 키워드 (부분 일치)
        String nickName
) {
}
