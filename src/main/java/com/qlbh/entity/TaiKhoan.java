/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.entity;

/**
 *
 * @author Admin
 */
public class TaiKhoan {
    private String maNV;
    private String pass;
    private boolean vaiTro;

    public TaiKhoan() {
    }

    public TaiKhoan(String maNV, String pass, boolean vaiTro) {
        this.maNV = maNV;
        this.pass = pass;
        this.vaiTro = vaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }
    
}
