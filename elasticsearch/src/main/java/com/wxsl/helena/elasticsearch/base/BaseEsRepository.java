package com.wxsl.helena.elasticsearch.base;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseEsRepository<T, ID> {

    @Autowired
    private RestHighLevelClient esClient;

    protected RestHighLevelClient esClient() {
        return esClient;
    }
}
