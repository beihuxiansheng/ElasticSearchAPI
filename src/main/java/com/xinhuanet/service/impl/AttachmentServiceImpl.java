package com.xinhuanet.service.impl;

import com.xinhuanet.dao.AttachmentDao;
import com.xinhuanet.entity.Attachment;
import com.xinhuanet.mapper.AttachmentMapper;
import com.xinhuanet.service.AttachmentService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return attachmentDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(Attachment attachment) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("libid", attachment.getLibid())
                    .field("title", attachment.getTitle())
                    .field("description", attachment.getDescription())
                    .field("attpath", attachment.getAttpath())
                    .field("type", attachment.getType())
                    .field("extraname", attachment.getExtraname())
                    .field("atttype", attachment.getAtttype())
                    .field("titlepicsort", attachment.getTitlepicsort())
                    .field("contentid", attachment.getContentid())
                    .endObject();

            result = attachmentDao.addOrUpdate(content, attachment.getId());
            log.info("attachment===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("attachment===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return attachmentDao.delete(id);
    }

    @Override
    public Attachment selectAttachment(String id) {
        return attachmentMapper.selectAttachment(id);
    }

    @Override
    public List<Attachment> findAll() {
        return attachmentMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = attachmentMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_attachment", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(attachmentMapper.selectAttachmentMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("attachment===批量导入1000条数据成功!!!===当前" + count + "条");
            }
        }
        bulkRequest.get();
        log.info("attachment===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = attachmentMapper.selectAttachmentMap(id);
        bulkRequest.add(client.prepareIndex("tb_evt_attachment", "data")
                //id需要单独设置
                .setId(id)
                .setSource(stringObjectMap)
        );
        bulkRequest.get();
        return "导入指定attachment的id为" + id + "的数据成功！！！";
    }
}
