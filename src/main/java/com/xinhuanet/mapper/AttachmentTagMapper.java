package com.xinhuanet.mapper;

import com.xinhuanet.entity.AttachmentTag;

import java.util.List;
import java.util.Map;

public interface AttachmentTagMapper {

    AttachmentTag selectAttachmentTag(String id);

    List<AttachmentTag> findAll();

    List<String> findAllId();

    Map<String, Object> selectAttachmentTagMap(String id);

    List<String> selectAttachmentTagId(String contentid);
}
