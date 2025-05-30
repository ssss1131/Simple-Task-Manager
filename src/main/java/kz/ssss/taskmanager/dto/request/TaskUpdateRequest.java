package kz.ssss.taskmanager.dto.request;

import kz.ssss.taskmanager.model.Status;
import lombok.Data;

@Data
public class TaskUpdateRequest {

    String title;
    String description;
    Status status;

}
