package com.devsuperior.dscatalog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
    @Autowired
	private CategoryRepository repository;
    @Transactional(readOnly=false)
	public List<CategoryDTO>findAll(){
		List<Category>list=repository.findAll();
		return  list.stream().map(c->new CategoryDTO(c)).toList();//função map transforma um elemento original em outra coisa
	}
    @Transactional(readOnly=false)
	public CategoryDTO findById(Long id) {
		Optional<Category> objDTO= repository.findById(id);
		return new CategoryDTO(objDTO.orElseThrow(()->new ObjectNotFoundException("Categoria não encontrada")));
	}
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		 Category entity=new Category();
		 entity.setName(dto.getName());
		 entity=repository.save(entity);
		 return new CategoryDTO(entity);
	}
	@Transactional
	public CategoryDTO update(Long id,CategoryDTO dto) {
		try {
		Category entity= repository.getById(id);
		entity.setName(dto.getName());
		entity=repository.save(entity);
		return new CategoryDTO(entity);
		}catch(EntityNotFoundException e){
			throw new ObjectNotFoundException("Id not found "+id);
		}
	}
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException("Id not found "+id);
		}
		catch (DataIntegrityViolationException e) {
			// TODO: handle exception
		}
	}
}
