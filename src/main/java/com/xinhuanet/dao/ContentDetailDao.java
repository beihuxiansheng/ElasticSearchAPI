package com.xinhuanet.dao;

import com.xinhuanet.entity.ContentDetail;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.List;

public interface ContentDetailDao {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(XContentBuilder content, String id);

    DeleteResponse delete(String id);

    List<ContentDetail> getByContentid(String contentid);
}
