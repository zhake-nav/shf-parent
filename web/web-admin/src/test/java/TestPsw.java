import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/11 17:32
 * @Version 1.0
 */
public class TestPsw {
    @Test
    public void test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("111111");
        System.out.println("encode = " + encode);
    }

}
