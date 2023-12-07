/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.qlbh.ui;

import com.qlbh.dao.HoaDonDao;
import com.qlbh.dao.MatHangDao;
import com.qlbh.dao.SanPhamDao;
import com.qlbh.entity.HoaDon;
import com.qlbh.entity.SanPham;
import com.qlbh.utils.Auth;
import com.qlbh.utils.MsgBox;
import com.qlbh.utils.XDate;
import com.qlbh.utils.XImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Admin
 */
public class HoaDonDialog extends javax.swing.JDialog {

    HoaDonDao dao = new HoaDonDao();
    SanPhamDao spDao = new SanPhamDao();
    MatHangDao mhDao = new MatHangDao();
    int row = 0;

    /**
     * Creates new form HoaDonDialog
     */
    public HoaDonDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();

    }

    void init() {
        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QUẢN LÝ HOÁ ĐƠN");
        fillTableSanPham();
        fillTable();
        fillCboMatHang();
    }

    void fillCboMatHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMatHang.getModel();
        model.removeAllElements();
        List<String> list = mhDao.selectID();
        for (String mh : list) {
            model.addElement(mh);
        }
        cboMatHang.setSelectedIndex(0);
    }

    void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);

        try {
            String maMH = (String) cboMatHang.getSelectedItem();
            List<SanPham> list = spDao.selectByMaMH(maMH);
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSP(), sp.getTenSP(), sp.getGiaTien()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<HoaDon> list = dao.selectByKeyWord(keyword);
            for (HoaDon hd : list) {
                Object[] row = {hd.getMaHD(), hd.getMaSP(), hd.getTenSP(), hd.getSoLuong(), hd.getDonGia(), hd.getTongTien(), hd.getNgayMua()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(txtMaHD.getText());
        hd.setMaKh(txtMaKH.getText());
        hd.setNgayMua(XDate.toDate(txtNgayMua.getText()));
        hd.setMaSP(txtMaSP.getText());
        hd.setTenSP(txtTenSP.getText());
        hd.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        hd.setDonGia(Integer.parseInt(txtDonGia.getText()));
        hd.setTongTien(Integer.parseInt(lblTongTien.getText()));
        return hd;
    }

    void setForm(HoaDon model) {
        txtMaHD.setText(model.getMaHD());
        txtMaKH.setText(model.getMaKh());
        txtNgayMua.setText(XDate.toString(model.getNgayMua()));
        txtMaSP.setText(model.getMaSP());
        txtTenSP.setText(model.getTenSP());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtDonGia.setText(String.valueOf(model.getDonGia()));
        lblTongTien.setText(String.valueOf(model.getTongTien()));
    }

    void setSP(SanPham model) {
        txtMaSP.setText(model.getMaSP());
        txtTenSP.setText(model.getTenSP());
        txtDonGia.setText(String.valueOf(model.getGiaTien()));
    }

    void editSP() {
        try {
            String maSP = (String) tblSanPham.getValueAt(this.row, 0);
            SanPham model = spDao.selectById(maSP);
            if (model != null) {
                setSP(model);
            }
        } catch (Exception e) {
            System.out.println("tblSanPham: " + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void edit() {
        try {
            String maHD = (String) tblHoaDon.getValueAt(this.row, 0);
            HoaDon model = dao.selectById(maHD);
            if (model != null) {
                setForm(model);
            }
            tab.setSelectedIndex(1);
        } catch (Exception e) {
            System.out.println("tblHoaDon: " + e.toString());
            MsgBox.alter(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void them() {
        HoaDon hd = getForm();
        try {
            dao.insert(hd);
            this.fillTable();
            MsgBox.alter(this, "Thêm mới thành công");
        } catch (Exception e) {
            MsgBox.alter(this, "Thêm mới thất bại");
        }
    }

    void update() {
        HoaDon hd = getForm();
        try {
            dao.update(hd);
            this.fillTable();
            MsgBox.alter(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alter(this, "Cập nhật thất bại");
        }
    }

    void xoa() {
        String maHD = txtMaHD.getText();
        if (MsgBox.confirm(this, "Bạn thực sự muốn xoá hóa đơn này")) {
            try {
                dao.delete(maHD);
                this.fillTable();
                MsgBox.alter(this, "Xoá thành công");
            } catch (Exception e) {
                System.out.println("Xoa: " + e.toString());
                MsgBox.alter(this, "Xoá thất bại");
            }
        }
    }

    void clearForm() {
        HoaDon hd = new HoaDon();
        this.setForm(hd);
        try {
            hd.setSoLuong(Integer.parseInt(""));
            hd.setTongTien(Integer.parseInt(""));
            hd.setDonGia(Integer.parseInt(""));
            hd.setNgayMua(XDate.now());
        } catch (Exception e) {
        }

        this.row = - 1;
    }

    public void selectTab(int index) {
        tab.setSelectedIndex(1);
    }

    void timKiem() {
        this.fillTable();
        this.clearForm();
        this.row = - 1;

    }

    boolean validateForm() {

        if (txtMaHD.getText().isEmpty() || txtSoLuong.getText().isEmpty() || txtMaKH.getText().isEmpty()) {
            MsgBox.alter(this, "Không được để trống các trường nhập liệu");
            return false;
        }

        if (!txtSoLuong.getText().matches("([0-9]|[1-9][0-9]|[1-9][0-9][0-9])")) {
            MsgBox.alter(this, "Số lượng phải lớn hơn 0");
            txtSoLuong.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(txtSoLuong.getText());
        } catch (NumberFormatException e) {
            MsgBox.alter(this, "Số lượng phải nhập số");
            return false;
        }

        return true;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        ds = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtDonGia = new javax.swing.JTextField();
        lblTongTien = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnTinhTien = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnXuatHoaDon = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cboMatHang = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtNgayMua = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 204, 255));

        tab.setBackground(new java.awt.Color(255, 255, 255));

        ds.setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Đơn giá", "Tổng tiền", "Ngày mua"
            }
        ));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/find.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel8.setText("Nhập để tìm kiếm");

        javax.swing.GroupLayout dsLayout = new javax.swing.GroupLayout(ds);
        ds.setLayout(dsLayout);
        dsLayout.setHorizontalGroup(
            dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dsLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTimKiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
        );
        dsLayout.setVerticalGroup(
            dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dsLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTimKiem)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dsLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(37, 37, 37)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tab.addTab("Danh sách", ds);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Mã hóa đơn");

        jLabel2.setText("Mã khách hàng");

        jLabel3.setText("Mã sản phẩm");

        jLabel4.setText("Tên sản phẩm");

        jLabel5.setText("Số lượng");

        jLabel6.setText("Đơn giá");

        jLabel7.setText("Tổng tiền:");

        txtTenSP.setEditable(false);

        txtDonGia.setEditable(false);

        lblTongTien.setForeground(new java.awt.Color(255, 51, 51));
        lblTongTien.setText("0");

        txtMaSP.setEditable(false);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn giá"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanPhamMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnTinhTien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/tinhtien_1.png"))); // NOI18N
        btnTinhTien.setText("Tính tiền");
        btnTinhTien.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnTinhTien.setIconTextGap(10);
        btnTinhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhTienActionPerformed(evt);
            }
        });

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnSua.setIconTextGap(10);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xoá");
        btnXoa.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnXoa.setIconTextGap(10);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/refresh_1.png"))); // NOI18N
        btnMoi.setText("New");
        btnMoi.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnMoi.setIconTextGap(10);
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXuatHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/print.png"))); // NOI18N
        btnXuatHoaDon.setText("Xuất hóa đơn");
        btnXuatHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnXuatHoaDon.setIconTextGap(10);
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });

        jLabel10.setText("Mã mặt hàng");

        cboMatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMatHangActionPerformed(evt);
            }
        });

        jLabel12.setText("Ngày mua");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jLabel12))
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongTien)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaKH)
                            .addComponent(txtMaHD)
                            .addComponent(txtTenSP)
                            .addComponent(txtSoLuong)
                            .addComponent(txtDonGia)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                            .addComponent(txtNgayMua))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboMatHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatHoaDon))
                    .addComponent(btnTinhTien))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboMatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTinhTien)
                    .addComponent(jLabel7)
                    .addComponent(lblTongTien))
                .addGap(47, 47, 47)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnXuatHoaDon))
                .addGap(19, 19, 19))
        );

        tab.addTab("Thông tin", jPanel3);

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("QUẢN LÝ HOÁ ĐƠN");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/qlbh/icon/bill_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11)
                .addGap(122, 122, 122)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tab)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tab)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblSanPham.rowAtPoint(evt.getPoint());
            editSP();
        }
    }//GEN-LAST:event_tblSanPhamMousePressed

    private void btnTinhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTinhTienActionPerformed
        // TODO add your handling code here:
        try {
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            int donGia = Integer.parseInt(txtDonGia.getText());

            int tongTien = soLuong * donGia;
            lblTongTien.setText(String.valueOf(tongTien));
        } catch (NumberFormatException e) {
            MsgBox.alter(this, "Số lượng phải nhập số!");
        }
    }//GEN-LAST:event_btnTinhTienActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblHoaDon.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblHoaDonMousePressed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed
        // TODO add your handling code here:
        String maHD = txtMaHD.getText();
        String maKH = txtMaKH.getText();
        String maSP = txtMaSP.getText();
        String tenSP = txtTenSP.getText();
        String soLuong = txtSoLuong.getText();
        String donGia = txtDonGia.getText();
        String tongTien = lblTongTien.getText();
        String ngayMua = txtNgayMua.getText();

        try {
            FileOutputStream outStream = new FileOutputStream("HoaDon.docx");
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph paragraph = doc.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontSize(20);
            run.setText("HÓA ĐƠN");
            doc.createParagraph().createRun().addBreak();
            doc.createParagraph().createRun().setText("******************");
            doc.createParagraph().createRun().setText("MÃ HÓA ĐƠN: " + maHD);
            doc.createParagraph().createRun().setText("MÃ SẢN PHẨM: " + maSP);
            doc.createParagraph().createRun().setText("TÊN SẢN PHẨM: " + tenSP);
            doc.createParagraph().createRun().setText("SỐ LƯỢNG: " + soLuong);
            doc.createParagraph().createRun().setText("ĐƠN GIÁ: " + donGia);
            doc.createParagraph().createRun().setText("NGÀY MUA: " + ngayMua);
            doc.createParagraph().createRun().setText("NHÂN VIÊN: " + Auth.user.getMaNV());
            doc.createParagraph().createRun().setText("------------------");
            doc.createParagraph().createRun().setText("THÀNH TIỀN: " + tongTien);
            doc.createParagraph().createRun().setText("THANH TOÁN: " + tongTien);
            doc.createParagraph().createRun().setText("------------------");
            doc.createParagraph().createRun().setText("******************");
            doc.createParagraph().createRun().setText("Chúc quý khách một ngày vui vẻ");
            doc.write(outStream);
            outStream.close();
            doc.close();
            them();
            MsgBox.alter(this, "Xuất hoá đơn thành công");
        } catch (Exception e) {
            System.out.println("XĐ: " + e.toString());
            MsgBox.alter(this, "Xuất hoá đơn thất bại");
        }
    }//GEN-LAST:event_btnXuatHoaDonActionPerformed

    private void cboMatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMatHangActionPerformed
        // TODO add your handling code here:
        fillTableSanPham();
    }//GEN-LAST:event_cboMatHangActionPerformed

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
            java.util.logging.Logger.getLogger(HoaDonDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonDialog dialog = new HoaDonDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTinhTien;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatHoaDon;
    private javax.swing.JComboBox<String> cboMatHang;
    private javax.swing.JPanel ds;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtNgayMua;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
