package com.xinhuanet.service;

import com.xinhuanet.entity.AttachmentTag;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;

import java.util.List;

public interface AttachmentTagService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(AttachmentTag contentInfo);

    DeleteResponse delete(String id);

    AttachmentTag selectAttachmentTag(String id);

    List<AttachmentTag> findAll();

    void importData();

    String importOne(String id);

    List<String> selectAttachmentTagId(String contentid);

    List<AttachmentTag> getByContentid(String contentid);

    BulkByScrollResponse deletes(String contentid);
}
