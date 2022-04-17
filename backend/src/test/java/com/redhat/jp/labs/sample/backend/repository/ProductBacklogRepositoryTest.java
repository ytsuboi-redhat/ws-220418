package com.redhat.jp.labs.sample.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.redhat.jp.labs.sample.backend.CsvDataSetLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@Transactional
public class ProductBacklogRepositoryTest {

  @Autowired
  ProductBacklogItemRepository repository;

  @Test
  @DatabaseSetup("classpath:/dbunit/ut/ProductBacklogRepository/init10/")
  @ExpectedDatabase("classpath:/dbunit/ut/ProductBacklogRepository/init10/")
  public void find_when10listed_thenSelect2to6() {
    assertThat(repository.find(5, 1)).extracting("itemId").containsExactly(2L,3L,4L,5L,6L);
  }
}
