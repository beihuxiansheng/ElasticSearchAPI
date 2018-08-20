package com.xinhuanet.service;

import com.xinhuanet.entity.ContentInfo;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

public interface ContentInfoService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(ContentInfo contentInfo);

    DeleteResponse delete(String id);

    ContentInfo selectContentInfo(String id);

    List<ContentInfo> findAll();

    void importData();

    String importOne(String id);
}
