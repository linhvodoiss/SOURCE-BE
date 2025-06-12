package com.fpt.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.fpt.entity.Cart;

public class CartSpecificationBuilder {
	private String search;

	public CartSpecificationBuilder(String search) {
		this.search = search;
	}

	@SuppressWarnings("deprecation")
	public Specification<Cart> build() {

		SearchCriteria seachCriteria = new SearchCriteria("id", "Like", search);

		Specification<Cart> where = null;

		// search
		if (!StringUtils.isEmpty(search)) {
			where = new CartSpecification(seachCriteria);
		}

		return where;
	}
}
