package com.redhat.jp.labs.sample.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.redhat.jp.labs.sample.backend.domain.ProductBacklogItem;
import com.redhat.jp.labs.sample.backend.repository.ProductBacklogItemRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ProductBacklogServiceTest {

    @Mock
    private ProductBacklogItemRepository productBacklogItemRepository;

    @InjectMocks
    private ProductBacklogService productBacklogService;

    @Test
    public void getBacklog_whenLimitOffsetSpecified_thenApplyAsIs() {
        ProductBacklogItem expected1 = new ProductBacklogItem(1L, "name1", "desc1", "1", "memo1");
        ProductBacklogItem expected2 = new ProductBacklogItem(2L, "name2", "desc2", "2", "memo2");
        List<ProductBacklogItem> list = Lists.newArrayList(expected1, expected2);

        doReturn(list).when(productBacklogItemRepository).find(100, 200);

        assertThat(productBacklogService.getBacklog(100, 200)).containsExactly(expected1, expected2);
    }

    @Test
    public void getBacklog_whenLimitIsNull_thenApplyDefault() {
        ProductBacklogItem expected = new ProductBacklogItem(3L, "name", "desc", "3", "memo");

        int limitDefault = 20;
        doReturn(Collections.singletonList(expected)).when(productBacklogItemRepository).find(limitDefault, 5);

        assertThat(productBacklogService.getBacklog(null, 5)).contains(expected, atIndex(0));
    }

    @Test
    public void getBacklog_whenOffsetIsNull_thenApplyZero() {
        ProductBacklogItem expected = new ProductBacklogItem(4L, "name", "desc", "4", "memo");

        doReturn(Collections.singletonList(expected)).when(productBacklogItemRepository).find(10, 0);

        assertThat(productBacklogService.getBacklog(10, null)).contains(expected, atIndex(0));
    }

}