package com.redhat.jp.labs.sample.backend.repository;

import com.redhat.jp.labs.sample.backend.domain.ProductBacklogItem;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductBacklogItemRepository extends CrudRepository<ProductBacklogItem, Long> {

    @Query("select * from product_backlog_item " +
            "order by item_id " +
            "limit :limit offset :offset")
    public List<ProductBacklogItem> find(int limit, int offset);
}