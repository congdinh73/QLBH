/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.KhachHang;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangDao extends QLBHDao<KhachHang, String>{
    final String INSERT_SQL = "INSERT INTO KhachHang (MaKH, HoVaTen, Email, SDT, DiaChi) values(?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE KhachHang SET HoVaTen=?, Email=?, SDT=?, DiaChi=? Where MaKH=?";
    final String DELETE_SQL = "DELETE from KhachHang  where MaKH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    final String SELECT_BY_ID = "SELECT * FROM KhachHang where MaKH=?";
    final String SELECT_BY_KEYWORD = "SELECT * FROM KhachHang where HoVaTen LIKE?";

    @Override
    public void insert(KhachHang entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaKH(), entity.getHoTen(), entity.getEmail(), entity.getSdt(), entity.getAddr());
    }

    @Override
    public void update(KhachHang entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getEmail(), entity.getSdt(), entity.getAddr(), entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setHoTen(rs.getString("HoVaTen"));
                entity.setEmail(rs.getString("Email"));
                entity.setSdt(rs.getString("SDT"));
                entity.setAddr(rs.getString("DiaChi"));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<KhachHang> selectByKeyWord(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD,"%"+keyword+"%");
    }
}
