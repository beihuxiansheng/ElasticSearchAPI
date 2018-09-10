package com.xinhuanet.dao.impl;

import com.xinhuanet.dao.AttachmentDao;
import com.xinhuanet.entity.Attachment;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class AttachmentDaoImpl implements AttachmentDao {

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return this.client.prepareGet("tb_evt_attachment", "data", id).get();
    }

    @Override
    public IndexResponse addOrUpdate(XContentBuilder content, String id) {

        return this.client.prepareIndex("tb_evt_attachment", "data")
                //id需要单独设置
                .setId(id)
                .setSource(content)
                .get();
    }

    @Override
    public DeleteResponse delete(String id) {
        return this.client.prepareDelete("tb_evt_attachment", "data", id).get();
    }

    @Override
    public List<Attachment> getByContentid(String contentid) {

        List<Attachment> attachments = new ArrayList<>();

        SearchResponse searchResponse = this.client.prepareSearch("tb_evt_attachment")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setTypes("data")
                .setQuery(QueryBuilders.matchPhraseQuery("contentid", contentid))
                .get();

        SearchHits hits = searchResponse.getHits();
        SearchHit[] hits1 = hits.getHits();
        for (SearchHit s : hits1) {
            Map<String, Object> sourceAsMap = s.getSourceAsMap();
            Attachment attachment = new Attachment();
            attachment.setId(s.getId());
            if (sourceAsMap.get("attpath") != null) {
                attachment.setAttpath(sourceAsMap.get("attpath").toString());
            }
            if (sourceAsMap.get("atttype") != null) {
                attachment.setAtttype(sourceAsMap.get("atttype").toString());
            }
            if (sourceAsMap.get("contentid") != null) {
                attachment.setContentid(sourceAsMap.get("contentid").toString());
            }
            if (sourceAsMap.get("description") != null) {
                attachment.setDescription(sourceAsMap.get("description").toString());
            }
            if (sourceAsMap.get("extraname") != null) {
                attachment.setExtraname(sourceAsMap.get("extraname").toString());
            }
            if (sourceAsMap.get("libid") != null) {
                attachment.setLibid((Integer) sourceAsMap.get("libid"));
            }
            if (sourceAsMap.get("title") != null) {
                attachment.setTitle(sourceAsMap.get("title").toString());
            }
            if (sourceAsMap.get("titlepicsort") != null) {
                attachment.setTitlepicsort(sourceAsMap.get("titlepicsort").toString());
            }
            if (sourceAsMap.get("type") != null) {
                attachment.setType((Integer) sourceAsMap.get("type"));
            }

            attachments.add(attachment);
        }

        return attachments;
    }

    @Override
    public BulkByScrollResponse deletes(String contentid){
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("contentid", contentid))
                .source("tb_evt_attachment")
                .get();
        return bulkByScrollResponse;
    }


}
