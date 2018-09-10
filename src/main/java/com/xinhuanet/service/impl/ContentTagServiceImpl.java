package com.xinhuanet.service.impl;

import com.xinhuanet.dao.ContentTagDao;
import com.xinhuanet.entity.ContentTag;
import com.xinhuanet.mapper.ContentTagMapper;
import com.xinhuanet.service.ContentTagService;
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
public class ContentTagServiceImpl implements ContentTagService {

    private static final Logger log = LoggerFactory.getLogger(ContentTagServiceImpl.class);

    @Autowired
    private ContentTagDao contentTagDao;

    @Autowired
    private ContentTagMapper contentTagMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return contentTagDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(ContentTag contentTag) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (contentTag.getContentid() != null) {
                content.field("contentid", contentTag.getContentid());
            }
            if (contentTag.getTag_score() != null) {
                content.field("tag_score", contentTag.getTag_score());
            }
            if (contentTag.getTagtype() != null) {
                content.field("tagtype", contentTag.getTagtype());
            }
            if (contentTag.getTagname() != null) {
                content.field("tagname", contentTag.getTagname());
            }
            content.endObject();
            result = contentTagDao.addOrUpdate(content, contentTag.getId());
            log.info("contentTag===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("contentTag===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return contentTagDao.delete(id);
    }

    @Override
    public ContentTag selectContentTag(String id) {
        return contentTagMapper.selectContentTag(id);
    }

    @Override
    public List<ContentTag> findAll() {
        return contentTagMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = contentTagMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_content_tag", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(contentTagMapper.selectContentTagMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("contentTag===批量导入1000条数据成功!!!===当前" + count + "条");
            }
        }
        bulkRequest.get();
        log.info("contentTag===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = contentTagMapper.selectContentTagMap(id);
        bulkRequest.add(client.prepareIndex("tb_evt_content_tag", "data")
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
    public List<String> selectContentTagId(String contentid) {
        return contentTagMapper.selectContentTagId(contentid);
    }

    @Override
    public List<ContentTag> getByContentid(String contentid) {
        return contentTagDao.getByContentid(contentid);
    }

    @Override
    public BulkByScrollResponse deletes(String contentid) {
        return contentTagDao.deletes(contentid);
    }
}
