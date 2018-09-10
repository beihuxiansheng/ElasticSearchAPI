package com.xinhuanet.service;

import com.xinhuanet.entity.ContentDetail;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

public interface ContentDetailService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(ContentDetail contentDetail);

    DeleteResponse delete(String id);

    ContentDetail selectContentDetail(String id);

    List<ContentDetail> findAll();

    void importData();

    String importOne(String id);

    String selectContentDetailId(String contentid);

    List<ContentDetail> getByContentid(String contentid);
}
