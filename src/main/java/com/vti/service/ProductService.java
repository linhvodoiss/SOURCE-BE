package com.vti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.vti.dto.ProductDTO;
import com.vti.dto.filter.ProductFilter;
import com.vti.entity.CartItem;
import com.vti.entity.Category;
import com.vti.entity.Product;
import com.vti.form.ProductFormForCreating;
import com.vti.form.ProductFormForUpdating;
import com.vti.repository.CartItemRepository;
import com.vti.repository.CategoryRepository;
import com.vti.repository.ProductRepository;
import com.vti.specification.ProductSpecificationBuilder;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired 
	private CartItemRepository cartitemRepository;
	

	@Autowired
	private ModelMapper modelMapper;
	
	public Page<Product> getAllProducts(Pageable pageable, ProductFilter filter, String search) {
        ProductSpecificationBuilder specification = new ProductSpecificationBuilder(filter, search);
        return repository.findAll(specification.build(), pageable);
    }

    public List<ProductDTO> convertToDto(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            if (product.getCategory() != null) {
                productDTO.setCategory_id(product.getCategory().getId());
                productDTO.setCategory_name(product.getCategory().getName());
            }
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

	public Product getProductByID(int id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public Page<Product> getListProduct(Pageable pageable, Integer id) {
	    if (id != null) {
	        return repository.getListProductByCategoryId(pageable, id);
	    } else {
	        return repository.findAll(pageable);
	    }
	}

	@Transactional
	public void createProduct(ProductFormForCreating form) {	
		// omit id field
		TypeMap<ProductFormForCreating, Product> typeMap = modelMapper.getTypeMap(ProductFormForCreating.class, Product.class);
		if (typeMap == null) { // if not already added
			// skip field
			modelMapper.addMappings(new PropertyMap<ProductFormForCreating, Product>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}
		// convert form to entity
	    Product productEntity = modelMapper.map(form, Product.class);
		
		Integer category_id = form.getCategory_id();	
		Category category = categoryRepository.findById(category_id).get();
		productEntity.setCategory(category);
		
		// create product
	    repository.save(productEntity);

	}

	@Transactional
	public void updateProduct(ProductFormForUpdating form) {

		// fetch existing product
		Product product = repository.findById(form.getId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// update fields
		modelMapper.map(form, product);

		// save updated product
		repository.save(product);
	}


	@Transactional
    public void deleteProducts(Set<Integer> ids) {
        List<Product> products = repository.findAllById(ids);
        List<Category> categoryEntities = new ArrayList<>();
        
        for (Product product : products) {
            Category category = product.getCategory();
            categoryEntities.add(category);
        }
        
        categoryRepository.saveAll(categoryEntities);
        
        for (Integer id : ids) {
            List<CartItem> cartitems = cartitemRepository.findByProduct_Id(id);
            cartitemRepository.deleteAll(cartitems);
        }
        
        repository.deleteByIdIn(ids);
    }
	

	public boolean isProductExistsByName(String username) {
		return repository.existsByName(username);
	}
}


