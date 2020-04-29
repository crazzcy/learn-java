package org.chenyang.spring.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ChenYang
 * @date 2020-04-29 11:01
 **/
@Component
public class SomeT1 {

    @Autowired
    DataSource dataSource;

    @Autowired
    SomeT2 someT2;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void t1() {
        System.out.println("This is t1 Method..");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement  = connection.createStatement();
            String sql = "select payment_id, amount from a_payment_test";
            ResultSet rs=  statement.executeQuery(sql);
            while(rs.next()) {
                // 外层方法执行一半，跑跑内层方法
                System.out.println("paymentId = " + rs.getString(1) + ",amount=" + rs.getString(2));
                someT2.t2(rs.getString(1) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
