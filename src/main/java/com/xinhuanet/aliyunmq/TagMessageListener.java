package com.xinhuanet.aliyunmq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.xinhuanet.entity.*;
import com.xinhuanet.model.CmsShegaoVO;
import com.xinhuanet.service.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


public class TagMessageListener implements MessageListener {

    private static Logger log = LoggerFactory.getLogger(TagMessageListener.class);

    @Autowired
    private ContentInfoService contentInfoService;

    @Autowired
    private ContentDetailService contentDetailService;

    @Autowired
    private ContentArticleidService contentArticleidService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private ContentTagService contentTagService;

    @Autowired
    private AttachmentTagService attachmentTagService;

    @Transactional
    public boolean cmsTb(CmsShegaoVO cm) {

        //同步contentinfo
        String contentInforesult = contentInfoService.importOne(cm.getContentid());
        if ("true".equals(contentInforesult)) {
            log.info("cms_ContentInfo同步成功！" + cm.getContentid());
        } else {
            log.info("cms_ContentInfo同步失败...稍后重试" + cm.getContentid());
            return false;
        }


        //同步contentdetail
        //先删旧的
        List<ContentDetail> contentDetails = contentDetailService.getByContentid(cm.getContentid());
        for (ContentDetail contentDetail : contentDetails) {
            DeleteResponse delete = contentDetailService.delete(contentDetail.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentDetail删除成功！" + contentDetail.getId());
            } else {
                log.info("cms_ContentDetail删除失败！" + contentDetail.getId() + "正在重试");
                return false;
            }
        }

        //再插新的
        String contentDetailId = contentDetailService.selectContentDetailId(cm.getContentid());
        if (!"".equals(contentDetailId) && contentDetailId != null) {
            String contentDetailresult = contentDetailService.importOne(contentDetailId);
            if ("true".equals(contentDetailresult)) {
                log.info("cms_ContentDetail同步成功！" + contentDetailId);
            } else {
                log.info("cms_ContentDetail同步失败...稍后重试" + contentDetailId);
                return false;
            }
        }

        //同步contentarticle
        //先删旧的
        List<ContentArticleid> contentArticleids = contentArticleidService.getByContentid(cm.getContentid());
        for (ContentArticleid contentArticleid : contentArticleids) {
            DeleteResponse delete = contentArticleidService.delete(contentArticleid.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentArticleId删除成功！" + contentArticleid.getId());
            } else {
                log.info("cms_ContentArticleId删除失败！" + contentArticleid.getId() + "正在重试");
                return false;
            }
        }

        //再插新的
        String contentArticleIdID = contentArticleidService.selectContentArticleIdID(cm.getContentid());
        if (!"".equals(contentArticleIdID) && contentArticleIdID != null) {
            String contentArticleidresult = contentArticleidService.importOne(contentArticleIdID);
            if ("true".equals(contentArticleidresult)) {
                log.info("cms_ContentArticleId同步成功！" + contentArticleIdID);
            } else {
                log.info("cms_ContentArticleId同步失败...稍后重试" + contentArticleIdID);
                return false;
            }
        }

        //同步attachment
        //先删旧的
        List<Attachment> attachments = attachmentService.getByContentid(cm.getContentid());
        for (Attachment attachment : attachments) {
            DeleteResponse delete = attachmentService.delete(attachment.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentAttachment删除成功！" + attachment.getId());
            } else {
                log.info("cms_ContentAttachment删除失败！" + attachment.getId() + "正在重试");
                return false;
            }

        }

        //再插新的
        List<String> attachmentIdList = attachmentService.selectAttachmentId(cm.getContentid());
        for (String attachmentId : attachmentIdList) {
            String attachmentresult = attachmentService.importOne(attachmentId);
            if ("true".equals(attachmentresult)) {
                log.info("cms_ContentAttachment同步成功！" + attachmentId);
            } else {
                log.info("cms_ContentAttachment同步失败...稍后重试" + attachmentId);
                return false;
            }
        }
        return true;
    }

