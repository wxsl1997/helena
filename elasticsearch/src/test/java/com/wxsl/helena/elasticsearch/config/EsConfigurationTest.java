package com.wxsl.helena.elasticsearch.config;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wxsl.helena.elasticsearch.enumeration.TradeRateResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class EsConfigurationTest {

    @Autowired
    private RestHighLevelClient esClient;

    private static final String index = "trade_rate";

    private static final String id = "1";

    @Test
    void indexRequest() throws IOException {
        IndexRequest request = new IndexRequest(index).id(id)
                .source(contentBuilder());
        IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
    }

    @Test
    void getRequest() throws IOException {
        System.out.println(esClient.get(new GetRequest(index).id(id), RequestOptions.DEFAULT));
    }

    @Test
    void updateRequest() throws IOException {

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("result", TradeRateResult.BAD)
                .endObject();

        UpdateRequest request = new UpdateRequest(index, id)
                .doc(builder);

        if (exists()) {
            esClient.update(request, RequestOptions.DEFAULT);
        }
    }

    private boolean exists() throws IOException {
        return esClient.exists(new GetRequest(index).id(id), RequestOptions.DEFAULT);
    }

    @Test
    void deleteRequest() throws IOException {
        esClient.delete(new DeleteRequest(index, id), RequestOptions.DEFAULT);
    }

    private XContentBuilder contentBuilder() throws IOException {
        return XContentFactory.jsonBuilder()
                .startObject()
                .field("tid", 2020120101L)
                .array("oid", 2020120101L)
                .field("nick", "文修森啦")
                .timeField("created", "2020-12-01 08:00:00")
                .field("numIid", 100001L)
                .field("content", "我以为恐怕灼伤而躲过火焰，却不料在在海水中惨遭灭顶。")
                .field("result", TradeRateResult.GOOD)
                .endObject();
    }
}