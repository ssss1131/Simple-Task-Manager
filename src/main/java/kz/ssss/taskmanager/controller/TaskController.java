package kz.ssss.taskmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("NICE");
    }

}
