package com.fpt.specification;

import com.fpt.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecificationBuilder {

	private String search;

	public UserSpecificationBuilder(String search) {
		this.search = search;
	}
	public Specification<User> build() {

		SearchCriteria searchCriteria = new SearchCriteria("lastName", "Like", search);
		Specification<User> where = null;

		// search
		if (!StringUtils.isEmpty(search)) {
			where = new UserSpecification(searchCriteria);
		}

		return where;
	}
}