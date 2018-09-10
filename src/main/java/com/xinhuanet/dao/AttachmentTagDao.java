package com.xinhuanet.dao;

import com.xinhuanet.entity.AttachmentTag;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.util.List;

public interface AttachmentTagDao {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(XContentBuilder content, String id);

    DeleteResponse delete(String id);

    List<AttachmentTag> getByContentid(String contentid);

    BulkByScrollResponse deletes(String contentid);
}