    @Transactional
    public boolean cmsSc(CmsShegaoVO cm) {

        //删除contentinfo
        DeleteResponse delete1 = contentInfoService.delete(cm.getContentid());
        if ("DELETED".equals(delete1.getResult())) {
            log.info("cms_ContentInfo删除成功！" + cm.getContentid());
        } else {
            log.info("cms_ContentInfo删除失败！" + cm.getContentid() + "正在重试");
            return false;
        }


        //删除contentdetail
        //先删旧的
        List<ContentDetail> contentDetails = contentDetailService.getByContentid(cm.getContentid());
        for (ContentDetail contentDetail : contentDetails) {
            DeleteResponse delete = contentDetailService.delete(contentDetail.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentDetail删除成功！" + contentDetail.getId());
            } else {
                log.info("cms_ContentDetail删除失败！" + contentDetail.getId() + "正在重试");
                return false;
            }

        }

        //删除contentarticle
        //先删旧的
        List<ContentArticleid> contentArticleids = contentArticleidService.getByContentid(cm.getContentid());
        for (ContentArticleid contentArticleid : contentArticleids) {
            DeleteResponse delete = contentArticleidService.delete(contentArticleid.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentArticleId删除成功！" + contentArticleid.getId());
            } else {
                log.info("cms_ContentArticleId删除失败！" + contentArticleid.getId() + "正在重试");
                return false;
            }
        }

        //删除attachment
        //先删旧的
        List<Attachment> attachments = attachmentService.getByContentid(cm.getContentid());
        for (Attachment attachment : attachments) {
            DeleteResponse delete = attachmentService.delete(attachment.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentAttachment删除成功！" + attachment.getId());
            } else {
                log.info("cms_ContentAttachment删除失败！" + attachment.getId() + "正在重试");
                return false;
            }
        }

        //删除contentTag
        //先删旧的
        List<ContentTag> contentTags = contentTagService.getByContentid(cm.getContentid());
        for (ContentTag contentTag : contentTags) {
            DeleteResponse delete = contentTagService.delete(contentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_ContentTag删除成功！" + contentTag.getId());
            } else {
                log.info("cms_ContentTag删除失败！" + contentTag.getId() + "正在重试");
                return false;
            }

        }

        //删除attachmentTag
        //先删旧的
        List<AttachmentTag> attachmentTags = attachmentTagService.getByContentid(cm.getContentid());
        for (AttachmentTag attachmentTag : attachmentTags) {
            DeleteResponse delete = attachmentTagService.delete(attachmentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("cms_AttachmentTag删除成功！" + attachmentTag.getId());
            } else {
                log.info("cms_AttachmentTag删除失败！" + attachmentTag.getId() + "正在重试");
            }

        }

        return true;
    }

    @Transactional
    public boolean shegaoTb(CmsShegaoVO cm) {

        //同步contentinfo
        String contentInforesult = contentInfoService.importOne(cm.getContentid());
        if ("true".equals(contentInforesult)) {
            log.info("shaogao_ContentInfo同步成功！" + cm.getContentid());
        } else {
            log.info("shaogao_ContentInfo同步失败...稍后重试" + cm.getContentid());
            return false;
        }


        //同步contentdetail
        //先删旧的
        List<ContentDetail> contentDetails = contentDetailService.getByContentid(cm.getContentid());
        for (ContentDetail contentDetail : contentDetails) {
            DeleteResponse delete = contentDetailService.delete(contentDetail.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentDetail删除成功！" + contentDetail.getId());
            } else {
                log.info("shaogao_ContentDetail删除失败！" + contentDetail.getId() + "正在重试");
                return false;
            }
        }

        //再插新的
        String contentDetailId = contentDetailService.selectContentDetailId(cm.getContentid());
        if (!"".equals(contentDetailId) && contentDetailId != null) {
            String contentDetailresult = contentDetailService.importOne(contentDetailId);
            if ("true".equals(contentDetailresult)) {
                log.info("shaogao_ContentDetail同步成功！" + contentDetailId);
            } else {
                log.info("shaogao_ContentDetail同步失败...稍后重试" + contentDetailId);
                return false;
            }
        }

        //同步contentarticle
        //先删旧的
        List<ContentArticleid> contentArticleids = contentArticleidService.getByContentid(cm.getContentid());
        for (ContentArticleid contentArticleid : contentArticleids) {
            DeleteResponse delete = contentArticleidService.delete(contentArticleid.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentArticleId删除成功！" + contentArticleid.getId());
            } else {
                log.info("shaogao_ContentArticleId删除失败！" + contentArticleid.getId() + "正在重试");
                return false;
            }
        }

        //再插新的
        String contentArticleIdID = contentArticleidService.selectContentArticleIdID(cm.getContentid());
        if (!"".equals(contentArticleIdID) && contentArticleIdID != null) {
            String contentArticleidresult = contentArticleidService.importOne(contentArticleIdID);
            if ("true".equals(contentArticleidresult)) {
                log.info("shaogao_ContentArticleId同步成功！" + contentArticleIdID);
            } else {
                log.info("shaogao_ContentArticleId同步失败...稍后重试" + contentArticleIdID);
                return false;
            }
        }

        //同步attachment
        //先删旧的
        List<Attachment> attachments = attachmentService.getByContentid(cm.getContentid());
        for (Attachment attachment : attachments) {
            DeleteResponse delete = attachmentService.delete(attachment.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentAttachment删除成功！" + attachment.getId());
            } else {
                log.info("shaogao_ContentAttachment删除失败！" + attachment.getId() + "正在重试");
                return false;
            }

        }

        //再插新的
        List<String> attachmentIdList = attachmentService.selectAttachmentId(cm.getContentid());
        for (String attachmentId : attachmentIdList) {
            String attachmentresult = attachmentService.importOne(attachmentId);
            if ("true".equals(attachmentresult)) {
                log.info("shaogao_ContentAttachment同步成功！" + attachmentId);
            } else {
                log.info("shaogao_ContentAttachment同步失败...稍后重试" + attachmentId);
                return false;
            }
        }

        return true;
    }

