/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlbh.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class MsgBox {
     public static void alter(Component parent, String mess) {
        JOptionPane.showMessageDialog(parent,mess, "HỆ THỐNG QUẢN LÝ BÁN HÀNG", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static boolean  confirm(Component parent, String mess) {
        int result = JOptionPane.showConfirmDialog(parent, mess, "HỆ THỐNG QUẢN LÝ BÁN HÀNG", JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
        
        return result == JOptionPane.YES_OPTION;
    }
    
    public static String prompt(Component parent, String mess) {
        return JOptionPane.showInputDialog(parent,mess, "HỆ THỐNG QUẢN LÝ BÁN HÀNG", JOptionPane.INFORMATION_MESSAGE);
    }
}
