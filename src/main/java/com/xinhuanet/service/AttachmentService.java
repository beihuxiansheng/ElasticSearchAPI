package com.xinhuanet.service;

import com.xinhuanet.entity.Attachment;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

public interface AttachmentService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(Attachment attachment);

    DeleteResponse delete(String id);

    Attachment selectAttachment(String id);

    List<Attachment> findAll();

    void importData();

    String importOne(String id);
}
