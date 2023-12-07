/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.SanPham;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamDao extends QLBHDao<SanPham, String> {

    final String UPDATE_SQL = "UPDATE SanPham SET MaMH=?, TenMH=?, TenSP=?, SoLuong=?, GiaTien=?, MoTa=?, Hinh=? Where MaSP=?";
    final String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    final String SELECT_BY_ID = "SELECT * FROM SanPham where MaSP=?";
    final String INSERT_SQL = "INSERT INTO SanPham (MaSP, MaMH, TenMH ,TenSP, SoLuong, GiaTien, MoTa, Hinh) values(?,?,?,?,?,?,?,?)";
    final String DELETE_SQL = "DELETE from SanPham  where MaSP=?";    
    final String SELECT_BY_KEYWORD = "SELECT * FROM SanPham where TenSP LIKE?";
     final String SELECT_BY_MaMH = "SELECT * FROM SanPham where MaMH LIKE?";

    @Override
    public void insert(SanPham entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaSP(),entity.getMaMH() ,entity.getTenMH(),entity.getTenSP(), entity.getSoLuong(), entity.getGiaTien(), entity.getMoTa(), entity.getHinh());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMaMH(),entity.getTenMH(),entity.getTenSP(), entity.getSoLuong(), entity.getGiaTien(), entity.getMoTa(), entity.getHinh(), entity.getMaSP());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<SanPham> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSP(rs.getString("MaSP"));
                entity.setMaMH(rs.getString("MaMH"));
                entity.setTenMH(rs.getString("TenMH"));
                entity.setTenSP(rs.getString("TenSP"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setGiaTien(rs.getInt("GiaTien"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setHinh(rs.getString("Hinh"));

                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
   public List<SanPham> selectByKeyWord(String keyword) {
        return selectBySql(SELECT_BY_KEYWORD,"%"+keyword+"%");
    }
   
   public List<SanPham> selectByMaMH(String keyword) {
        return selectBySql(SELECT_BY_MaMH,"%"+keyword+"%");
    }
}
