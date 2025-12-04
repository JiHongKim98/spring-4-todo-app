package demo.todo.adapter.out.jpa.entity

import demo.todo.application.service.model.TodoReadModel
import demo.todo.domain.entity.TodoEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "tbl_todo")
@Entity
class TodoJpaEntity(
    @Id
    val id: String,
    var title: String,
    var description: String,
    var author: String,
    var status: String,
) {
    fun toDomainEntity(): TodoEntity =
        TodoEntity(
            id = id,
            title = title,
            description = description,
            author = author,
            status = TodoEntity.Status.valueOf(status),
        )

    fun toReadModel(): TodoReadModel =
        TodoReadModel(
            id = id,
            title = title,
            description = description,
            author = author,
            status = status,
        )

    fun update(todoEntity: TodoEntity) {
        this.title = todoEntity.title
        this.description = todoEntity.description
        this.author = todoEntity.author
        this.status = todoEntity.status.name
    }

    companion object {
        fun fromDomainEntity(todoEntity: TodoEntity): TodoJpaEntity =
            TodoJpaEntity(
                id = todoEntity.id,
                title = todoEntity.title,
                description = todoEntity.description,
                author = todoEntity.author,
                status = todoEntity.status.name,
            )
    }
}
