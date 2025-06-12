package com.vti.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.dto.filter.CartItemFilter;
import com.vti.entity.CartItem;

public class CartItemSpecificationBuilder {

	private CartItemFilter filter;
	private String search;

	public CartItemSpecificationBuilder(CartItemFilter filter, String search) {
		this.filter = filter;
		this.search = search;
	}

	@SuppressWarnings("deprecation")
	public Specification<CartItem> build() {

		SearchCriteria seachCriteria = new SearchCriteria("id", "Like", search);
		SearchCriteria minQuantityCriteria = new SearchCriteria("price", ">=", filter.getMinQuantity());
		SearchCriteria maxQuantityCriteria = new SearchCriteria("price", "<=", filter.getMaxQuantity());

		Specification<CartItem> where = null;

		// search
		if (!StringUtils.isEmpty(search)) {
			where = new CartItemSpecification(seachCriteria);
		}

		// min filter
		if (filter.getMinQuantity() != 0) {
			if (where != null) {
				where = where.and(new CartItemSpecification(minQuantityCriteria));
			} else {
				where = new CartItemSpecification(minQuantityCriteria);
			}
		}

		// max filter
		if (filter.getMaxQuantity() != 0) {
			if (where != null) {
				where = where.and(new CartItemSpecification(maxQuantityCriteria));
			} else {
				where = new CartItemSpecification(maxQuantityCriteria);
			}
		}

		return where;
	}
}
