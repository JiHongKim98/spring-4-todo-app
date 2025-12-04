package demo.todo.adapter.out.jpa

import demo.todo.common.exception.BaseSuppressedException

sealed class JpaException(
    message: String,
) : BaseSuppressedException(message, Type.INFRA) {
    class NotExists : JpaException("Not exists")
}
