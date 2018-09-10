package com.xinhuanet.service;

import com.xinhuanet.entity.ContentTagType;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

public interface ContentTagTypeService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(ContentTagType contentInfo);

    DeleteResponse delete(String id);

    ContentTagType selectContentTagType(String id);

    List<ContentTagType> findAll();

    void importData();

    String importOne(String id);
}
