package com.redhat.jp.labs.sample.backend.service;

import java.util.List;

import com.redhat.jp.labs.sample.backend.domain.ProductBacklogItem;
import com.redhat.jp.labs.sample.backend.repository.ProductBacklogItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBacklogService {

    private static final int DEFAULT_LIMIT = 20;

    @Autowired
    ProductBacklogItemRepository productBacklogItemRepository;

    public List<ProductBacklogItem> getBacklog(Integer limit, Integer offset) {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }
        if (offset == null) {
            offset = 0;
        }
        return productBacklogItemRepository.find(limit, offset);
    }
}