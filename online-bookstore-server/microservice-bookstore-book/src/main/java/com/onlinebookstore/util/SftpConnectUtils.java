package com.onlinebookstore.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

/**
 * sftp连接工具类
 *
 * @author rkc
 * @version 1.0
 * @date 2020/12/5 10:59
 */
@Slf4j
public class SftpConnectUtils {
    private ChannelSftp sftp;
    private Session session;
    //ftp登录用户名
    private String username;
    //ftp登录密码
    private String password;
    //秘钥
    private String privateKey;
    //ftp服务器地址
    private String host;
    //ftp端口
    private int port;

    public SftpConnectUtils() {
    }

    /**
     * 构造基于密码认证的sftp对象
     * @param username 账号
     * @param password 密码
     * @param host     ip
     * @param port     端口
     */
    public SftpConnectUtils(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     * @param username   登录账号
     * @param host       ip
     * @param port       端口
     * @param privateKey 秘钥
     */
    public SftpConnectUtils(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    /**
     * 连接sftp服务器
     */
    public void connect() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);   // 设置私钥
                log.info("sftp connect,path of private key file：{}", privateKey);
            }
            log.info("sftp connect by host:{} username:{}", host, username);
            session = jsch.getSession(username, host, port);
            log.info("Session is build");
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            log.info("Session is connected");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            log.info("channel is connected");

            sftp = (ChannelSftp) channel;
            log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));
        } catch (JSchException e) {
            log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}",
                    host, port, e.getMessage());
        }
    }

    /**
     * 关闭连接 server
     */
    public void close() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param is           输入流
     */
    public void upload(String directory, String sftpFileName, InputStream is) throws SftpException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("directory is not exist. prepare for making directory");
            sftp.mkdir(directory);
            log.info("directory is created successfully");
            sftp.cd(directory);
        }
        sftp.put(is, sftpFileName);
        log.info("file:{} is upload successful", sftpFileName);
    }

    /**
     * 删除文件
     * @param directory 目录
     * @param sftpFileName 文件名
     */
    public void delete(String directory, String sftpFileName) throws SftpException {
        log.info("directory: {}, fileName: {}", directory, sftpFileName);
        try {
            sftp.cd(directory);
            sftp.rm(sftpFileName);
        } catch (SftpException e) {
            log.warn("directory is not exist");
        }
        log.info("file:{} delete successfully", sftpFileName);
    }
}
