package com.xinhuanet.service.impl;

import com.xinhuanet.dao.ContentArticleidDao;
import com.xinhuanet.entity.ContentArticleid;
import com.xinhuanet.mapper.ContentArticleidMapper;
import com.xinhuanet.service.ContentArticleidService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
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
public class ContentArticleidServiceImpl implements ContentArticleidService {

    private static final Logger log = LoggerFactory.getLogger(ContentArticleidServiceImpl.class);

    @Autowired
    private ContentArticleidDao contentArticleidDao;

    @Autowired
    private ContentArticleidMapper contentArticleidMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return contentArticleidDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(ContentArticleid contentArticleid) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (contentArticleid.getArticleid() != null) {
                content.field("articleid", contentArticleid.getArticleid());
            }
            if (contentArticleid.getContentid() != null) {
                content.field("contentid", contentArticleid.getContentid());
            }

            content.endObject();

            result = contentArticleidDao.addOrUpdate(content, contentArticleid.getId());
            log.info("contentArticleid===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("contentArticleid===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return contentArticleidDao.delete(id);
    }

    @Override
    public ContentArticleid selectContentDetail(String id) {
        return contentArticleidMapper.selectContentArticleId(id);
    }

    @Override
    public List<ContentArticleid> findAll() {
        return contentArticleidMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = contentArticleidMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_content_articleid", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(contentArticleidMapper.selectContentArticleidMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("contentArticleid===批量导入1000条数据成功!!!===当前" + count + "条");
            }
        }
        bulkRequest.get();
        log.info("contentArticleid===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = contentArticleidMapper.selectContentArticleidMap(id);
        bulkRequest.add(client.prepareIndex("tb_evt_content_articleid", "data")
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
    public String selectContentArticleIdID(String contentid) {
        return contentArticleidMapper.selectContentArticleIdID(contentid);
    }

    @Override
    public List<ContentArticleid> getByContentid(String contentid) {
        return contentArticleidDao.getByContentid(contentid);
    }
}
