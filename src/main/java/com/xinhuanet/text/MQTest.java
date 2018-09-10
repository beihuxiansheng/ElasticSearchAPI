package com.xinhuanet.text;

import java.util.Arrays;
import java.util.List;

public class MQTest {

    public static void main(String[] args) {

        String me1 = "{\"contentid\":490206,\"tag\":{\"status\":\"OK\",\"taskid\":\"2214977\",\"tag\":[{\"score\":0.471225,\"name\":\"皮卡车\"},{\"score\":0.172833,\"name\":\"半履带装甲车\"},{\"score\":0.119497,\"name\":\"拖车,牵引车,清障车\"},{\"score\":0.101978,\"name\":\"吉普车\"},{\"score\":0.0110822,\"name\":\"警车,巡逻车\"},{\"score\":0.00928089,\"name\":\"车轮\"},{\"score\":0.00648724,\"name\":\"拖车,铰接式卡车\"},{\"score\":0.00579959,\"name\":\"迷你巴士\"},{\"score\":0.00550682,\"name\":\"面包车\"},{\"score\":0.00404555,\"name\":\"T型发动机小汽车\"}]}}";
        String me2 = "{\"contentid\":490209,\"tag\":{\"msg\":1,\"detail\":{\"res\":{\"ner\":{\"人名\":[\"理查德·布朗宁\",\"伯勒\"],\"地名\":[\"英国\"],\"机构团体名\":[\"新华社\"],\"时间词\":[\"当日\",\"2018年7月23日\",\"7月22日\"]}}}}}";

        String messageContent = new String(me2);
        String[] split = messageContent.split("\"");
        List<String> contentList = Arrays.asList(split);
        String contentid = split[2].trim().substring(1, split[2].length() - 1);
        if (contentList.contains("msg")) {
            System.out.println("文字mq");
        } else if (contentList.contains("status")) {
            System.out.println("图片mq");
        }
    }
}
