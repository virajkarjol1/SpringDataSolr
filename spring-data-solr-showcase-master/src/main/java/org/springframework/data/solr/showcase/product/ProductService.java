package org.springframework.data.solr.showcase.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.showcase.product.model.Product;


public interface ProductService {

	int DEFAULT_PAGE_SIZE = 3;

	Page<Product> findByName(String searchTerm, Pageable pageable);

	Product findById(String id);
	
	List<Product> findByCategories(String searchTerm);
	
	List<Product> findByName(String searchTerm);

	FacetPage<Product> autocompleteNameFragment(String fragment, Pageable pageable);

}
