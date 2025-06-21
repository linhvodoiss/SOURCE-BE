package com.fpt.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
	private Integer id;
	private String name;
	private String slug;
	private Integer order;
	private Boolean isActive;
	private Integer versionId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
