package com.xinhuanet.dao.impl;

import com.xinhuanet.dao.ContentArticleidDao;
import com.xinhuanet.entity.ContentArticleid;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class ContentArticleidDaoImpl implements ContentArticleidDao {

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return this.client.prepareGet("tb_evt_content_articleid", "data", id).get();
    }

    @Override
    public IndexResponse addOrUpdate(XContentBuilder content, String id) {

        return this.client.prepareIndex("tb_evt_content_articleid", "data")
                //id需要单独设置
                .setId(id)
                .setSource(content)
                .get();
    }

    @Override
    public DeleteResponse delete(String id) {
        return this.client.prepareDelete("tb_evt_content_articleid", "data", id).get();
    }

    @Override
    public List<ContentArticleid> getByContentid(String contentid){

        List<ContentArticleid> ContentArticleids = new ArrayList<ContentArticleid>();

        SearchResponse searchResponse = this.client.prepareSearch("tb_evt_content_articleid")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes("data")
                .setQuery(QueryBuilders.matchPhraseQuery("contentid", contentid))
                .get();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit s : hits1) {
            Map<String, Object> sourceAsMap = s.getSourceAsMap();
            ContentArticleid contentArticleid = new ContentArticleid();
            contentArticleid.setId(s.getId());
            if(sourceAsMap.get("articleid")!=null) {
                contentArticleid.setArticleid(sourceAsMap.get("articleid").toString());
            }
            if(sourceAsMap.get("contentid")!=null) {
                contentArticleid.setContentid(sourceAsMap.get("contentid").toString());
            }
            ContentArticleids.add(contentArticleid);
        }

        return ContentArticleids;
    }

}
