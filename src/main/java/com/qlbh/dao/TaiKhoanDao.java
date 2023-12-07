/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.TaiKhoan;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TaiKhoanDao extends QLBHDao<TaiKhoan, String> {

    final String UPDATE_SQL = "UPDATE TaiKhoan SET MatKhau=?, VaiTro=? Where MaNV=?";
    final String SELECT_ALL_SQL = "SELECT * FROM TaiKhoan";
    final String SELECT_BY_ID = "SELECT * FROM TaiKhoan where MaNV=?";
    final String INSERT_SQL = "INSERT INTO TaiKhoan (MaNV, MatKhau, VaiTro) values(?,?,?)";

    @Override
    public void insert(TaiKhoan entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaNV(), entity.getPass(), entity.isVaiTro());
    }

    @Override
    public void update(TaiKhoan entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getPass(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TaiKhoan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TaiKhoan selectById(String id) {
        List<TaiKhoan> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TaiKhoan> selectBySql(String sql, Object... args) {
        List<TaiKhoan> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                TaiKhoan entity = new TaiKhoan();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setPass(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
