package com.fpt.specification;

import com.fpt.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecificationBuilder {

	private final String search;
	private final Integer status;

	public UserSpecificationBuilder(String search, Integer status) {
		this.search = search;
		this.status = status;
	}

	public Specification<User> build() {
		Specification<User> statusSpec = null;
		Specification<User> searchSpec = null;

		// filter status
		if (status != null) {
			SearchCriteria statusCriteria = new SearchCriteria("status", "Equal", status);
			statusSpec = new UserSpecification(statusCriteria);
		}

		// search field free text
		if (!StringUtils.isEmpty(search)) {
			String[] fields = {"firstName", "lastName", "email", "userName", "phoneNumber"};
			for (String field : fields) {
				SearchCriteria criteria = new SearchCriteria(field, "Like", search);
				Specification<User> spec = new UserSpecification(criteria);
				searchSpec = (searchSpec == null) ? Specification.where(spec) : searchSpec.or(spec);
			}
		}

		// no params, so default
		if (searchSpec == null && statusSpec == null) {
			return null;
		}

		// combine them
		if (searchSpec != null && statusSpec != null) {
			return searchSpec.and(statusSpec);
		} else {
			return (searchSpec != null) ? searchSpec : statusSpec;
		}
	}
}
