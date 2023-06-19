package com.nowcoder.community.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Accessors(chain = true)
public class AliyunOssConfig {

    @Value("${aliyun.oss.bucket.accessKey}")
    private String accessKey;

    @Value("${aliyun.oss.bucket.secretKey}")
    private String secretKey;

    @Value("${aliyun.oss.bucket.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucket.headerName}")
    private String headerBucketName;

    @Value("${aliyun.oss.bucket.headerUrl}")
    private String headerBucketUrl;

    @Value("${aliyun.oss.bucket.shareName}")
    private String shareBucketName;

    @Value("${aliyun.oss.bucket.shareUrl}")
    private String shareBucketUrl;


    @Value("${aliyun.oss.bucket.fileHost}")
    private String fileHost;

    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(endpoint, accessKey, secretKey);
    }
}
