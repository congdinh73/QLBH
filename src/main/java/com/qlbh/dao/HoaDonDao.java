/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.HoaDon;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonDao extends QLBHDao<HoaDon, String> {

    final String UPDATE_SQL = "UPDATE HoaDon SET MaKH=?, MaSP=?, TenSP=?, SoLuong=?, DonGia=?, TongTien=?, NgayMua=? WHERE MaHD=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    final String SELECT_BY_ID = "SELECT * FROM HoaDon where MaHD=?";
    final String INSERT_SQL = "INSERT INTO HoaDon (MaHD, MaKH, MaSP, TenSP, SoLuong, DonGia, TongTien, NgayMua) values(?,?,?,?,?,?,?,?)";
    final String DELETE_BY_ID = "DELETE FROM HoaDon WHERE MaHD=?";
    final String SELECT_BY_KEYWORD = "SELECT * FROM HoaDon where TenSP LIKE?";

    @Override
    public void insert(HoaDon entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaHD(), entity.getMaKh(), entity.getMaSP(), entity.getTenSP(), entity.getSoLuong(), entity.getDonGia(), entity.getTongTien(), entity.getNgayMua());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMaKh(), entity.getMaSP(), entity.getTenSP(), entity.getSoLuong(), entity.getDonGia(), entity.getTongTien(), entity.getNgayMua(), entity.getMaHD());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_BY_ID, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getString("MaHD"));
                entity.setMaKh(rs.getString("MaKH"));
                entity.setMaSP(rs.getString("MaSP"));
                entity.setTenSP(rs.getString("TenSP"));
                entity.setSoLuong(Integer.parseInt(rs.getString("SoLuong")));
                entity.setDonGia(Integer.parseInt(rs.getString("DonGia")));
                entity.setTongTien(Integer.parseInt(rs.getString("TongTien")));
                entity.setNgayMua(rs.getDate("NgayMua"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<HoaDon> selectByKeyWord(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD, "%" + keyword + "%");
    }

    public List<String> selectDoanhThu() {
        final String sql = "select Sum(SoLuong * DonGia) from HoaDon";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Integer> selectYear() {
        String sql = "SELECT DISTINCT Year(Ngaymua) Year FROM HoaDon ORDER BY Year DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
