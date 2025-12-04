package demo.todo.adapter.out.jpa

import demo.todo.adapter.out.jpa.entity.TodoJpaEntity
import demo.todo.adapter.out.jpa.repository.TodoJpaRepository
import demo.todo.application.port.out.TodoCommandRepository
import demo.todo.application.port.out.TodoQueryRepository
import demo.todo.application.service.model.TodoReadModel
import demo.todo.domain.entity.TodoEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class TodoCommandJpaAdapter(
    private val todoJpaRepository: TodoJpaRepository,
) : TodoCommandRepository,
    TodoQueryRepository {
    @Transactional
    override fun save(todoEntity: TodoEntity): TodoEntity {
        val jpaEntity = TodoJpaEntity.fromDomainEntity(todoEntity)
        return todoJpaRepository.save(jpaEntity).toDomainEntity()
    }

    @Transactional
    override fun update(
        id: String,
        modifier: (TodoEntity) -> TodoEntity,
    ): TodoEntity {
        val todoJpaEntity = todoJpaRepository.findByIdOrNull(id)
            ?: throw JpaException.NotExists()

        val todoEntity = modifier.invoke(todoJpaEntity.toDomainEntity())
        todoJpaEntity.update(todoEntity)

        return todoJpaRepository.save(todoJpaEntity).toDomainEntity()
    }

    override fun getById(id: String): TodoReadModel {
        val todoJpaEntity = todoJpaRepository.findByIdWithoutDelete(id)
            ?: throw JpaException.NotExists()
        return todoJpaEntity.toReadModel()
    }

    override fun getAll(): List<TodoReadModel> {
        val todoJpaEntities = todoJpaRepository.findAllByIdWithoutDelete()
        return todoJpaEntities.map { it.toReadModel() }
    }
}
