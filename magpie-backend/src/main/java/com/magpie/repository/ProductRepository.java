package com.magpie.repository;

import com.magpie.entity.Country;
import com.magpie.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public List<Product> findAll();
}
