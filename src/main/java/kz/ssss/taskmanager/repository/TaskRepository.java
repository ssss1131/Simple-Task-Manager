package kz.ssss.taskmanager.repository;

import kz.ssss.taskmanager.model.Status;
import kz.ssss.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByStatus(Status status, Pageable pageable);

}
