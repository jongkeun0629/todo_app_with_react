package com.jongkeun.backend.service;

import com.jongkeun.backend.dto.TodoDto;
import com.jongkeun.backend.dto.TodoResponseDto;
import com.jongkeun.backend.dto.TodoStatsDto;
import com.jongkeun.backend.dto.TodoUpdateDto;
import com.jongkeun.backend.model.Todo;
import com.jongkeun.backend.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public List<TodoResponseDto> getAllTodos(){
        return todoRepository.findAllByOrderByCreatedAtDesc().stream().map(TodoResponseDto::fromEntity).toList();
    }

    public List<TodoResponseDto> getTodosByCompleted(Boolean completed){
        return todoRepository.findByCompletedOrderByCreatedAtDesc(completed).stream().map(TodoResponseDto::fromEntity).toList();
    }

    public List<TodoResponseDto> searchTodosByTitle(String title){
        return todoRepository.findByTitleContainingIgnoreCase(title).stream().map(TodoResponseDto::fromEntity).toList();
    }

    public Page<TodoResponseDto> getAllTodos(Pageable pageable){
        return todoRepository.findAll(pageable).map(TodoResponseDto::fromEntity);
    }

    public TodoResponseDto getTodoById(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
        return TodoResponseDto.fromEntity(todo);
    }

    public TodoResponseDto createTodo(TodoDto dto){
        Todo todo = new Todo(dto.getTitle(), dto.getDescription());
        Todo savedTdo = todoRepository.save(todo);
        return TodoResponseDto.fromEntity(savedTdo);
    }

    public TodoResponseDto updateTodo(Long id, TodoUpdateDto dto){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));

        if(dto.getTitle() != null && !dto.getTitle().trim().isEmpty()){
            todo.setTitle(dto.getTitle());
        }
        if(dto.getDescription() != null){
            todo.setDescription(dto.getDescription());
        }
        if(dto.getCompleted() != null){
            todo.setCompleted(dto.getCompleted());
        }

        Todo updatedTodo = todoRepository.save(todo);
        return TodoResponseDto.fromEntity(updatedTodo);
    }

    public TodoResponseDto toggleTodoCompleted(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
        todo.setCompleted(!todo.getCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return TodoResponseDto.fromEntity(updatedTodo);
    }

    public void deleteTodo(Long id){
        if(!todoRepository.existsById(id)){
            throw new NoSuchElementException("찾을 수 없음");
        }
        todoRepository.deleteById(id);
    }

    public TodoStatsDto getStats(){
        long totalCount = todoRepository.count();
        long completedCount = todoRepository.countByCompleted(true);
        long pendingCont = todoRepository.countByCompleted(false);

        return new TodoStatsDto(totalCount, completedCount, pendingCont);
    }
}