    @Transactional
    public boolean shegaoSc(CmsShegaoVO cm) {

        //删除contentinfo
        DeleteResponse delete1 = contentInfoService.delete(cm.getContentid());
        if ("DELETED".equals(delete1.getResult())) {
            log.info("shaogao_ContentInfo删除成功！" + cm.getContentid());
        } else {
            log.info("shaogao_ContentInfo删除失败！" + cm.getContentid() + "正在重试");
            return false;
        }


        //删除contentdetail
        //先删旧的
        List<ContentDetail> contentDetails = contentDetailService.getByContentid(cm.getContentid());
        for (ContentDetail contentDetail : contentDetails) {
            DeleteResponse delete = contentDetailService.delete(contentDetail.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentDetail删除成功！" + contentDetail.getId());
            } else {
                log.info("shaogao_ContentDetail删除失败！" + contentDetail.getId() + "正在重试");
                return false;
            }

        }

        //删除contentarticle
        //先删旧的
        List<ContentArticleid> contentArticleids = contentArticleidService.getByContentid(cm.getContentid());
        for (ContentArticleid contentArticleid : contentArticleids) {
            DeleteResponse delete = contentArticleidService.delete(contentArticleid.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentArticleId删除成功！" + contentArticleid.getId());
            } else {
                log.info("shaogao_ContentArticleId删除失败！" + contentArticleid.getId() + "正在重试");
                return false;
            }
        }

        //删除attachment
        //先删旧的
        List<Attachment> attachments = attachmentService.getByContentid(cm.getContentid());
        for (Attachment attachment : attachments) {
            DeleteResponse delete = attachmentService.delete(attachment.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentAttachment删除成功！" + attachment.getId());
            } else {
                log.info("shaogao_ContentAttachment删除失败！" + attachment.getId() + "正在重试");
                return false;
            }
        }

        //删除contentTag
        //先删旧的
        List<ContentTag> contentTags = contentTagService.getByContentid(cm.getContentid());
        for (ContentTag contentTag : contentTags) {
            DeleteResponse delete = contentTagService.delete(contentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_ContentTag删除成功！" + contentTag.getId());
            } else {
                log.info("shaogao_ContentTag删除失败！" + contentTag.getId() + "正在重试");
                return false;
            }
        }

        //删除attachmentTag
        //先删旧的
        List<AttachmentTag> attachmentTags = attachmentTagService.getByContentid(cm.getContentid());
        for (AttachmentTag attachmentTag : attachmentTags) {
            DeleteResponse delete = attachmentTagService.delete(attachmentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("shaogao_AttachmentTag删除成功！" + attachmentTag.getId());
            } else {
                log.info("shaogao_AttachmentTag删除失败！" + attachmentTag.getId() + "正在重试");
            }
        }

        return true;
    }

