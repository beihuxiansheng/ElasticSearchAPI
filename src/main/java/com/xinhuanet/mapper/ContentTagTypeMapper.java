package com.xinhuanet.mapper;

import com.xinhuanet.entity.ContentTagType;

import java.util.List;
import java.util.Map;

public interface ContentTagTypeMapper {

    ContentTagType selectContentTagType(String id);

    List<ContentTagType> findAll();

    List<String> findAllId();

    Map<String, Object> selectContentTagTypeMap(String id);
}
