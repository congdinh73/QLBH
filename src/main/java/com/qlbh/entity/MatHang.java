/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.entity;

/**
 *
 * @author Admin
 */
public class MatHang {

    private String maSP;
    private String tenMH;
    private String moTa;

    public MatHang() {
    }

    public MatHang(String maSP, String tenMH, String moTaS) {
        this.maSP = maSP;
        this.tenMH = tenMH;
        this.moTa = moTaS;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTaS) {
        this.moTa = moTaS;
    }

    

}
