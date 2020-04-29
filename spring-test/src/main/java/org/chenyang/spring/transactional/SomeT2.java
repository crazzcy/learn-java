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
public class SomeT2 {

    @Autowired
    DataSource dataSource;

    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void t2(String paymentId) {

        System.out.println("This is t2 Method..");
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
