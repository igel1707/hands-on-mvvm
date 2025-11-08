package com.kelompok6.todolistreactiveapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kelompok6.todolistreactiveapp.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoViewModel : ViewModel() {
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    // New: filter state
    enum class TodoFilter { All, Active, Completed }

    private val _filter = MutableStateFlow(TodoFilter.All)
    val filter: StateFlow<TodoFilter> = _filter

    fun setFilter(f: TodoFilter) {
        _filter.value = f
    }

    // Add deadline parameter (epoch millis) optional
    fun addTask(title: String, deadline: Long? = null) {
        val nextId = (_todos.value.maxOfOrNull { it.id } ?: 0) + 1
        val now = System.currentTimeMillis()
        val newTask = Todo(id = nextId, title = title, createdAt = now, deadline = deadline)
        _todos.value = _todos.value + newTask
    }
    fun toggleTask(id: Int) {
        _todos.value = _todos.value.map { t ->
            if (t.id == id) t.copy(isDone = !t.isDone) else t
        }
    }
    fun deleteTask(id: Int) {
        _todos.value = _todos.value.filterNot { it.id == id }
    }
}
