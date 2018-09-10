package com.xinhuanet.service.impl;

import com.xinhuanet.dao.AttachmentTagDao;
import com.xinhuanet.entity.AttachmentTag;
import com.xinhuanet.mapper.AttachmentTagMapper;
import com.xinhuanet.service.AttachmentTagService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Primary
@Service
@Transactional
public class AttachmentTagServiceImpl implements AttachmentTagService {

    private static final Logger log = LoggerFactory.getLogger(AttachmentTagServiceImpl.class);

    @Autowired
    private AttachmentTagDao attachmentTagDao;

    @Autowired
    private AttachmentTagMapper attachmentTagMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return attachmentTagDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(AttachmentTag attachmentTag) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (attachmentTag.getContentid() != null) {
                content.field("contentid", attachmentTag.getContentid());
            }
            if (attachmentTag.getTag_score() != null) {
                content.field("tag_score", attachmentTag.getTag_score());
            }
            if (attachmentTag.getTagtype() != null) {
                content.field("tagtype", attachmentTag.getTagtype());
            }
            if (attachmentTag.getTagname() != null) {
                content.field("tagname", attachmentTag.getTagname());
            }
            content.endObject();
            result = attachmentTagDao.addOrUpdate(content, attachmentTag.getId());
            log.info("attachmentTag===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("attachmentTag===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return attachmentTagDao.delete(id);
    }

    @Override
    public AttachmentTag selectAttachmentTag(String id) {
        return attachmentTagMapper.selectAttachmentTag(id);
    }

    @Override
    public List<AttachmentTag> findAll() {
        return attachmentTagMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = attachmentTagMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_attachment_tag", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(attachmentTagMapper.selectAttachmentTagMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("attachmentTag===批量导入1000条数据成功!!!===当前" + count + "条");
            }
        }
        bulkRequest.get();
        log.info("attachmentTag===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = attachmentTagMapper.selectAttachmentTagMap(id);
//        stringObjectMap.put("tag_score",((BigDecimal)stringObjectMap.get("tag_score")).doubleValue());
        bulkRequest.add(client.prepareIndex("tb_evt_attachment_tag", "data")
                //id需要单独设置
                .setId(id)
                .setSource(stringObjectMap)
        );
        BulkResponse bulkItemResponses = bulkRequest.get();
        if (bulkItemResponses.hasFailures()) {
            return "false";
        } else {
            return "true";
        }
    }

    @Override
    public List<String> selectAttachmentTagId(String contentid) {
        return attachmentTagMapper.selectAttachmentTagId(contentid);
    }

    @Override
    public List<AttachmentTag> getByContentid(String contentid) {
        return attachmentTagDao.getByContentid(contentid);
    }

    @Override
    public BulkByScrollResponse deletes(String contentid) {
        return attachmentTagDao.deletes(contentid);
    }
}
