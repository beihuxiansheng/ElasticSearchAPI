package com.xinhuanet.mapper;

import com.xinhuanet.entity.ContentDetail;

import java.util.List;
import java.util.Map;

public interface ContentDetailMapper {

    ContentDetail selectContentDetail(String id);

    List<ContentDetail> findAll();

    List<String> findAllId();

    Map<String, Object> selectContentDetailMap(String id);

    String selectContentDetailId(String contentid);
}
