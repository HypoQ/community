package com.nowcoder.community.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.nowcoder.community.config.AliyunOssConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UploadHeaderService {
    private static final String[] IMAGE_FILE_EXTD = new String[]{"png", "bmp", "jpg", "jpeg"};

    private static final Logger logger = LoggerFactory.getLogger(UploadHeaderService.class);

    @Autowired
    private OSS ossClient;

    @Autowired
    private AliyunOssConfig aliyunOssConfig;

    @Autowired
    private UserService userService;

    public String upload(MultipartFile uploadFile, String userName) {


        // 获取oss的bucket名称
        String bukcetName = aliyunOssConfig.getHeaderBucketName();

        // 获取oss的地域节点
        String endpoint = aliyunOssConfig.getEndpoint();

        // 获取oss的AccessKey
        String accessKey = aliyunOssConfig.getAccessKey();

        // 获取oss的SecretKey
        String secretKey = aliyunOssConfig.getSecretKey();

        // 获取oss的文件存储目录
        String fileHost = aliyunOssConfig.getFileHost();

        // 返回上传成功的url
        String returnImgeUrl = "";

        // 校验文件格式
        boolean isLegal = false;
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1))) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            logger.error("文件格式不正确");
            return null;
        }

        String fileName = uploadFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String NewFileName = UUID.randomUUID().toString() + "." + fileType;
        String uploadUrl = fileHost + "/" + userName + "/" + NewFileName;

        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpg");

        //文件上传至阿里云OSS
        ossClient.putObject(bukcetName, uploadUrl, inputStream, meta);
        /**
         * 注意：在实际项目中，文件上传成功后，数据库中存储文件地址
         */
        // 获取文件上传后的图片返回地址
        returnImgeUrl = "http://" + bukcetName + "." + endpoint + "/" + uploadUrl;

        return returnImgeUrl;
    }
}
