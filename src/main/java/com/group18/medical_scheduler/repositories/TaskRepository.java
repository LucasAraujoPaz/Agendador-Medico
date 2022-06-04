package com.group18.medical_scheduler.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group18.medical_scheduler.models.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
}
