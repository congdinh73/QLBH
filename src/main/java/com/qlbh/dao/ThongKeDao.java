/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author _nqt188
 */
public class ThongKeDao {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getTonKho(String maMH) {
        String sql = "{CALL Tk_TonKho(?)}";
        String[] cols = {"MaMH", "MaSP", "SoLuong", "DaBan", "TonKho"};
        return getListOfArray(sql, cols, maMH);
    }

    public List<Object[]> getDoanhThu(String maMH) {
        String sql = "{CALL hd_ThongKe(?)}";
        String[] cols = {"MaMH", "MaSP", "TenSP", "TongSoLuong", "GiaTien", "DoanhThu"};
        return getListOfArray(sql, cols, maMH);
    }

    public List<Object[]> getDoanhThuNam(Integer nam) {
        String sql = "{CALL hd_Thongketheonam(?)}";
        String[] cols = {"MaSP", "TenSP", "TongSoLuong", "GiaTien", "DoanhThu", "CaoNhat", "ThapNhat", "TrungBinh"};
        return getListOfArray(sql, cols, nam);
    }

    public List<Object[]> getDoanhThuTheoThang(int year, int month) {
        String sql = "{CALL hd_Thongketheothang(?,?)}";
        String[] cols = {"MaSP", "TenSP", "TongSoLuong", "GiaTien", "DoanhThu","CaoNhat", "ThapNhat", "TrungBinh"};
        return getListOfArray(sql, cols, year, month);
    }
}
