package com.xinhuanet.mapper;

import com.xinhuanet.entity.ContentTag;

import java.util.List;
import java.util.Map;

public interface ContentTagMapper {

    ContentTag selectContentTag(String id);

    List<ContentTag> findAll();

    List<String> findAllId();

    Map<String, Object> selectContentTagMap(String id);

    List<String> selectContentTagId(String contentid);
}
