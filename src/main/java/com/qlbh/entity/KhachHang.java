/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.entity;

/**
 *
 * @author Admin
 */
public class KhachHang {
    private String maKH;
    private String hoTen;
    private String email;
    private String sdt;
    private String addr;

    public KhachHang() {
    }

    public KhachHang(String maKH, String hoTen, String email, String sdt, String addr) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.addr = addr;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
            
            
}
