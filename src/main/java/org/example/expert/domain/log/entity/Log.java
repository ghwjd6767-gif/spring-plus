package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;

    // 요청한 유저 아이디
    private Long requestUserId;

    @Enumerated(EnumType.STRING)
    private Status result;

    // 실패 사유
    private String failReason;

    public Log(Long todoId, Long requestUserId, Status result, String failReason) {
        this.todoId = todoId;
        this.requestUserId = requestUserId;
        this.result = result;
        this.failReason = failReason;
    }
}
