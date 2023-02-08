import com.atguigu.util.QiniuUtil;
import org.junit.Test;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/6 15:38
 * @Version 1.0
 */

public class QiniuTest {
    @Test
    public void test() {
        QiniuUtil.upload2Qiniu("D:\\picture\\p1.jpg", "p1.png");

    }
}
