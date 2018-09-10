package com.xinhuanet.dao;

import com.xinhuanet.entity.Attachment;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.util.List;

public interface AttachmentDao {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(XContentBuilder content, String id);

    DeleteResponse delete(String id);

    List<Attachment> getByContentid(String contentid);

    BulkByScrollResponse deletes(String contentid);
}
