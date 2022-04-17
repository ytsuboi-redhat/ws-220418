package com.redhat.jp.labs.sample.backend.controller;

import com.redhat.jp.labs.sample.backend.domain.ProductBacklogItem;
import com.redhat.jp.labs.sample.backend.service.ProductBacklogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/backlog")
public class ProductBacklogController {

    @Autowired
    ProductBacklogService productBacklogService;

    @GetMapping
    public Iterable<ProductBacklogItem> getBacklogs(@RequestParam(name = "limit", required = false) Integer limit, @RequestParam(name = "offset", required = false) Integer offset) {
        return productBacklogService.getBacklog(limit, offset);
    }
    
}
