/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.dao;

import com.qlbh.entity.MatHang;
import com.qlbh.utils.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MatHangDao extends QLBHDao<MatHang, String> {

    final String UPDATE_SQL = "UPDATE MatHang SET TenMatHang=?, MoTa=? Where MaMH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM MatHang";
    final String SELECT_BY_ID = "SELECT * FROM MatHang where MaMH=?";
    final String INSERT_SQL = "INSERT INTO MatHang (MaMH, TenMatHang, MoTa) values(?,?,?)";
    final String DELETE_SQL = "DELETE from MatHang  where MaMH=?";
    final String SELECT_ID = "SELECT MaMH FROM MatHang";
   

    @Override
    public void insert(MatHang entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaSP(), entity.getTenMH(), entity.getMoTa());
    }

    @Override
    public void update(MatHang entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenMH(), entity.getMoTa(), entity.getMaSP());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<MatHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public MatHang selectById(String id) {
         List<MatHang> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MatHang> selectBySql(String sql, Object... args) {
        List<MatHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                MatHang entity = new MatHang();
                entity.setMaSP(rs.getString("MaMH"));
                entity.setTenMH(rs.getString("TenMatHang"));
                entity.setMoTa(rs.getString("MoTa"));
               
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> selectID() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(SELECT_ID);
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
