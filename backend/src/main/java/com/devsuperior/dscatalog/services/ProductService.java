package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
    @Autowired
	private ProductRepository repository;
    @Transactional(readOnly=false)
	public Page<ProductDTO>findAllPaged(PageRequest pageRequest){
		Page<Product>list=repository.findAll(pageRequest);
		return  list.map(c->new ProductDTO(c));//função map transforma um elemento original em outra coisa
	}
    @Transactional(readOnly=false)
	public ProductDTO findById(Long id) {
		Optional<Product> objDTO= repository.findById(id);
		Product entity=objDTO.orElseThrow(()->new ObjectNotFoundException("Categoria não encontrada"));
		return new ProductDTO(entity,entity.getCategories());
	}
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity=new Product();
		 entity.setName(dto.getName());
		 entity=repository.save(entity);
		 return new ProductDTO(entity);
	}
	@Transactional
	public ProductDTO update(Long id,ProductDTO dto) {
		try {
			Product entity= repository.getById(id);
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity=repository.save(entity);
		return new ProductDTO(entity);
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
			throw new DatabaseException("Não é possível deletar Categorias com produtos associados");
		}
	}
}
