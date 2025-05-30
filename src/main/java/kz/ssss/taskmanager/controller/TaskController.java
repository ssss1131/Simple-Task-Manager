package kz.ssss.taskmanager.controller;

import jakarta.validation.Valid;
import kz.ssss.taskmanager.dto.request.TaskRequest;
import kz.ssss.taskmanager.dto.request.TaskUpdateRequest;
import kz.ssss.taskmanager.dto.response.TaskResponse;
import kz.ssss.taskmanager.model.Status;
import kz.ssss.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponse> list(Status status, Pageable page){
        return service.list(status, page);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody @Valid TaskRequest dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse update(@PathVariable Long id,
                          @RequestBody @Valid TaskUpdateRequest dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
