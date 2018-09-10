package com.xinhuanet.mapper;

import com.xinhuanet.entity.Attachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AttachmentMapper {

    Attachment selectAttachment(@Param(value = "id") String id);

    List<Attachment> findAll();

    List<String> findAllId();

    Map<String, Object> selectAttachmentMap(String id);

    List<String> selectAttachmentId(String contentid);
}
