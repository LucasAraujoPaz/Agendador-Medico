package com.group18.medical_scheduler.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;

public interface CRUDService<T extends Identifiable> extends CRUD<T>{
	
	CrudRepository<T, Integer> getRepository();
	
	@Override
	public default T create(final T entity) {
		
		Assert.isTrue(
				entity.getId() == null || 
				! getRepository().existsById(entity.getId()),
				"Entity already exists.");
		
		return getRepository().save(entity);
	}

	@Override
	public default T findById(final int id) {
		return getRepository().findById(id).orElseThrow();
	}

	@Override
	public default Iterable<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public default T update(final T entity) {
		
		Assert.isTrue(
				entity.getId() != null && 
				getRepository().existsById(entity.getId()),
				"Entity not found.");
		
		return getRepository().save(entity);
	}

	@Override
	public default void delete(final int id) {
		getRepository().deleteById(id);
	}
}
