package com.quxue.template.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MinIOUtils {


    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Resource
    private MinioClient minioClient;

    public void deleteFile(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            try {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> deleteFiles(List<String> fileNames) {
        if (!fileNames.isEmpty()) {
            //将传入的List<String>通过流转换成List<DeleteObject>
            List<DeleteObject> deleteObjects = fileNames.stream().map(new Function<String, DeleteObject>() {
                @Override
                public DeleteObject apply(String fileName) {
                    return new DeleteObject(fileName);
                }
            }).collect(Collectors.toList());
/*            ArrayList<DeleteObject> deleteObjects = new ArrayList<>();
            for (String fileName : fileNames) {
                deleteObjects.add(new DeleteObject(fileName));
            }*/
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(bucketName)
                    .objects(deleteObjects)
                    .build()
            );
            try {
                ArrayList<String> errors = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                for (Result<DeleteError> result : results) {//对结果集进行遍历删除
                    DeleteError error = result.get();
                    sb.append("error ObjectName= ")
                            .append(error.objectName())
                            .append("\terrorMessage= ")
                            .append(error.message())
                            .append("\n");
                    errors.add(sb.toString());
                }
                return errors;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    public String uploadFile(MultipartFile multipartFile) {

        //根据旧文件名称生成一个新的文件名称
        String fileName = generateName(multipartFile.getOriginalFilename());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            //创建PutObject对象（封装对象信息）
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().object(fileName)//设置对象的名称
                    .contentType(multipartFile.getContentType())//设置文件类型
                    .stream(inputStream, multipartFile.getSize(), -1)//设置上传的文件
                    .bucket(bucketName)//设置桶
                    .build();

            //提交对象到minio服务器
            minioClient.putObject(putObjectArgs);

            //拼接一个minio服务器的地址返回给前端
            //http://192.168.126.11:9000/miniotest/37362e29131541ddb35082da168f0680.jpg
            StringBuilder filePath = new StringBuilder();
            filePath.append(this.endpoint).append("/").append(bucketName).append("/").append(fileName);
            return filePath.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件方法
     *
     * @param fileName
     * @return
     */
    public FilterInputStream getInputStream(String fileName) {
        GetObjectResponse objectResponse;
        try {
            objectResponse = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return objectResponse;

    }

    /**
     * 获取文件的MIME类型  如  jpg : image/jpeg
     *
     * @param fileName
     * @return
     */
    public String getFileMimeType(String fileName) {
        try {
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return statObjectResponse.contentType();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String generateName(String oldName) {
        //获取文件后缀
        String extension = FilenameUtils.getExtension(oldName);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //生成新的文件名称
        return uuid + "." + extension;
    }
}
