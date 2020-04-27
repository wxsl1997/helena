package com.wxsl.helena.elasticsearch.base;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEsRepository<T, ID> extends ElasticsearchRepository<T, ID> {
}
