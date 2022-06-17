package com.group18.medical_scheduler.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group18.medical_scheduler.interfaces.CRUD;
import com.group18.medical_scheduler.models.Task;
import com.group18.medical_scheduler.services.TaskService;
import com.group18.medical_scheduler.services.UserService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController implements CRUD<Task> {

	@Autowired
	private TaskService taskService; 
	@Autowired
	private UserService userService;
	
	@Override
	@PostMapping
	public Task create(@RequestBody @Valid final Task task) {
		
		var taskWithUser = new Task(
				task.getId(), 
				task.getDescription(), 
				task.getDueDate(), 
				userService.getLoggedInUser());
		
		return taskService.create(taskWithUser);
	}

	@Override
	@GetMapping("/{id}")
	public Task findById(@PathVariable final int id) {
		return taskService.findById(id);
	}

	@Override
	@GetMapping
	public Iterable<Task> findAll() {
		return userService.getLoggedInUser().getTasks();
	}

	@Override
	@PutMapping
	public Task update(@RequestBody @Valid final Task task) {
		return taskService.update(task);
	}

	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable final int id) {
		taskService.delete(id);
	}
}