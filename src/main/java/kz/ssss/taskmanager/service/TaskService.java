package kz.ssss.taskmanager.service;

import kz.ssss.taskmanager.dto.request.TaskRequest;
import kz.ssss.taskmanager.dto.request.TaskUpdateRequest;
import kz.ssss.taskmanager.dto.response.TaskResponse;
import kz.ssss.taskmanager.exception.TaskNotFoundException;
import kz.ssss.taskmanager.mapper.TaskMapper;
import kz.ssss.taskmanager.model.Status;
import kz.ssss.taskmanager.model.Task;
import kz.ssss.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final UserService userService;

    public Page<TaskResponse> list(Status status, Pageable page) {
        var current = userService.getCurrentUser();
        log.info("Listing tasks for user {} with status {}", current.getUsername(), status);
        Page<Task> tasks;

        if (status == null) {
            tasks = repository.findAllByUser(current, page);
        } else {
            tasks = repository.findAllByUserAndStatus(current, status, page);
        }

        return tasks.map(mapper::toResponse);
    }

    public TaskResponse create(TaskRequest dto) {
        var current = userService.getCurrentUser();
        log.info("Creating task for user {}: {}", current.getUsername(), dto.getTitle());
        var t = mapper.toEntity(dto);
        t.setStatus(Status.TODO);
        t.setUser(current);
        Task saved = repository.save(t);
        log.debug("Task created: {}", saved.getId());
        return mapper.toResponse(saved);
    }

    public TaskResponse get(long id) {
        var current = userService.getCurrentUser();
        log.info("Retrieving task {} for user {}", id, current.getUsername());
        var t = repository.findByIdAndUser(id, current)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return mapper.toResponse(t);
    }

    public TaskResponse update(long id, TaskUpdateRequest dto) {
        var current = userService.getCurrentUser();
        log.info("Updating task {} for user {}", id, current.getUsername());
        var t = repository.findByIdAndUser(id, current)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setStatus(dto.getStatus());
        Task updated = repository.save(t);
        log.debug("Task {} updated", updated.getId());
        return mapper.toResponse(updated);
    }

    public void delete(long id) {
        var current = userService.getCurrentUser();
        log.info("Deleting task {} for user {}", id, current.getUsername());
        var t = repository.findByIdAndUser(id, current)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        repository.delete(t);
        log.debug("Task {} deleted", id);
    }
}
