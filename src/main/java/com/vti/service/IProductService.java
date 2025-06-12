package com.vti.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import com.vti.dto.ProductDTO;
import com.vti.dto.filter.ProductFilter;
import com.vti.entity.Product;

public interface IProductService {

	Page<Product> getAllProducts(Pageable pageable, ProductFilter filter, String search);

	boolean isProductExistsByName(String name);

	void createProduct(ProductFormForCreating form);

	public Product getProductByID(int id);

	public void updateProduct(ProductFormForUpdating form);

	public void deleteProducts(Set<Integer> ids);

	Page<Product> getListProduct(Pageable pageable, Integer id);

	List<ProductDTO> convertToDto(List<Product> content);

}
