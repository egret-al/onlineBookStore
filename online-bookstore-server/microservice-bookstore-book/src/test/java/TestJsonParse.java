import com.alibaba.fastjson.JSONObject;
import com.onlinebookstore.BookServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author rkc
 * @date 2020/11/8 12:24
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BookServerApplication.class})
public class TestJsonParse {

    @Test
    public void test() throws Exception {
        String jsonStr = "{\n" +
                "\t\"book\": {\n" +
                "\t\t\"bookName\": \"高等代数\",\n" +
                "\t\t\"isbn\": \"123456789012\",\n" +
                "\t\t\"author\": \"rkc\",\n" +
                "\t\t\"publisher\": \"清华大学出版社\"\n" +
                "\t},\n" +
                "\t\"bookResource\": {\n" +
                "\t\t\"resourceUrl\": \"http://www.baidu.com\"\n" +
                "\t}\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Object obj1 = jsonObject.get("book");
        System.out.println(obj1);
        Object bookResource = jsonObject.get("bookResource");
        System.out.println(bookResource);
    }
}
