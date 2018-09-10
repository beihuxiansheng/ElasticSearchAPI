package com.xinhuanet.controller;

import com.xinhuanet.entity.ContentTagType;
import com.xinhuanet.service.ContentTagTypeService;
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
@RequestMapping("/tb_evt_content_tag_type")
public class ContentTagTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(ContentTagTypeController.class);

    @Autowired
    private ContentTagTypeService contentTagTypeService;

    /**
     * es根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public ResponseEntity getById(@RequestParam(name = "id", defaultValue = "") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        GetResponse result = contentTagTypeService.getById(id);
        if (!result.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        LOG.info("contentTagType===查询id为" + result.getId() + "的数据成功!===");
        return new ResponseEntity(result.getSource(), HttpStatus.OK);
    }

    /**
     * es根据id新增修改
     * @param contentTagType
     * @return
     */
    @RequestMapping("/addOrUpdate")
    public ResponseEntity addOrUpdate(ContentTagType contentTagType) {
        IndexResponse result = contentTagTypeService.addOrUpdate(contentTagType);
        return new ResponseEntity("新增/修改id为" + result.getId() + "的数据成功！", HttpStatus.OK);
    }

    /**
     * es根据id删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public ResponseEntity delete(@RequestParam(name = "id") String id) {
        if (id.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        DeleteResponse result = contentTagTypeService.delete(id);
        if ((result.getResult() + "").equals("NOT_FOUND")) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        LOG.info("contentTagType===删除id为" + result.getId() + "的数据成功!===");
        return new ResponseEntity("删除id为" + result.getId() + "的数据成功！", HttpStatus.OK);
    }

    /**
     * 数据库根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/selectContentTagType")
    public ContentTagType selectContentTagType(@RequestParam(name = "id", defaultValue = "") String id) {
        return contentTagTypeService.selectContentTagType(id);
    }

    /**
     * 数据库查询所有
     * @return
     */
    @RequestMapping("/findAll")
    public List<ContentTagType> findAll() {
        return contentTagTypeService.findAll();
    }

    /**
     * 数据库导入es
     */
    @RequestMapping("/importData")
    public void importData() {
        contentTagTypeService.importData();
    }

    /**
     * 数据库根据id导入es
     * @param id
     * @return
     */
    @RequestMapping("/importOne")
    public String importOne(String id) {
        return contentTagTypeService.importOne(id);
    }

}
