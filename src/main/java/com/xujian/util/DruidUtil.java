package com.xujian.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
 

public class DruidUtil {
	public static DataSource ds = null;
	static {
		try {
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("druid.properties"));
			// 创建DataSource对象
			ds = DruidDataSourceFactory.createDataSource(p);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	public static void main(String[] args) {
        // 测试连接是否正常
        for (int i = 0; i < 10; i++) {
            try {
                // 从池中取出连接
                Connection conn = DruidUtil.ds.getConnection();
                System.out.println(conn.hashCode());
                // 使用完后将连接放入池中(close()是代理过的方法，并不是原生的colse)
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	}
 
	
	


}
