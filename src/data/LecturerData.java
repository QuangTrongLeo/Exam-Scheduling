package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Lecturer;
import model.Subject;

public class LecturerData {
	public static List<Lecturer> initLectutes() {
	    List<Lecturer> lecturers = new ArrayList<>();
	    List<Subject> subjects = SubjectData.initSubjects();

	    // ==== Giảng viên có học vị TS. ====
	    lecturers.add(new Lecturer(1, "TS. Nguyễn Văn Dũ", Arrays.asList(
	            subjects.get(4),  // Cấu trúc dữ liệu
	            subjects.get(8),  // Lập trình Python
	            subjects.get(11)  // Nhập môn trí tuệ nhân tạo
	    )));

	    lecturers.add(new Lecturer(2, "TS. Phạm Văn Tính", Arrays.asList(
	            subjects.get(5),  // Lập trình mạng
	            subjects.get(13)  // Xử lý ảnh và thị giác máy tính
	    )));

	    // ==== Giảng viên có học vị ThS. ====
	    lecturers.add(new Lecturer(3, "ThS. Võ Tấn Linh", Arrays.asList(
	            subjects.get(0) // Nhập môn tin học
	    )));

	    lecturers.add(new Lecturer(4, "ThS. Nguyễn Thị Phương Trâm", Arrays.asList(
	            subjects.get(1), // Lập trình cơ bản
	            subjects.get(3)  // Toán rời rạc
	    )));

	    lecturers.add(new Lecturer(5, "ThS. Nguyễn Đức Công Song", Arrays.asList(
	            subjects.get(1),  // Lập trình cơ bản
	            subjects.get(18)  // Data Warehouse
	    )));

	    lecturers.add(new Lecturer(6, "ThS. Võ Tấn Toàn", Arrays.asList(
	            subjects.get(2),  // Giao tiếp người - máy
	            subjects.get(7),  // Lập trình .NET
	            subjects.get(14)  // Thực tập lập trình trên thiết bị di động
	    )));

	    lecturers.add(new Lecturer(7, "ThS. Lê Phi Hùng", Arrays.asList(
	            subjects.get(2),  // Giao tiếp người - máy
	            subjects.get(6),  // Đồ họa máy tính
	            subjects.get(10)  // Lập trình Web
	    )));

	    lecturers.add(new Lecturer(8, "ThS. Trần Quốc Việt", Arrays.asList(
	            subjects.get(3),  // Toán rời rạc
	            subjects.get(17)  // Data Mining
	    )));

	    lecturers.add(new Lecturer(9, "ThS. Trần Lê Như Quỳnh", Arrays.asList(
	            subjects.get(4),  // Cấu trúc dữ liệu
	            subjects.get(9)   // Hệ quản trị cơ sở dữ liệu
	    )));

	    lecturers.add(new Lecturer(10, "ThS. Nguyễn Thị Minh Hương", Arrays.asList(
	            subjects.get(9)   // Hệ quản trị cơ sở dữ liệu
	    )));

	    lecturers.add(new Lecturer(11, "ThS. Phan Đình Long", Arrays.asList(
	            subjects.get(10), // Lập trình Web
	            subjects.get(16)  // Lập trình Front End
	    )));

	    lecturers.add(new Lecturer(12, "ThS. Khương Hải Châu", Arrays.asList(
	            subjects.get(11)  // Nhập môn trí tuệ nhân tạo
	    )));

	    lecturers.add(new Lecturer(13, "ThS. Phan Vĩnh Thuần", Arrays.asList(
	            subjects.get(12)  // Quản trị mạng
	    )));

	    lecturers.add(new Lecturer(14, "ThS. Trần Thị Thanh Nga", Arrays.asList(
	            subjects.get(15)  // Đảm bảo chất lượng và kiểm thử phần mềm
	    )));

	    lecturers.add(new Lecturer(15, "ThS. Đặng Minh Tiến", Arrays.asList(
	            subjects.get(17)  // Data Mining
	    )));

	    return lecturers;
	}

	public static void main(String[] args) {
		for (Lecturer l : initLectutes()) {
			System.out.println(l.toString() + "\n");
		}
	}
}
