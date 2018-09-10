package com.xinhuanet.dao;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

public interface ContentTagTypeDao {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(XContentBuilder content, String id);

    DeleteResponse delete(String id);
}
