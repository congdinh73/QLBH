/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.qlbh.ui;

import com.qlbh.dao.MatHangDao;
import com.qlbh.dao.SanPhamDao;
import com.qlbh.entity.MatHang;
import com.qlbh.entity.SanPham;
import com.qlbh.utils.Auth;
import com.qlbh.utils.MsgBox;
import com.qlbh.utils.XImage;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SanPhamDialog extends javax.swing.JDialog {

    JFileChooser fileChooser = new JFileChooser();
    MatHangDao mhDao = new MatHangDao();
    SanPhamDao spDao = new SanPhamDao();
    SanPham sp = new SanPham();
    MatHang mh = new MatHang();
    int row = 0;

    /**
     * Creates new form SanPhamDialog
     */
    public SanPhamDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();

        if (!Auth.isManager()) {
            tab.removeTabAt(2);

        }
    }

    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QUẢN LÝ HOÁ ĐƠN");
        fillTableSanPham();
        fillTableMatHang();
        fillTableDSMatHang();
        fillTableSanPham();
    }

    MatHang getFormMH() {
        MatHang mh = new MatHang();

        mh.setMaSP(txtMaMatHang.getText());
        mh.setTenMH(txtTenMatHang.getText());
        mh.setMoTa(txtMieuTa.getText());

        return mh;
    }

    void setFormMH(MatHang model) {

        txtMaMatHang.setText(model.getMaSP());
        txtTenMatHang.setText(model.getTenMH());
        txtMieuTa.setText(model.getMoTa());

    }

    void setDSMH(MatHang model) {
        txtMaMH.setText(model.getMaSP());
        txtTenMH.setText(model.getTenMH());
    }

    SanPham getFormSP() {
        SanPham sp = new SanPham();

        sp.setMaSP(txtMaSP.getText());
        sp.setMaMH(txtMaMH.getText());
        sp.setTenMH(txtTenMH.getText());
        sp.setTenSP(txtTenSP.getText());
        sp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        sp.setGiaTien(Integer.parseInt(txtGiaTien.getText()));
        sp.setMoTa(txtMoTa.getText());
        sp.setHinh(lblHinh.getToolTipText());

        return sp;
    }

    void setFormSP(SanPham model) {
        txtMaMH.setText(model.getMaMH());
        txtTenMH.setText(model.getTenMH());
        txtMaSP.setText(model.getMaSP());
        txtTenSP.setText(model.getTenSP());
        txtSoLuong.setText((String.valueOf(model.getSoLuong())));
        txtGiaTien.setText(String.valueOf(model.getGiaTien()));
        txtMoTa.setText(model.getMoTa());
        lblHinh.setToolTipText(model.getHinh());
        if (model.getHinh() != null) {
            lblHinh.setIcon(XImage.read(model.getHinh()));
        }
    }

    void fillTableDSMatHang() {
        DefaultTableModel model = (DefaultTableModel) tblDSMatHang.getModel();
        model.setRowCount(0);
        try {
            List<MatHang> list = mhDao.selectAll();
            for (MatHang mh : list) {
                Object[] row = {mh.getMaSP(), mh.getTenMH()};
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("tblMatHang" + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void fillTableMatHang() {
        DefaultTableModel model = (DefaultTableModel) tblMatHang.getModel();
        model.setRowCount(0);
        try {
            List<MatHang> list = mhDao.selectAll();
            for (MatHang mh : list) {
                Object[] row = {mh.getMaSP(), mh.getTenMH()};
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("tblMatHang" + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        try {
            String keyword = txtTimKiem.getText();
            List<SanPham> list = spDao.selectByKeyWord(keyword);
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSP(), sp.getMaMH(), sp.getTenMH(), sp.getTenSP(), sp.getSoLuong(), sp.getGiaTien(), sp.getMoTa(), sp.getHinh()};
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("tblMatHang" + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void themMH() {
        MatHang mh = getFormMH();
        try {
            mhDao.insert(mh);
            this.fillTableMatHang();
            this.fillTableDSMatHang();
            MsgBox.alter(this, "Thêm mới thành công");
        } catch (Exception e) {
            System.out.println("them:" + e.toString());
            if (txtMaMatHang.getText().equals(mh.getMaSP())) {
                 MsgBox.alter(this, "Đã tồn tại sản mặt hàng");
            } else {
                 MsgBox.alter(this, "Thêm mới thất bại");
            }
           
        }
    }

    void themSP() {
        SanPham sp = getFormSP();
        try {
            spDao.insert(sp);
            this.fillTableSanPham();
            MsgBox.alter(this, "Thêm mới thành công");
        } catch (Exception e) {
            System.out.println("themSP:" + e.toString());
            if (txtMaSP.getText().equals(sp.getMaSP())) {
                MsgBox.alter(this, "Đã tồn tại sản phẩm");
            } else {
                MsgBox.alter(this, "Thêm mới thất bại");
            }          
        }
    }

    void xoaMH() {
        String maMH = txtMaMatHang.getText();
        try {
            mhDao.delete(maMH);
            this.fillTableMatHang();
            this.fillTableDSMatHang();
            this.fillTableSanPham();
            MsgBox.alter(this, "Xoá thành công");
        } catch (Exception e) {
            System.out.println("Xoa: " + e.toString());
            MsgBox.alter(this, "Xoá thất bại");
        }
    }

    void xoaSP() {
        String maSP = txtMaSP.getText();
        try {
            spDao.delete(maSP);
            this.fillTableSanPham();
            MsgBox.alter(this, "Xoá thành công");
        } catch (Exception e) {
            System.out.println("XoaSP: " + e.toString());
            MsgBox.alter(this, "Xoá thất bại");
        }
    }

    void clearFormMH() {
        MatHang nv = new MatHang();
        this.setFormMH(nv);
        this.row = -1;
    }

    void clearFormSP() {
        SanPham sp = new SanPham();
        this.setFormSP(sp);
        this.row = -1;
    }

    void updateMH() {
        MatHang mh = getFormMH();
        try {
            mhDao.update(mh);
            this.fillTableMatHang();
            MsgBox.alter(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alter(this, "Cập nhật thất bại");
        }
    }

    void updateSP() {
        SanPham sp = getFormSP();
        try {
            spDao.update(sp);
            this.fillTableSanPham();
            MsgBox.alter(this, "Cập nhật thành công");
        } catch (Exception e) {
            System.out.println("UpdateSP: " + e.toString());
            MsgBox.alter(this, "Cập nhật thất bại");
        }
    }

    void editMatHangDS() {
        try {
            String maMH = (String) tblDSMatHang.getValueAt(this.row, 0);
            MatHang model = mhDao.selectById(maMH);
            if (model != null) {
                setDSMH(model);
            }
        } catch (Exception e) {
            System.out.println("tblMatHang: " + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void editSP() {
        try {
            String maSP = (String) tblSanPham.getValueAt(this.row, 0);
            SanPham model = spDao.selectById(maSP);
            if (model != null) {
                setFormSP(model);
                tab.setSelectedIndex(1);
            }
        } catch (Exception e) {
            System.out.println("tblSanPham: " + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void editMatHang() {
        try {
            String maMH = (String) tblMatHang.getValueAt(this.row, 0);
            MatHang model = mhDao.selectById(maMH);
            if (model != null) {
                setFormMH(model);
            }
        } catch (Exception e) {
            System.out.println("tblMatHang: " + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (XImage.save(file)) {
                lblHinh.setIcon(XImage.read(file.getName()));
                lblHinh.setToolTipText(file.getName());
            }

        }
    }
    
    void findSanPham() {
         this.fillTableSanPham();
        this.clearFormSP();
        this.row = - 1;
    }

    public void selectTab(int index) {
        tab.setSelectedIndex(index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTimSanPham = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnChonAnh = new javax.swing.JButton();
        txtMaMH = new javax.swing.JTextField();
        txtTenMH = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtGiaTien = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDSMatHang = new javax.swing.JTable();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        btnXoaSP = new javax.swing.JButton();
        btnMoiSP = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMaMatHang = new javax.swing.JTextField();
        txtTenMatHang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMieuTa = new javax.swing.JTextField();
        btnThemMH = new javax.swing.JButton();
        btnSuaMH = new javax.swing.JButton();
        btnXoaMH = new javax.swing.JButton();
        btnMoiMH = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMatHang = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tab.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Mã MH", "Tên MH", "Tên SP", "Số lượng", "Giá tiền", "Mô tả", "Hình"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnTimSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/find.png"))); // NOI18N
        btnTimSanPham.setText("Tìm kiếm");
        btnTimSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimSanPhamActionPerformed(evt);
            }
        });

        jLabel14.setText("Nhập để tìm kiếm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel14)
                                .addGap(18, 18, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimKiem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(btnTimSanPham)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimSanPham)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        tab.addTab("Dannh sách", jPanel1);

        jLabel1.setText("Mặt hàng");

        jLabel2.setText("Tên mặt hàng");

        jLabel3.setText("Mã mặt hàng");

        jLabel4.setText("Mã sản phẩm");

        jLabel5.setText("Tên sản phẩm");

        jLabel6.setText("Số lượng");

        jLabel7.setText("Giá tiền");

        jLabel8.setText("Mô tả");

        btnChonAnh.setText("Chọn ảnh");
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        txtMaMH.setEditable(false);
        txtMaMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaMHActionPerformed(evt);
            }
        });

        txtTenMH.setEditable(false);
        txtTenMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenMHActionPerformed(evt);
            }
        });

        tblDSMatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã mặt hàng", " Tên mặt hàng"
            }
        ));
        tblDSMatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDSMatHangMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblDSMatHang);

        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/add.png"))); // NOI18N
        btnThemSP.setText("Thêm");
        btnThemSP.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnThemSP.setIconTextGap(10);
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/edit.png"))); // NOI18N
        btnSuaSP.setText("Sửa");
        btnSuaSP.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnSuaSP.setIconTextGap(10);
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        btnXoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/delete.png"))); // NOI18N
        btnXoaSP.setText("Xóa");
        btnXoaSP.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnXoaSP.setIconTextGap(10);
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        btnMoiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/refresh_1.png"))); // NOI18N
        btnMoiSP.setText("New");
        btnMoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiSPActionPerformed(evt);
            }
        });

        jPanel4.setAutoscrolls(true);

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel13.setText("Ảnh sản phẩm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(btnChonAnh))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel13)))
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 509, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addContainerGap(107, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                .addComponent(txtTenMH, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMaSP, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtGiaTien, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(txtMaMH, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnThemSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMoiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaMH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenMH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnChonAnh)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemSP)
                    .addComponent(btnSuaSP)
                    .addComponent(btnXoaSP)
                    .addComponent(btnMoiSP))
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab.addTab("Sản phẩm", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Mã mặt hàng");

        jLabel10.setText("Tên mặt hàng");

        jLabel11.setText("Mô tả");

        btnThemMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/add.png"))); // NOI18N
        btnThemMH.setText("Thêm");
        btnThemMH.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnThemMH.setIconTextGap(10);
        btnThemMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMHActionPerformed(evt);
            }
        });

        btnSuaMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/edit.png"))); // NOI18N
        btnSuaMH.setText("Sửa");
        btnSuaMH.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnSuaMH.setIconTextGap(10);
        btnSuaMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaMHActionPerformed(evt);
            }
        });

        btnXoaMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/delete.png"))); // NOI18N
        btnXoaMH.setText("Xóa");
        btnXoaMH.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnXoaMH.setIconTextGap(10);
        btnXoaMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaMHActionPerformed(evt);
            }
        });

        btnMoiMH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/refresh_1.png"))); // NOI18N
        btnMoiMH.setText("New");
        btnMoiMH.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnMoiMH.setIconTextGap(10);
        btnMoiMH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiMHActionPerformed(evt);
            }
        });

        jLabel12.setText("Mặt hàng");

        tblMatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã mặt hàng", "Tên mặt hàng"
            }
        ));
        tblMatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMatHangMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblMatHang);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel11)))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMieuTa, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThemMH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSuaMH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoaMH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoiMH, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(71, 71, 71))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtMaMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtTenMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMieuTa, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemMH)
                            .addComponent(btnSuaMH)
                            .addComponent(btnXoaMH)
                            .addComponent(btnMoiMH))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 221, Short.MAX_VALUE))))
        );

        tab.addTab("Mặt hàng", jPanel3);

        jPanel6.setBackground(new java.awt.Color(0, 204, 255));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/wishlist.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(265, 265, 265)
                    .addComponent(jLabel16)
                    .addContainerGap(265, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(20, 20, 20))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(57, 57, 57)
                    .addComponent(jLabel16)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab)
                .addContainerGap())
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenMHActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtTenMHActionPerformed

    private void txtMaMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaMHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaMHActionPerformed

    private void btnThemMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMHActionPerformed
        // TODO add your handling code here:
        themMH();
        clearFormMH();
    }//GEN-LAST:event_btnThemMHActionPerformed

    private void btnSuaMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaMHActionPerformed
        // TODO add your handling code here:
        updateMH();

    }//GEN-LAST:event_btnSuaMHActionPerformed

    private void btnXoaMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaMHActionPerformed
        // TODO add your handling code here:
        xoaMH();
        clearFormMH();
    }//GEN-LAST:event_btnXoaMHActionPerformed

    private void btnMoiMHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiMHActionPerformed
        // TODO add your handling code here:
        clearFormMH();
    }//GEN-LAST:event_btnMoiMHActionPerformed

    private void tblDSMatHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSMatHangMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDSMatHang.rowAtPoint(evt.getPoint());
            editMatHangDS();
        }
    }//GEN-LAST:event_tblDSMatHangMousePressed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        themSP();
        clearFormSP();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        updateSP();
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        // TODO add your handling code here:
        xoaSP();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void btnMoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiSPActionPerformed
        // TODO add your handling code here:
        clearFormSP();
    }//GEN-LAST:event_btnMoiSPActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        // TODO add your handling code here:
        chonAnh();
    }//GEN-LAST:event_btnChonAnhActionPerformed

    private void tblMatHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMatHangMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblMatHang.rowAtPoint(evt.getPoint());
            editMatHang();
        }
    }//GEN-LAST:event_tblMatHangMousePressed

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblSanPham.rowAtPoint(evt.getPoint());
            editSP();
        }
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void btnTimSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimSanPhamActionPerformed
        // TODO add your handling code here:
        findSanPham();
    }//GEN-LAST:event_btnTimSanPhamActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SanPhamDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SanPhamDialog dialog = new SanPhamDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnMoiMH;
    private javax.swing.JButton btnMoiSP;
    private javax.swing.JButton btnSuaMH;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemMH;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimSanPham;
    private javax.swing.JButton btnXoaMH;
    private javax.swing.JButton btnXoaSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblDSMatHang;
    private javax.swing.JTable tblMatHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaMH;
    private javax.swing.JTextField txtMaMatHang;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtMieuTa;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenMH;
    private javax.swing.JTextField txtTenMatHang;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
