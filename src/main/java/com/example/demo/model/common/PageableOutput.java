package com.example.demo.model.common;

import com.example.demo.entity.OrderEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "pageable list of records")
public class PageableOutput<T> {

    @Schema(description = "data in the list")
    private List<T> items;

    @Schema(description = "total items per page")
    private int pageSize;

    @Schema(description = "current page no.")
    private int pageIndex;

    @Schema(description = "the number of items according to query criteria")
    private int totalItems;

    @Schema(description = "the number of pages")
    private int totalPages;

    @Schema(description = "would the list reach to the tail.")
    private boolean hasNextPage;

    @Schema(description = "would the list reach to the head.")
    private boolean hasPreviousPage;

    public PageableOutput(PanacheQuery<?> panacheQuery, List<T> items, int pageSize, int pageIndex) {
        this.items = items;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalItems = (int) panacheQuery.count();
        this.totalPages = panacheQuery.pageCount();
        this.hasNextPage = panacheQuery.hasNextPage();
        this.hasPreviousPage = panacheQuery.hasPreviousPage();
    }
}
