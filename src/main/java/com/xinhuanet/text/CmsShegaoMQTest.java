package com.xinhuanet.text;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.xinhuanet.model.CmsShegaoVO;

public class CmsShegaoMQTest {

    public static void main(String[] args) {

        String message = "{\"contentid\":696444}";
        String id = "696444";
        String messagee = "{\"contentid\":" + 696444 + ",\"deleteflag\": 1}";

        CmsShegaoVO cm = new Gson().fromJson(new JsonParser().parse(messagee).getAsJsonObject(), CmsShegaoVO.class);

        System.out.println(cm.getContentid());
        System.out.println(cm.getDeleteflag());

    }
}
