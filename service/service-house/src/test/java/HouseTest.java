import com.atguigu.dao.HouseDao;
import com.atguigu.entity.House;
import com.atguigu.service.DictService;
import com.atguigu.service.HouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author ZhangHaoYu
 * @Date 2023/2/4 21:59
 * @Version 1.0
 */
@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class HouseTest {
    @Autowired
    private HouseDao houseDao;

    @Test
    public void test() {
        House byId = houseDao.getById(1l);
        System.out.println("byId = " + byId);
    }
}
