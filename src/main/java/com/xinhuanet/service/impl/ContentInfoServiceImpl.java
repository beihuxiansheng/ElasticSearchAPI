package com.xinhuanet.service.impl;

import com.xinhuanet.dao.ContentInfoDao;
import com.xinhuanet.entity.ContentInfo;
import com.xinhuanet.mapper.ContentInfoMapper;
import com.xinhuanet.service.ContentInfoService;
import com.xinhuanet.tool.ObjectUtils;
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
public class ContentInfoServiceImpl implements ContentInfoService {

    private static final Logger log = LoggerFactory.getLogger(ContentInfoServiceImpl.class);

    @Autowired
    private ContentInfoDao contentInfoDao;

    @Autowired
    private ContentInfoMapper contentInfoMapper;

    @Autowired
    private TransportClient client;

    @Override
    public GetResponse getById(String id) {
        return contentInfoDao.getById(id);
    }

    @Override
    public IndexResponse addOrUpdate(ContentInfo contentInfo) {

        IndexResponse result = null;

        try {
            XContentBuilder content = XContentFactory.jsonBuilder()
                    .startObject();
            if (contentInfo.getLibid() != null) {
                content.field("libid", contentInfo.getLibid());
            }
            if (contentInfo.getIntrotitle() != null) {
                content.field("introtitle", contentInfo.getIntrotitle());
            }
            if (contentInfo.getTitle() != null) {
                content.field("title", contentInfo.getTitle());
            }
            if (contentInfo.getSubtitle() != null) {
                content.field("subtitle", contentInfo.getSubtitle());
            }
            if (contentInfo.getAuthor() != null) {
                content.field("author", contentInfo.getAuthor());
            }
            if (contentInfo.getEditor() != null) {
                content.field("editor", contentInfo.getEditor());
            }
            if (contentInfo.getLiability() != null) {
                content.field("liability", contentInfo.getLiability());
            }
            if (contentInfo.getSubscriber() != null) {
                content.field("subscriber", contentInfo.getSubscriber());
            }
            if (contentInfo.getSourcename() != null) {
                content.field("sourcename", contentInfo.getSourcename());
            }
            if (contentInfo.getSourceurl() != null) {
                content.field("sourceurl", contentInfo.getSourceurl());
            }
            if (contentInfo.getMultiattach() != null) {
                content.field("multiattach", contentInfo.getMultiattach());
            }
            if (contentInfo.getUrl() != null) {
                content.field("url", contentInfo.getUrl());
            }
            if (contentInfo.getKeyword() != null) {
                content.field("keyword", contentInfo.getKeyword());
            }
            if (contentInfo.getCategory() != null) {
                content.field("category", contentInfo.getCategory());
            }
            if (contentInfo.getArea() != null) {
                content.field("area", contentInfo.getArea());
            }
            if (contentInfo.getSabstract() != null) {
                content.field("sabstract", contentInfo.getSabstract());
            }
            if (contentInfo.getAutoabstract() != null) {
                content.field("autoabstract", contentInfo.getAutoabstract());
            }
            if (contentInfo.getCopyright() != null) {
                content.field("copyright", contentInfo.getCopyright());
            }
            if (contentInfo.getDeleteflag() != null) {
                content.field("deleteflag", contentInfo.getDeleteflag());
            }
            if (contentInfo.getLanguageid() != null) {
                content.field("languageid", contentInfo.getLanguageid());
            }
            if (contentInfo.getContenttype() != null) {
                content.field("contenttype", contentInfo.getContenttype());
            }
            if (contentInfo.getOrganization() != null) {
                content.field("organization", contentInfo.getOrganization());
            }
            if (contentInfo.getPersonname() != null) {
                content.field("personname", contentInfo.getPersonname());
            }
            if (contentInfo.getImportid() != null) {
                content.field("importid", contentInfo.getImportid());
            }

            if (contentInfo.getPubtime() != null) {
                content.field("pubtime", ObjectUtils.DateTimeFormat(contentInfo.getPubtime()));
            }
            if (contentInfo.getImporttime() != null) {
                content.field("importtime", ObjectUtils.DateTimeFormat(contentInfo.getImporttime()));
            }
            if (contentInfo.getLastmodify() != null) {
                content.field("lastmodify", ObjectUtils.DateTimeFormat(contentInfo.getLastmodify()));
            }
            if (contentInfo.getCreatetime() != null) {
                content.field("createtime", ObjectUtils.DateTimeFormat(contentInfo.getCreatetime()));
            }
            content.endObject();
            result = contentInfoDao.addOrUpdate(content, contentInfo.getId());
            log.info("contentinfo===新增/修改id为" + result.getId() + "的数据成功!===");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("contentinfo===新增/修改id为" + result.getId() + "的数据失败!===");
            return null;
        }
    }

    @Override
    public DeleteResponse delete(String id) {
        return contentInfoDao.delete(id);
    }

    @Override
    public ContentInfo selectContentInfo(String id) {
        return contentInfoMapper.selectContentInfo(id);
    }

    @Override
    public List<ContentInfo> findAll() {
        return contentInfoMapper.findAll();
    }

    @Override
    public void importData() {
        List<String> allId = contentInfoMapper.findAllId();
        int count = 0;
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 0; i < allId.size(); i++) {
            bulkRequest.add(client.prepareIndex("tb_evt_contentinfo", "data")
                    //id需要单独设置
                    .setId(allId.get(i))
                    .setSource(contentInfoMapper.selectContentInfoMap(allId.get(i)))
            );
            count++;
            System.out.println(count);
            if (count % 1000 == 0) {
                bulkRequest.get();
                log.info("contentInfo===批量导入1000条数据成功!!!===当前"+count+"条");
            }
        }
        bulkRequest.get();
        log.info("contentInfo===批量导入数据成功!!!===");
    }

    @Override
    public String importOne(String id) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        Map<String, Object> stringObjectMap = contentInfoMapper.selectContentInfoMap(id);
        System.out.println(stringObjectMap.get("pubtime"));
        bulkRequest.add(client.prepareIndex("tb_evt_contentinfo", "data")
                //id需要单独设置
                .setId(id)
                .setSource(stringObjectMap)
        );
        bulkRequest.get();
        return "导入指定contentInfo的id为" + id + "的数据成功！！！";
    }
}
