package com.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Category;
import com.ems.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//find  all available categories 
	public List<Category> getCategoryList()
	{
		return categoryRepository.findAll();
	}
	
	//find category with name
	public Category getCategory(String categoryName)
	{
		return categoryRepository.findByName(categoryName);
	}
	
	//find category by id
	public Category getCategoryById(int id)
	{
		return categoryRepository.getById(id);
	}

}
