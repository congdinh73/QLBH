/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.NhanVien;
import com.qlbh.entity.TaiKhoan;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienDao extends QLBHDao<NhanVien, String> {

    final String INSERT_SQL = "INSERT INTO NhanVien (MaNV, HoVaTen, Email, SDT, Address  ) values(?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NhanVien SET HoVaTen=?, Email=?, SDT=?, Address=? Where MaNV=?";
    final String DELETE_SQL = "DELETE from NhanVien  where MaNV=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    final String SELECT_BY_ID = "SELECT * FROM NhanVien where MaNV=?";
    final String SELECT_BY_KEYWORD = "SELECT * FROM NhanVien where HoVaTen LIKE?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getHoTen(), entity.getEmail(), entity.getSdt(), entity.getAddr());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getEmail(), entity.getSdt(), entity.getAddr(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHoTen(rs.getString("HoVaTen"));
                entity.setEmail(rs.getString("Email"));
                entity.setSdt(rs.getString("SDT"));
                entity.setAddr(rs.getString("Address"));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> selectMaNV() {
        final String sql = "select MaNV from NhanVien where not exists(select * from TaiKhoan where TaiKhoan.MaNV = NhanVien.MaNV)";
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
    
    public List<NhanVien> selectByKeyWord(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD,"%"+keyword+"%");
    }
    

}
