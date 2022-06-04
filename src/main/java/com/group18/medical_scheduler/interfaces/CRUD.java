package com.group18.medical_scheduler.interfaces;

public interface CRUD<T> {
	
	T create(final T entity);
	
	T findById(final int id);
	
	Iterable<T> findAll();
	
	T update(final T entity);
	
	void delete(final int id);
}
