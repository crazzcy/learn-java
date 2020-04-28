package org.chenyang.spring.transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ChenYang
 * @date 2020-04-28 20:32
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTT {

    @Autowired
    TT tt;

    @Test
    public void test01(){
        tt.outMethod();
    }
}
