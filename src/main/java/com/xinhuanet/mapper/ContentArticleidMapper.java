package com.xinhuanet.mapper;

import com.xinhuanet.entity.ContentArticleid;

import java.util.List;
import java.util.Map;

public interface ContentArticleidMapper {

    ContentArticleid selectContentArticleId(String id);

    List<ContentArticleid> findAll();

    List<String> findAllId();

    Map<String, Object> selectContentArticleidMap(String id);
}
