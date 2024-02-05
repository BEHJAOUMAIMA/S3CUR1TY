package com.example.s3cur1ty.web.rest;

import com.example.s3cur1ty.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskRest {

    private final TaskService taskService;



}
