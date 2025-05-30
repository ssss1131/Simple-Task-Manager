package kz.ssss.taskmanager.mapper;

import kz.ssss.taskmanager.dto.request.TaskRequest;
import kz.ssss.taskmanager.dto.response.TaskResponse;
import kz.ssss.taskmanager.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toResponse(Task task);
    Task toEntity(TaskRequest dto);

}
