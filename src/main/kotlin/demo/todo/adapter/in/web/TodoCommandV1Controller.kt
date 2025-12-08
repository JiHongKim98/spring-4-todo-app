package demo.todo.adapter.`in`.web

import demo.todo.adapter.`in`.web.request.CreateTodoRequest
import demo.todo.adapter.`in`.web.request.UpdateTodoRequest
import demo.todo.adapter.`in`.web.response.TodoIdResponse
import demo.todo.application.port.`in`.CompleteTodoUseCase
import demo.todo.application.port.`in`.CreateTodoUseCase
import demo.todo.application.port.`in`.DeleteTodoUseCase
import demo.todo.application.port.`in`.UpdateTodoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * version 설정에서 '1+' 의 의미는 supportVersion 으로 등록된 범위 내에서 1 버전 이후의 모든 버전을 의미
 * @see demo.todo.common.config.AppVersionConfig.configureApiVersioning()
 */
@RestController
@RequestMapping("/api/todos", version = "1+")
internal class TodoCommandV1Controller(
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val completeTodoUseCase: CompleteTodoUseCase,
) {
    @PostMapping
    fun createTodo(
        @RequestHeader(AUTHOR_INFO_HEADER) author: String,
        @RequestBody request: CreateTodoRequest,
    ): ResponseEntity<TodoIdResponse> {
        val command = request.toCommand(author = author)
        val id = createTodoUseCase.execute(command)
        return ResponseEntity.ok(TodoIdResponse(id))
    }

    @PatchMapping("/{id}")
    fun updateTodo(
        @PathVariable id: String,
        @RequestHeader(AUTHOR_INFO_HEADER) author: String,
        @RequestBody request: UpdateTodoRequest,
    ): ResponseEntity<TodoIdResponse> {
        val command = request.toCommand(id = id, author = author)
        val id = updateTodoUseCase.execute(command)
        return ResponseEntity.ok(TodoIdResponse(id))
    }

    @PostMapping("/{id}/done")
    fun completeTodo(
        @PathVariable id: String,
        @RequestHeader(AUTHOR_INFO_HEADER) author: String,
    ): ResponseEntity<TodoIdResponse> {
        val command = CompleteTodoUseCase.Command(id = id, author = author)
        val id = completeTodoUseCase.execute(command)
        return ResponseEntity.ok(TodoIdResponse(id))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(
        @PathVariable id: String,
        @RequestHeader(AUTHOR_INFO_HEADER) author: String,
    ): ResponseEntity<TodoIdResponse> {
        val command = DeleteTodoUseCase.Command(id = id, author = author)
        val id = deleteTodoUseCase.execute(command)
        return ResponseEntity.ok(TodoIdResponse(id))
    }

    companion object {
        private const val AUTHOR_INFO_HEADER = "X-Author-Info"
    }
}
