package com.xinhuanet.controller;

import com.xinhuanet.entity.Attachment;
import com.xinhuanet.entity.ContentInfo;
import com.xinhuanet.service.AttachmentService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tb_evt_attachment")
public class AttachmentController {

    private static final Logger LOG = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public ResponseEntity getById(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        GetResponse result = attachmentService.getById(id);
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        LOG.info("attachment===查询id为" + result.getId() + "的数据成功!===");
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    /**
     * 新增/修改
     *
     * @param attachment
     * @return
     */
    @RequestMapping("/addOrUpdate")
    public ResponseEntity addOrUpdate(Attachment attachment) {
        IndexResponse result = attachmentService.addOrUpdate(attachment);
        return new ResponseEntity("新增/修改id为" + result.getId() + "的数据成功！", HttpStatus.OK);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ResponseEntity delete(@RequestParam(name = "id") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        DeleteResponse result = attachmentService.delete(id);
        if ((result.getResult() + "").equals("NOT_FOUND")) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        LOG.info("attachment===删除id为" + result.getId() + "的数据成功!===");
        return new ResponseEntity("删除id为" + result.getId() + "的数据成功！", HttpStatus.OK);
    }

    /**
     * 根据id查询数据库
     * @param id
     * @return
     */
    @RequestMapping("/selectAttachment")
    public Attachment selectAttachment(@RequestParam(name = "id", defaultValue = "") String id) {
        return attachmentService.selectAttachment(id);
    }

    /**
     * 查询数据库所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<Attachment> findAll() {
        return attachmentService.findAll();
    }

    @RequestMapping("/importData")
    public void importData(){
        attachmentService.importData();
    }

    @RequestMapping("/importOne")
    public String importOne(String id){
        return attachmentService.importOne(id);
    }

}
