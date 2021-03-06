package org.chenyang.spring.transactional;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
 * @date 2020-04-28 20:25
 **/
@Component
public class TT {

    @Autowired
    DataSource dataSource;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void outerMethod() {
        System.out.println("This is outerMethod..");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement  = connection.createStatement();
            String sql = "select payment_id, amount from a_payment_test";
            ResultSet rs=  statement.executeQuery(sql);
            while(rs.next()) {
                // 外层方法执行一半，跑跑内层方法
                System.out.println("paymentId = " + rs.getString(1) + ",amount=" + rs.getString(2));
                innerMethod(rs.getString(1) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void innerMethod(String paymentId) {
        System.out.println("This is innerMethod..");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement  = connection.createStatement();
            String sql = "select acc_num from a_payment_test where payment_id = " + paymentId;
            ResultSet rs=  statement.executeQuery(sql);
            while(rs.next()) {
                System.out.println("acc_num = " + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
