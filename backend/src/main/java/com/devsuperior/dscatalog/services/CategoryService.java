package com.devsuperior.dscatalog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public CategoryDTO findById(Long id) {
		Optional<Category> objDTO= repository.findById(id);
		return new CategoryDTO(objDTO.orElseThrow(()->new ObjectNotFoundException("Categoria não encontrada")));
	}
}