package kz.ssss.taskmanager.dto.response;

import kz.ssss.taskmanager.model.Status;
import lombok.Data;

import java.time.Instant;

@Data
public class TaskResponse {

    Long id;
    String title;
    String description;
    Status status;
    Instant createdAt;
    Instant updatedAt;

}
