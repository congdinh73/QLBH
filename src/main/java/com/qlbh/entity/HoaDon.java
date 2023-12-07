/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.entity;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class HoaDon {
    private String maHD;
    private String MaKh;
    private String maSP;
    private String tenSP;
    private Date ngayMua;
    int soLuong;
    int donGia;
    int tongTien = soLuong * donGia;

    public HoaDon() {
    }

    public HoaDon(String maHD, String MaKh, String maSP, String tenSP, Date ngayMua, int soLuong, int donGia) {
        this.maHD = maHD;
        this.MaKh = MaKh;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.ngayMua = ngayMua;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaKh() {
        return MaKh;
    }

    public void setMaKh(String MaKh) {
        this.MaKh = MaKh;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    
    
    
}
