package org.chenyang.spring.transactional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ChenYang
 * @date 2020-04-28 20:25
 **/
@Component
public class TT {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void outMethod() {
        System.out.println("This is outMethod..");
        innerMethod();
    }

    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void innerMethod() {
        System.out.println("This is innerMethod..");
    }

}
