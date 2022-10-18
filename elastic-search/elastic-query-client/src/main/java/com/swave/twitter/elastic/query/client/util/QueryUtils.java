package com.swave.twitter.elastic.query.client.util;

import org.elasticsearch.index.query.BoolQueryBuilder;
import com.swave.twitter.elastic.config.model.index.IndexModel;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.Collections;

@Component
public class QueryUtils<T extends IndexModel> {

    public Query getSearchQueryById(String id) {
        return new NativeSearchQueryBuilder()
                .withIds(Collections.singleton(id))
                .build();
    }

    public Query getSearchQueryWithFieldText(String field, String text) {
        return new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .must(QueryBuilders.matchQuery(field, text)))
                .build();
    }

    public Query getSearchForAll() {
        return new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .must(QueryBuilders.matchAllQuery()))
                .build();
    }
}
