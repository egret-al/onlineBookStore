package randomtest;

import com.onlinebookstore.UserServerApplication;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/12/3 10:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServerApplication.class})
public class TestEncryptor {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void jacketEncrypt() {
        //加密
        String password1 = encryptor.encrypt("123456");
        String password2 = encryptor.encrypt("123456");
        String password3 = encryptor.encrypt("123456");
        String password4 = encryptor.encrypt("123456");
        System.out.println("password1 密文: " + password1);
        System.out.println("password2 密文: " + password2);
        System.out.println("password3 密文: " + password3);
        System.out.println("password4 密文: " + password4);

        //解密
        String decrypt1 = encryptor.decrypt(password1);
        String decrypt2 = encryptor.decrypt(password2);
        String decrypt3 = encryptor.decrypt(password3);
        String decrypt4 = encryptor.decrypt(password4);
        System.out.println("password1 解密：" + decrypt1);
        System.out.println("password2 解密：" + decrypt2);
        System.out.println("password3 解密：" + decrypt3);
        System.out.println("password4 解密：" + decrypt4);
    }
}
