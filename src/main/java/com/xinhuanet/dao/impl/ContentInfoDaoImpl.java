package com.xinhuanet.dao.impl;

import com.xinhuanet.dao.ContentInfoDao;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class ContentInfoDaoImpl implements ContentInfoDao {

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return this.client.prepareGet("tb_evt_contentinfo", "data", id).get();
    }

    @Override
    public IndexResponse addOrUpdate(XContentBuilder content, String id) {

        return this.client.prepareIndex("tb_evt_contentinfo", "data")
                //id需要单独设置
                .setId(id)
                .setSource(content)
                .get();
    }

    @Override
    public DeleteResponse delete(String id) {
        return this.client.prepareDelete("tb_evt_contentinfo", "data", id).get();
    }
}
