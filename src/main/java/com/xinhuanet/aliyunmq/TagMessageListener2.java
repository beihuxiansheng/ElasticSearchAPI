package com.xinhuanet.aliyunmq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xinhuanet.entity.*;
import com.xinhuanet.service.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;


public class TagMessageListener2 implements MessageListener {

    private static Logger log = LoggerFactory.getLogger(TagMessageListener2.class);

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

    @Override
    public Action consume(Message message, ConsumeContext context) {

        if ("DataPartitioning".equals(message.getTag())) {
            String messageContent = new String(message.getBody());
            String[] split = messageContent.split("\"");
            List<String> contentList = Arrays.asList(split);
            String contentid = split[2].trim().substring(1, split[2].length() - 1);

            //判断是否二次
            GetResponse byId = contentInfoService.getById(contentid);
            if (!byId.isExists()) {

                log.info("==============新增：" + contentid + "================");

                //直接插入
                String s = contentInfoService.importOne(contentid);
                if ("true".equals(s)) {
                    log.info("新增：添加contentInfoID成功： " + contentid);
                } else {
                    log.info("新增：添加contentInfoID重试。。。" + contentid);
                    return Action.ReconsumeLater;
                }

                String contentDetailId = contentDetailService.selectContentDetailId(contentid);
                String s1 = contentDetailService.importOne(contentDetailId);
                if ("true".equals(s1)) {
                    log.info("新增：添加contentdetailID成功： " + contentDetailId);
                } else {
                    log.info("新增：添加contentdetailID重试。。。" + contentDetailId);
                    return Action.ReconsumeLater;
                }

                String contentArticleIdID = contentArticleidService.selectContentArticleIdID(contentid);
                if (contentArticleIdID != null) {
                    String s2 = contentArticleidService.importOne(contentArticleIdID);
                    if ("true".equals(s2)) {
                        log.info("新增：添加contentArticleID成功： " + contentArticleIdID);
                    } else {
                        log.info("新增：添加contentArticleID重试。。。" + contentArticleIdID);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> attachmentIds = attachmentService.selectAttachmentId(contentid);
                for (String attachmentId : attachmentIds) {
                    String s3 = attachmentService.importOne(attachmentId);
                    if ("true".equals(s3)) {
                        log.info("新增：添加attachmentID成功： " + attachmentId);
                    } else {
                        log.info("新增：添加attachmentID重试。。。： " + attachmentId);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> contentTagIds = contentTagService.selectContentTagId(contentid);
                for (String contentTagId : contentTagIds) {
                    String s4 = contentTagService.importOne(contentTagId);
                    if ("true".equals(s4)) {
                        log.info("新增：添加contentTagID成功： " + contentTagId);
                    } else {
                        log.info("新增：添加contentTagID重试。。。： " + contentTagId);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> attachmentTagIds = attachmentTagService.selectAttachmentTagId(contentid);
                for (String attachmentTagId : attachmentTagIds) {
                    String s5 = attachmentTagService.importOne(attachmentTagId);
                    if ("true".equals(s5)) {
                        log.info("新增：添加attachmentTagID成功： " + attachmentTagId);
                    } else {
                        log.info("新增：添加attachmentTagID重试。。。： " + attachmentTagId);
                        return Action.ReconsumeLater;
                    }
                }

            } else {
                log.info("==============修改：" + contentid + "================");


                //先删除ES
                //双保险 get
                List<ContentDetail> byContentid = contentDetailService.getByContentid(contentid);
                if (byContentid.size() > 0 && byContentid != null) {
                    List<ContentDetail> byContentidd = contentDetailService.getByContentid(contentid);
                    if (byContentidd.size() > 0 && byContentidd != null) {
                        for (ContentDetail contentDetail : byContentidd) {
                            DeleteResponse delete = contentDetailService.delete(contentDetail.getId());
                            if ("DELETED".equals(delete.getResult().toString())) {
                                log.info("修改：删除contentdetailID成功： " + contentDetail.getId());
                            } else {
                                log.info("修改：删除contentdetailID重试。。。： " + contentDetail.getId());
                                return Action.ReconsumeLater;
                            }
                        }
                    }
                }


                List<ContentArticleid> byContentid1 = contentArticleidService.getByContentid(contentid);
                if (byContentid1.size() > 0 && byContentid1 != null) {
                    List<ContentArticleid> byContentid1d = contentArticleidService.getByContentid(contentid);
                    if (byContentid1d.size() > 0 && byContentid1d != null) {
                        for (ContentArticleid contentArticleid : byContentid1d) {
                            DeleteResponse delete = contentArticleidService.delete(contentArticleid.getId());
                            if ("DELETED".equals(delete.getResult().toString())) {
                                log.info("修改：删除contentArticleID成功： " + contentArticleid.getId());
                            } else {
                                log.info("修改：删除contentArticleID重试。。。： " + contentArticleid.getId());
                                return Action.ReconsumeLater;
                            }
                        }
                    }
                }

//                List<Attachment> byContentid2 = attachmentService.getByContentid(contentid);
//                if (byContentid2.size() > 0 && byContentid2 != null) {
//                    List<Attachment> byContentid2d = attachmentService.getByContentid(contentid);
//                    if (byContentid2d.size() > 0 && byContentid2d != null) {
//                        for (Attachment attachment : byContentid2d) {
//                            DeleteResponse delete = attachmentService.delete(attachment.getId());
//                            if ("DELETED".equals(delete.getResult().toString())) {
//                                log.info("修改：删除attachmentID成功： " + attachment.getId());
//                            } else {
//                                log.info("修改：删除attachmentID重试。。。： " + attachment.getId());
//                                return Action.ReconsumeLater;
//                            }
//                        }
//                    }
//                }

                BulkByScrollResponse deletes2 = attachmentService.deletes(contentid);
                long deleted2 = deletes2.getDeleted();
                log.info("删除了"+deleted2+"个attachment");

//                List<ContentTag> byContentid3 = contentTagService.getByContentid(contentid);
//                if (byContentid3.size() > 0 && byContentid3 != null) {
//                    List<ContentTag> byContentid3d = contentTagService.getByContentid(contentid);
//                    if (byContentid3d.size() > 0 && byContentid3d != null) {
//                        for (ContentTag contentTag : byContentid3d) {
//                            DeleteResponse delete = contentTagService.delete(contentTag.getId());
//                            if ("DELETED".equals(delete.getResult().toString())) {
//                                log.info("修改：删除contentTagID成功： " + contentTag.getId());
//                            } else {
//                                log.info("修改：删除contentTagID重试。。。： " + contentTag.getId());
//                                return Action.ReconsumeLater;
//                            }
//                        }
//                    }
//                }

                //查询删除，适用于数量较多的删除
                BulkByScrollResponse deletes = contentTagService.deletes(contentid);
                long deleted = deletes.getDeleted();
                log.info("删除了"+deleted+"个contenttag");

//                List<AttachmentTag> byContentid4 = attachmentTagService.getByContentid(contentid);
//                if (byContentid4.size() > 0 && byContentid4 != null) {
//                    List<AttachmentTag> byContentid4d = attachmentTagService.getByContentid(contentid);
//                    if (byContentid4d.size() > 0 && byContentid4d != null) {
//                        for (AttachmentTag attachmentTag : byContentid4d) {
//                            DeleteResponse delete = attachmentTagService.delete(attachmentTag.getId());
//                            if ("DELETED".equals(delete.getResult().toString())) {
//                                log.info("修改：删除attachmentID成功： " + attachmentTag.getId());
//                            } else {
//                                log.info("修改：删除attachmentID重试。。。： " + attachmentTag.getId());
//                                return Action.ReconsumeLater;
//                            }
//                        }
//                    }
//                }

                BulkByScrollResponse deletes1 = attachmentTagService.deletes(contentid);
                long deleted1 = deletes1.getDeleted();
                log.info("删除了"+deleted1+"个attachmenttag");

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                //直接插入

                String contentDetailId = contentDetailService.selectContentDetailId(contentid);
                String s1 = contentDetailService.importOne(contentDetailId);
                if ("true".equals(s1)) {
                    log.info("修改：添加contentdetailID成功： " + contentDetailId);
                } else {
                    log.info("修改：添加contentdetailID重试。。。" + contentDetailId);
                    return Action.ReconsumeLater;
                }

                String contentArticleIdID = contentArticleidService.selectContentArticleIdID(contentid);
                if (contentArticleIdID != null) {
                    String s2 = contentArticleidService.importOne(contentArticleIdID);
                    if ("true".equals(s2)) {
                        log.info("修改：添加contentArticleID成功： " + contentArticleIdID);
                    } else {
                        log.info("修改：添加contentArticleID重试。。。" + contentArticleIdID);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> attachmentIds = attachmentService.selectAttachmentId(contentid);
                for (String attachmentId : attachmentIds) {
                    String s3 = attachmentService.importOne(attachmentId);
                    if ("true".equals(s3)) {
                        log.info("修改：添加attachmentID成功： " + attachmentId);
                    } else {
                        log.info("修改：添加attachmentID重试。。。： " + attachmentId);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> contentTagIds = contentTagService.selectContentTagId(contentid);
                for (String contentTagId : contentTagIds) {
                    String s4 = contentTagService.importOne(contentTagId);
                    if ("true".equals(s4)) {
                        log.info("修改：添加contentTagID成功： " + contentTagId);
                    } else {
                        log.info("修改：添加contentTagID重试。。。： " + contentTagId);
                        return Action.ReconsumeLater;
                    }
                }

                List<String> attachmentTagIds = attachmentTagService.selectAttachmentTagId(contentid);
                for (String attachmentTagId : attachmentTagIds) {
                    String s5 = attachmentTagService.importOne(attachmentTagId);
                    if ("true".equals(s5)) {
                        log.info("修改：添加attachmentTagID成功： " + attachmentTagId);
                    } else {
                        log.info("修改：添加attachmentTagID重试。。。： " + attachmentTagId);
                        return Action.ReconsumeLater;
                    }
                }
            }
        }
        return Action.CommitMessage;
    }
}
