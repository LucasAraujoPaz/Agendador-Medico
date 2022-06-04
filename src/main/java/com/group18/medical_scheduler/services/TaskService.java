package com.group18.medical_scheduler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.group18.medical_scheduler.interfaces.CRUDService;
import com.group18.medical_scheduler.models.Task;
import com.group18.medical_scheduler.repositories.TaskRepository;

@Service
public class TaskService implements CRUDService<Task> {
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public CrudRepository<Task, Integer> getRepository() {
		return taskRepository;
	}
}
