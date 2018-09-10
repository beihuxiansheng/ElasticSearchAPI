package com.xinhuanet.service.impl;

import com.xinhuanet.dao.ContentTagTypeDao;
import com.xinhuanet.entity.ContentTagType;
import com.xinhuanet.mapper.ContentTagTypeMapper;
import com.xinhuanet.service.ContentTagTypeService;
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
public class ContentTagTypeServiceImpl implements ContentTagTypeService {

    private static final Logger log = LoggerFactory.getLogger(ContentTagTypeServiceImpl.class);

    @Autowired
    private ContentTagTypeDao contentTagTypeDao;

    @Autowired
    private ContentTagTypeMapper contentTagTypeMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return contentTagTypeDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(ContentTagType contentTagType) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (contentTagType.getType_code() != null) {
                content.field("type_code", contentTagType.getType_code());
            }
            if (contentTagType.getType_name() != null) {
                content.field("type_name", contentTagType.getType_name());
            }
            content.endObject();
            result = contentTagTypeDao.addOrUpdate(content, contentTagType.getId());
            log.info("contentTagType===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("contentTagType===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return contentTagTypeDao.delete(id);
    }

    @Override
    public ContentTagType selectContentTagType(String id) {
        return contentTagTypeMapper.selectContentTagType(id);
    }

    @Override
    public List<ContentTagType> findAll() {
        return contentTagTypeMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = contentTagTypeMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_content_tag_type", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(contentTagTypeMapper.selectContentTagTypeMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("contentTagType===批量导入1000条数据成功!!!===当前" + count + "条");
            }
        }
        bulkRequest.get();
        log.info("contentTagType===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = contentTagTypeMapper.selectContentTagTypeMap(id);
        bulkRequest.add(client.prepareIndex("tb_evt_content_tag_type", "data")
                //id需要单独设置
                .setId(id)
                .setSource(stringObjectMap)
        );
        bulkRequest.get();
        return "导入指定contentTagType的id为" + id + "的数据成功！！！";
    }
}
