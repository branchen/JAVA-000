package com.geektime.insertdata;

import lombok.extern.java.Log;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;



@Log
public class TestInsertData {


    // 开启自动提交事务   耗时：3001202 ms  = 50min
    // 添加：rewriteBatchedStatements=true后 耗时：13998ms
    @Test
    public void batchInsertAutoCommit() {
        int batchCount = 1_000_000;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement("insert into t_user(id, user_name, mobile, password, email) values(?, ?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 0; i < batchCount; i++){
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.setString(5,  String.valueOf(i)+"@qq.com");
                ps.addBatch();

                if (i % 1000 == 0) {
                    log.info("count：" + i);
                }
            }

            ps.executeBatch();
            ps.clearBatch();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }


    // 关闭自动提交事务，单个大事务  耗时：152070 ms = 2.5 min
    // 添加：rewriteBatchedStatements=true后 耗时：14607ms
    @Test
    public void batchInsertSingleTx() {
        int batchCount = 1_000_000;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("insert into t_user(id, user_name, mobile, password, email) values(?, ?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 0; i < batchCount; i++) {
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.setString(5,  String.valueOf(i)+"@qq.com");
                ps.addBatch();
            }

            ps.executeBatch();
            ps.clearBatch();

            conn.commit();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }

    // 关闭自动提交事务，10000条/事务 耗时：163975 ms = 2.7 min
    // 添加：rewriteBatchedStatements=true后 耗时：11770ms
    @Test
    public void batchInsertMultiTx() {
        int batchCount = 1_000_000;
        int txBatchCount = 10000;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            ps = conn.prepareStatement("insert into t_user(id, user_name, mobile, password, email) values(?, ?, ?, ?, ?)");

            long start = System.currentTimeMillis();

            for(int i = 1; i <= batchCount; i++) {
                ps.setInt(1, i);
                ps.setString(2, "user_" + i);
                ps.setString(3,  String.valueOf(i));
                ps.setString(4,  String.valueOf(i));
                ps.setString(5,  String.valueOf(i)+"@qq.com");
                ps.addBatch();

                if (i % txBatchCount == 0) {
                    ps.executeBatch();
                    ps.clearBatch();
                    conn.commit();
                }
            }

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }


    // 100w拼接为一条sql：
    // 添加：rewriteBatchedStatements=true后 耗时：10680ms
    // 注意 url链接增加参数：&useSSL=false、 设置my.ini 的 max_allowed_packet 大小。如 64M
    @Test
    public void batchInsertConcatValueString() {
        int batchCount = 1_000_000;

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtils.getConnection();

            conn.setAutoCommit(false);

            String insertSql = "insert into t_user(id, user_name, mobile, password, email) values(?, ?, ?, ?, ?)";

            StringBuilder stringBuilder = new StringBuilder(insertSql);

            long start = System.currentTimeMillis();

            for(int i = 1; i < batchCount; i++) {
                stringBuilder.append(",(?, ?, ?, ?, ?)");
            }

            ps = conn.prepareStatement(stringBuilder.toString());

            for(int i = 1; i <= batchCount; i++) {
                ps.setInt((i - 1) * 5 + 1, i);
                ps.setString((i - 1) * 5 + 2, "user_" + i);
                ps.setString((i - 1) * 5 + 3,  String.valueOf(i));
                ps.setString((i - 1) * 5 + 4,  String.valueOf(i));
                ps.setString((i - 1) * 5 + 5,  String.valueOf(i)+"@qq.com");
            }

            ps.execute();

            conn.commit();

            log.info("耗时：" + (System.currentTimeMillis() - start));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, ps,null);
        }
    }




}
