Create Database QLBH
use QLBH
drop Database QLBH


CREATE TABLE NhanVien (
    MaNV nvarchar(50) PRIMARY KEY,
    HoVaTen VARCHAR(50),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    Address TEXT,
);


CREATE TABLE KhachHang (
    MaKH nvarchar(50) PRIMARY KEY,
    HoVaTen VARCHAR(50),
    Email VARCHAR(100),
    SDT VARCHAR(15),
    DiaChi TEXT
);

CREATE TABLE MatHang (
    MaMH nvarchar(50) PRIMARY KEY,
    TenMatHang NVARCHAR(50),
    MoTa TEXT,
);

Create table SanPham (
	MaSP nvarchar(50) primary key,
	MaMH nvarchar(50),
	TenSP nvarchar(50),
	SoLuong int,
	GiaTien int,
	MoTa text,
	Hinh text,
	FOREIGN KEY (MaMH) REFERENCES MatHang(MaMH),
)

CREATE TABLE TaiKhoan (
    MaNV nvarchar(50) PRIMARY KEY,
    Password VARCHAR(50),
	VaiTro bit,
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    
);
drop TABLE HoaDon
CREATE TABLE HoaDon (
    MaHD nvarchar(50) PRIMARY KEY,
    MaKH nvarchar(50),
    MaSP nvarchar(50),
	TenSP nvarchar(50),
    SoLuong INT,
    DonGia int,
    TongTien int,
	NgayMua date,
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);


alter table TaiKhoan 
add constraint FK_Xoa_MaNV
foreign key (MaNV) references NhanVien(MaNV) on delete cascade

alter table SanPham 
add constraint FK_Xoa_SP
foreign key (MaMH) references MatHang(MaMH) on delete cascade





-- Drop the existing procedure if it exists
IF OBJECT_ID('hd_Thongke', 'P') IS NOT NULL
BEGIN
    DROP PROCEDURE hd_Thongke;
END;




-- Drop the existing procedure if it exists
IF OBJECT_ID('hd_Thongke', 'P') IS NOT NULL
BEGIN
    DROP PROCEDURE hd_Thongketonghop;
END;

-- Recreate the procedure with the corrected query
CREATE PROCEDURE hd_Thongketonghop
    @MaMH nvarchar(50)
AS
BEGIN
    SELECT
		hd.MaHD,
        sp.MaSP,
        sp.TenSP,
        SUM(hd.SoLuong) AS TongSoLuong, -- Calculate total quantity for products with the same MaSP
        MAX(sp.GiaTien) AS GiaTien,      -- Use an aggregate function to get the max GiaTien
        SUM(hd.SoLuong * sp.GiaTien) AS DoanhThu
    FROM HoaDon hd
    JOIN SanPham sp ON hd.MaSP = sp.MaSP
	inner join MatHang mh on mh.MaMH = sp.MaMH
	where mh.MaMH = @MaMH
    GROUP BY mh.MaMH, sp.MaSP, sp.TenSP;
END;

DECLARE @MaMH nvarchar(50);
-- Execute the stored procedure with the provided parameter value
EXEC hd_Thongketonghop 'MH01';



DROP PROCEDURE Tk_TonKho;

CREATE PROCEDURE Tk_TonKho
    @MaMH NVARCHAR(50)
AS
BEGIN
    SELECT
		mh.MaMH,
        sp.MaSP,
        sp.SoLuong,
        SUM(hd.SoLuong) AS DaBan,
        sp.SoLuong - SUM(hd.SoLuong) AS TonKho
    FROM SanPham sp
    inner join HoaDon hd ON sp.MaSP = hd.MaSP
	inner join MatHang mh on mh.MaMH = sp.MaMH
	where mh.MaMH = @MaMH
    GROUP BY mh.MaMH, sp.MaSP, sp.SoLuong;
END;

DECLARE @MaMH nvarchar(50);
EXEC Tk_TonKho 'MH01'



-- Drop the existing procedure if it exists
IF OBJECT_ID('hd_Thongketheothang', 'P') IS NOT NULL
BEGIN
    DROP PROCEDURE hd_Thongketheothang;
END;

-- Recreate the procedure with the corrected query
CREATE PROCEDURE hd_Thongketheothang
    @SelectedMonth INT,
    @SelectedYear INT
AS
BEGIN
    SELECT
        sp.MaSP,
        sp.TenSP,
        SUM(hd.SoLuong) AS TongSoLuong, 
        MAX(sp.GiaTien) AS GiaTien,      
        SUM(hd.SoLuong * sp.GiaTien) AS DoanhThu,
		Max(hd.SoLuong * sp.GiaTien) AS CaoNhat,
		Min(hd.SoLuong * sp.GiaTien) AS ThapNhat,
		AVG(hd.SoLuong * sp.GiaTien) AS ThapNhat
    FROM HoaDon hd
    JOIN SanPham sp ON hd.MaSP = sp.MaSP
    WHERE  YEAR(hd.Ngaymua) = @SelectedYear AND  MONTH(hd.Ngaymua) = @SelectedMonth 
    GROUP BY sp.MaSP, sp.TenSP, sp.GiaTien;
END;
-- Example of running the stored procedure
DECLARE @SelectedMonth INT = 11;  -- Replace with your desired month
DECLARE @SelectedYear INT = 2023; -- Replace with your desired year
-- Execute the stored procedure
EXEC hd_Thongketheothang @SelectedMonth, @SelectedYear;

