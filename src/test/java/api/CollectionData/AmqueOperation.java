package api.CollectionData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对amque库里部分表进行增删改查的数据访问对象
 * DAO是数据访问对象
 */
public class AmqueOperation {
    //    更新mt_batch_status中batch_status=6的batchnum为当天
    public int updateMt_batch_status() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String update_mt_batch_status = "update mt_batch_status set batchnum=? where batch_status=6 ;";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(update_mt_batch_status);
            stmt.setString(1, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    public int deleteTodayMt_loan() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String delete_mt_batch_status = "delete from mt_loan where batchnum = ? ; ";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(delete_mt_batch_status);
            stmt.setString(1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
            System.out.println(stmt);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    public int deleteTodayMt_customer() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String delete_mt_customer = "delete from mt_customer where batchnum = ? ; ";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(delete_mt_customer);
            stmt.setString(1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
            System.out.println(stmt);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    public int deleteTodayMt_contact() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String delete_mt_contact = "delete from mt_contact where batchnum = ? ; ";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(delete_mt_contact);
            stmt.setString(1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
            System.out.println(stmt);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    public int deleteAllLoan_durable() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String delete_loan_durable = "delete from loan_durable; ";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(delete_loan_durable);
            System.out.println(stmt);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    public int deleteAllLoan_durable_md() {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String delete_loan_durable_md = "delete from loan_durable_md; ";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(delete_loan_durable_md);
            System.out.println(stmt);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }

    //    更新BI推的风险数据
    public int updateMt_collection_related_loans(Integer loan_id) {
        int rows = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        String update_mt_collection_related_loans = "update mt_collection_related_loans set batchnum=? ,loan_id=" + loan_id + " where id=1 ;";
        try {
            conn = DButil.GetAmquecCnnection();
            stmt = conn.prepareStatement(update_mt_collection_related_loans);
            stmt.setString(1, new SimpleDateFormat("yyyyMMdd").format(new Date()));
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButil.Close(res, conn, stmt);
        }
        return rows;
    }
}

