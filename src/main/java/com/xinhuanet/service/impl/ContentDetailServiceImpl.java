package com.xinhuanet.service.impl;

import com.xinhuanet.dao.ContentDetailDao;
import com.xinhuanet.entity.ContentDetail;
import com.xinhuanet.mapper.ContentDetailMapper;
import com.xinhuanet.service.ContentDetailService;
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
public class ContentDetailServiceImpl implements ContentDetailService {

    private static final Logger log = LoggerFactory.getLogger(ContentDetailServiceImpl.class);

    @Autowired
    private ContentDetailDao contentDetailDao;

    @Autowired
    private ContentDetailMapper contentDetailMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return contentDetailDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(ContentDetail contentDetail) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (contentDetail.getLibid() != null) {
                content.field("libid", contentDetail.getLibid());
            }
            if (contentDetail.getContent() != null) {
                content.field("content", contentDetail.getContent());
            }
            if (contentDetail.getContentid() != null) {
                content.field("contentid", contentDetail.getContentid());
            }

            content.endObject();

            result = contentDetailDao.addOrUpdate(content, contentDetail.getId());
            log.info("contentDetail===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("contentDetail===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return contentDetailDao.delete(id);
    }

    @Override
    public ContentDetail selectContentDetail(String id) {
        return contentDetailMapper.selectContentDetail(id);
    }

    @Override
    public List<ContentDetail> findAll() {
        return contentDetailMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = contentDetailMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_content_detail", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(contentDetailMapper.selectContentDetailMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("contentDetail===批量导入1000条数据成功!!!===当前"+count+"条");
            }
        }
        bulkRequest.get();
        log.info("contentDetail===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = contentDetailMapper.selectContentDetailMap(id);
        bulkRequest.add(client.prepareIndex("tb_evt_content_detail", "data")
                //id需要单独设置
                .setId(id)
                .setSource(stringObjectMap)
        );
        bulkRequest.get();
        return "导入指定contentDetail的id为" + id + "的数据成功！！！";
    }
}
