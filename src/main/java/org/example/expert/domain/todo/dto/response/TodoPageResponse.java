package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoPageResponse {

    private final Long id;
    private final String title;             // 일정 제목
    private final Long managerCount;        // 담당자 수
    private final Long commentCount;        // 댓글 개수
    private final LocalDateTime createdAt;  // 생성일
    private final LocalDateTime modifiedAt; // 수정일

    public TodoPageResponse(Long id, String title,
                            Long managerCount, Long commentCount,
                            LocalDateTime createdAt, LocalDateTime modifiedAt){
        this.id = id;
        this.title = title;
        this.managerCount = managerCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
