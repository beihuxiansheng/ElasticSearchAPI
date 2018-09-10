package com.xinhuanet.dao.impl;

import com.xinhuanet.dao.ContentDetailDao;
import com.xinhuanet.entity.ContentDetail;
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
public class ContentDetailDaoImpl implements ContentDetailDao {

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return this.client.prepareGet("tb_evt_content_detail", "data", id).get();
    }

    @Override
    public IndexResponse addOrUpdate(XContentBuilder content, String id) {

        return this.client.prepareIndex("tb_evt_content_detail", "data")
                //id需要单独设置
                .setId(id)
                .setSource(content)
                .get();
    }

    @Override
    public DeleteResponse delete(String id) {
        return this.client.prepareDelete("tb_evt_content_detail", "data", id).get();
    }

    @Override
    public List<ContentDetail> getByContentid(String contentid){

        List<ContentDetail> contentDetails = new ArrayList<>();

        SearchResponse searchResponse = this.client.prepareSearch("tb_evt_content_detail")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes("data")
                .setQuery(QueryBuilders.matchPhraseQuery("contentid", contentid))
                .get();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit s : hits1) {
            Map<String, Object> sourceAsMap = s.getSourceAsMap();
            ContentDetail contentDetail = new ContentDetail();
            contentDetail.setId(s.getId());
            if(sourceAsMap.get("contentid")!=null){
                contentDetail.setContentid(sourceAsMap.get("contentid").toString());
            }
            if(sourceAsMap.get("content")!=null){
                contentDetail.setContent(sourceAsMap.get("content").toString());
            }
            if(sourceAsMap.get("libid")!=null){
                contentDetail.setLibid((Integer) sourceAsMap.get("libid"));
            }
            contentDetails.add(contentDetail);
        }

        return contentDetails;
    }
}
