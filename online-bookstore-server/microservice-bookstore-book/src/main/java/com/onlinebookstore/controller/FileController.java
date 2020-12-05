package com.onlinebookstore.controller;

import com.onlinebookstore.common.CommonplaceResult;
import com.onlinebookstore.config.SftpProperties;
import com.onlinebookstore.util.SftpConnectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/5 11:09
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file/pri")
public class FileController {
    @Autowired
    private SftpProperties sftpProperties;

    /**
     * 上传文件到nginx服务器
     * @param file MultipartFile
     * @return CommonplaceResult
     */
    @PostMapping("upload")
    public CommonplaceResult uploadFileToNginx(MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) return CommonplaceResult.buildErrorNoData("请选择文件！");
        log.info("file: " + file);
        SftpConnectUtils sftpConnectUtils = new SftpConnectUtils(sftpProperties.getUsername(), sftpProperties.getPassword(), sftpProperties.getIp(), sftpProperties.getId());
        //连接linux
        sftpConnectUtils.login();
        try {
            String fileName = file.getOriginalFilename();
            log.info("图片名称：" + fileName);
            //保存的文件的名称
            String name = UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
            log.info("上传地址：" + sftpProperties.getUploadPath());
            sftpConnectUtils.upload(sftpProperties.getUploadPath(), name, file.getInputStream());
            log.info("图片在nginx中的位置：" + sftpProperties.getDownPath() + name);
            String accessAddr = "http://" + sftpProperties.getIp() + sftpProperties.getDownPath() + name;
            log.info("访问地址：" + accessAddr);
            return CommonplaceResult.buildSuccess(accessAddr,"上传成功！");
        } catch (Exception e) {
            log.error("上传失败！" + e.getMessage());
            e.printStackTrace();
            return CommonplaceResult.buildErrorNoData("上传失败！" + e.getMessage());
        } finally {
            //释放连接
            sftpConnectUtils.logout();
        }
    }
}
