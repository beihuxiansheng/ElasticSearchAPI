package com.xinhuanet.service;

import com.xinhuanet.entity.ContentTag;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.util.List;

public interface ContentTagService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(ContentTag contentInfo);

    DeleteResponse delete(String id);

    ContentTag selectContentTag(String id);

    List<ContentTag> findAll();

    void importData();

    String importOne(String id);

    List<String> selectContentTagId(String contentid);

    List<ContentTag> getByContentid(String contentid);

    BulkByScrollResponse deletes(String contentid);
}
