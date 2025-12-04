package demo.todo.adapter.out.jpa.repository

import demo.todo.adapter.out.jpa.entity.TodoJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

internal interface TodoJpaRepository : JpaRepository<TodoJpaEntity, String> {
    @Query(
        """
            select tje from TodoJpaEntity tje
            where tje.id = :id
                and tje.status != 'DELETED'
        """,
    )
    fun findByIdWithoutDelete(id: String): TodoJpaEntity?

    @Query(
        """
            select tje from TodoJpaEntity tje
            where tje.status != 'DELETED'
        """,
    )
    fun findAllByIdWithoutDelete(): List<TodoJpaEntity>
}
