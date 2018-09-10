package com.xinhuanet.dao.impl;

import com.xinhuanet.dao.ContentTagDao;
import com.xinhuanet.entity.ContentTag;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SpanQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class ContentTagDaoImpl implements ContentTagDao {

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return this.client.prepareGet("tb_evt_content_tag", "data", id).get();
    }

    @Override
    public IndexResponse addOrUpdate(XContentBuilder content, String id) {

        return this.client.prepareIndex("tb_evt_content_tag", "data")
                //id需要单独设置
                .setId(id)
                .setSource(content)
                .get();
    }

    @Override
    public DeleteResponse delete(String id) {
        return this.client.prepareDelete("tb_evt_content_tag", "data", id).get();
    }

    @Override
    public List<ContentTag> getByContentid(String contentid) {

        List<ContentTag> contentTags = new ArrayList<ContentTag>();

        SearchResponse searchResponse = this.client.prepareSearch("tb_evt_content_tag")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes("data")
                .setQuery(QueryBuilders.matchPhraseQuery("contentid", contentid))
                .get();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit s : hits1) {
            Map<String, Object> sourceAsMap = s.getSourceAsMap();
            ContentTag contentTag = new ContentTag();
            contentTag.setId(s.getId());
            if (sourceAsMap.get("contentid") != null) {
                contentTag.setContentid(sourceAsMap.get("contentid").toString());
            }
            if (sourceAsMap.get("tag_score") != null) {
                contentTag.setTag_score((Double) sourceAsMap.get("tag_score"));
            }
            if (sourceAsMap.get("tagname") != null) {
                contentTag.setTagname(sourceAsMap.get("tagname").toString());
            }
            if (sourceAsMap.get("tagtype") != null) {
                contentTag.setTagtype(sourceAsMap.get("tagtype").toString());
            }
            contentTags.add(contentTag);
        }

        return contentTags;
    }

    @Override
    public BulkByScrollResponse deletes(String contentid){
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("contentid", contentid))
                .source("tb_evt_content_tag")
                .get();
        return bulkByScrollResponse;
    }

}
