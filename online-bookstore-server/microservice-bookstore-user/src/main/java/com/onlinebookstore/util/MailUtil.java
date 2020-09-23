package com.onlinebookstore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author rkc
 * @date 2020/9/21 11:26
 * @version 1.0
 */
@Slf4j
@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String from;
    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 普通文本邮件
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(from);

        javaMailSender.send(simpleMailMessage);
    }

    /**
     * HTML 文本邮件
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content HTML内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        javaMailSender.send(message);
    }

    /**
     * 携带附件的邮箱
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content HTML内容
     * @param filePath 附件路径
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = fileSystemResource.getFilename();
        helper.addAttachment(fileName, fileSystemResource);

        javaMailSender.send(message);
    }

    /**
     * 图片邮件
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content HTML内容
     * @param rscPath 图片路径
     * @param rscId 图片ID
     */
    public void sendInLinkResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        log.info("发送静态邮件开始: {},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(from);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            javaMailSender.send(message);
            log.info("发送静态邮件成功!");

        } catch (MessagingException e) {
            log.error("发送静态邮件失败: ", e);
        }
    }
}
