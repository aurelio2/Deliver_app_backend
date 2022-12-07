package com.deliver.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliver.api.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
	//ordernar produtos pelo nome
	List<Product> findAllByOrderByNameAsc();
}