    @Transactional
    public boolean contenttagTb(String contentid) {
        //先删旧的
        List<ContentTag> contentTags = contentTagService.getByContentid(contentid);
        for (ContentTag contentTag : contentTags) {
            DeleteResponse delete = contentTagService.delete(contentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("contentTag删除成功！" + contentTag.getId());
            } else {
                log.info("contentTag删除失败！" + contentTag.getId() + "正在重试");
                return false;
            }
        }

        //同步contentTag
        List<String> TtagIds = contentTagService.selectContentTagId(contentid);
        for (String ttagId : TtagIds) {
            String contentTagresult = contentTagService.importOne(ttagId);
            if ("true".equals(contentTagresult)) {
                log.info("contenttag同步成功！" + ttagId);
            } else {
                log.info("contenttag同步失败...稍后重试" + ttagId);
                return false;
            }
        }
        return true;
    }

    @Transactional
    public boolean attachmentTagTb(String contentid) {

        //先删旧的
        List<AttachmentTag> attachmentTags = attachmentTagService.getByContentid(contentid);
        for (AttachmentTag attachmentTag : attachmentTags) {
            DeleteResponse delete = attachmentTagService.delete(attachmentTag.getId());
            if ("DELETED".equals(delete.getResult())) {
                log.info("AttachmentTag删除成功！" + attachmentTag.getId());
            } else {
                log.info("AttachmentTag删除失败！" + attachmentTag.getId() + "正在重试");
                return false;
            }
        }

        //同步attachmentTag
        List<String> attachmentTagIds = attachmentTagService.selectAttachmentTagId(contentid);
        for (String attachmentTagId : attachmentTagIds) {
            String attachmentTagresult = attachmentTagService.importOne(attachmentTagId);

            if ("true".equals(attachmentTagresult)) {
                log.info("attachmenttag同步成功！" + attachmentTagId);
            } else {
                log.info("attachmenttag同步失败...稍后重试" + attachmentTagId);
                return false;
            }
        }

        return true;
    }


    @Override
    public Action consume(Message message, ConsumeContext context) {

        if ("cms_files_store".equals(message.getTag())) {

            log.info("=============== cms的mq消息为： " + new String(message.getBody()) + "====================");

            CmsShegaoVO cm = new Gson().fromJson(new JsonParser().parse(new String(message.getBody())).getAsJsonObject(), CmsShegaoVO.class);

            if (cm.getDeleteflag() == null) {

                System.out.println("cms同步");

                /*boolean b = cmsTb(cm);
                if (!b) {
                    log.info("cms同步失败！" + cm.getContentid() + "正在重试");
                    return Action.ReconsumeLater;
                }*/

            } else if (cm.getDeleteflag() != null) {

                System.out.println("cms删除");

                /*boolean b = cmsSc(cm);
                if (!b) {
                    log.info("cms删除失败！" + cm.getContentid() + "正在重试");
                    return Action.ReconsumeLater;
                }*/
            }

        } else if ("cms_shegao_store".equals(message.getTag())) {

            log.info("=============== shaogao的mq消息为： " + new String(message.getBody()) + "====================");

            CmsShegaoVO cm = new Gson().fromJson(new JsonParser().parse(new String(message.getBody())).getAsJsonObject(), CmsShegaoVO.class);

            if (cm.getDeleteflag() == null) {

                System.out.println("shegao同步");

               /* boolean b = shegaoTb(cm);
                if (!b) {
                    log.info("shegao同步失败！" + cm.getContentid() + "正在重试");
                    return Action.ReconsumeLater;
                }*/

            } else if (cm.getDeleteflag() != null) {

                System.out.println("shegao删除");

                /*boolean b = shegaoSc(cm);
                if (!b) {
                    log.info("shegao删除失败！" + cm.getContentid() + "正在重试");
                    return Action.ReconsumeLater;
                }*/
            }

        } else if ("DataPartitioning".equals(message.getTag())) {

            String messageContent = new String(message.getBody());
            String[] split = messageContent.split("\"");
            List<String> contentList = Arrays.asList(split);
            String contentid = split[2].trim().substring(1, split[2].length() - 1);
            if (contentList.contains("msg")) {

                System.out.println("contenttag同步");

                /*boolean b = contenttagTb(contentid);
                if (!b) {
                    log.info("ContentTag删除失败！" + contentid + "正在重试");
                    return Action.ReconsumeLater;
                }*/

            } else if (contentList.contains("status")) {

                System.out.println("attachmenttag删除");

                /*boolean b = attachmentTagTb(contentid);
                if (!b) {
                    log.info("AttachmentTag删除失败！" + contentid + "正在重试");
                    return Action.ReconsumeLater;
                }*/
            }
        }
        return Action.CommitMessage;
    }
}
