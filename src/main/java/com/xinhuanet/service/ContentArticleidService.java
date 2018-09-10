package com.xinhuanet.service;

import com.xinhuanet.entity.ContentArticleid;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

public interface ContentArticleidService {
    GetResponse getById(String id);

    IndexResponse addOrUpdate(ContentArticleid contentArticleid);

    DeleteResponse delete(String id);

    ContentArticleid selectContentDetail(String id);

    List<ContentArticleid> findAll();

    void importData();

    String importOne(String id);

    String selectContentArticleIdID(String contentid);

    List<ContentArticleid> getByContentid(String contentid);
}
