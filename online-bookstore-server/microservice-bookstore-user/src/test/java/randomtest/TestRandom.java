package randomtest;

import com.alibaba.nacos.common.util.UuidUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author rkc
 * @date 2020/9/23 8:23
 * @version 1.0
 */
public class TestRandom {

    public static void main(String[] args) {
        String uuid = UuidUtils.generateUuid().substring(0, 30);
        System.out.println(uuid);
        System.out.println(uuid.length());
    }
}
