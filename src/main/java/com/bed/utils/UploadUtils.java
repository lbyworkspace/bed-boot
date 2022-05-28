package com.bed.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * 上传图片的工具类
 */
public class UploadUtils {

    private static final String ACCESS_KEY = "sYCIyCtgDytolURtNGtMNO4O5We2g-jcPNsMOV34";

    private static final String SECRET_KEY = "J8cJhFNl3PGgejOAtw19YAgQTDjSECpWM0DF8vwi";

    private static final String BUCKET = "hedexin";

    public static String upload(File file){
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        String key = null;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);
        try {
            Response response = uploadManager.put(file.getPath(), key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "http://rckmj5bw1.hn-bkt.clouddn.com/"+putRet.key;
        } catch (QiniuException ex) {
            throw new RuntimeException(ex);
        }
    }
}
