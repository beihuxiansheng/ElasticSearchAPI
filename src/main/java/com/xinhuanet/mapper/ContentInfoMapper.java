package com.xinhuanet.mapper;

import com.xinhuanet.entity.ContentInfo;

import java.util.List;
import java.util.Map;

public interface ContentInfoMapper {

    ContentInfo selectContentInfo(String id);

    List<ContentInfo> findAll();

    List<String> findAllId();

    Map<String,Object> selectContentInfoMap(String id);
}
