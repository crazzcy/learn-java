package org.chenyang.spring.transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ChenYang
 * @date 2020年4月29日11:04:32
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSomeT {

    @Autowired
    SomeT1 someT1;

    @Test
    public void test01(){
        someT1.t1();
    }
}