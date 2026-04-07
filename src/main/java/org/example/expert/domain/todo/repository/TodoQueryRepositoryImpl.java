package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.SearchCondition;
import org.example.expert.domain.todo.dto.response.TodoPageResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoQueryRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;

        Todo result = queryFactory
                .selectFrom(todo)
                .join(todo.user, user).fetchJoin() // fetch join 으로 N+1 방지!
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoPageResponse> findTodosByCondition(Pageable pageable, SearchCondition searchCondition) {

        List<TodoPageResponse> content = queryFactory
                .select(Projections.constructor(
                TodoPageResponse.class,
                        todo.id,
                        todo.title,
                        manager.count(),
                        comment.count(),
                        todo.createdAt,
                        todo.modifiedAt
        )).from(todo)
                .join(todo.user, user)
                .leftJoin(manager).on(manager.todo.id.eq(todo.id))
                .leftJoin(comment).on(comment.todo.id.eq(todo.id))
                .where(
                        nicknameContains(searchCondition.nickName()),
                        titleContains(searchCondition.title()),
                        createdAtAfter(searchCondition.createStartAt()),
                        createdAtBefore(searchCondition.createEndAt())
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .join(todo.user, user)
                .where(nicknameContains(searchCondition.nickName()),
                        titleContains(searchCondition.title()),
                        createdAtAfter(searchCondition.createStartAt()),
                        createdAtBefore(searchCondition.createEndAt()))
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nicknameContains(String nickName) {
        return nickName != null ? user.nickname.contains(nickName) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? todo.title.contains(title) : null;
    }

    private BooleanExpression createdAtAfter(LocalDate createStartAt) {
        return createStartAt != null ? todo.createdAt.goe(createStartAt.atStartOfDay()) : null;
    }

    private BooleanExpression createdAtBefore(LocalDate createEndAt) {
        return createEndAt != null ? todo.createdAt.loe(createEndAt.atTime(23,59,59)) : null;
    }

}
