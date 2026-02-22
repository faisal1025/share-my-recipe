package com.airtribe.ShareMyRecipe.dto;

import java.util.List;

public record PageResponse<T>(int totalPages, boolean isFirst, boolean isLast, boolean hasNext, boolean hasPerv,
                              List<T> content, int pageNo, int pageSize, Long totalElements) {
}
