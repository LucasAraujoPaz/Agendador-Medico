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

@RestController
@RequestMapping("/api/tasks")
public class TaskController implements CRUD<Task> {

	@Autowired
	private TaskService taskService; 
	
	@Override
	@PostMapping
	public Task create(@RequestBody @Valid final Task task) {
		return taskService.create(task);
	}

	@Override
	@GetMapping("/{id}")
	public Task findById(@PathVariable final int id) {
		return taskService.findById(id);
	}

	@Override
	@GetMapping
	public Iterable<Task> findAll() {
		return taskService.findAll();
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