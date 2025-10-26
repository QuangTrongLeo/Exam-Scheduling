package data;

import model.*;
import java.util.*;

public class SubjectData {

    // KHỞI TẠO DANH SÁCH MÔN HỌC
	public static List<Subject> initSubjects() {
	    List<Subject> subjects = new ArrayList<>();

	    subjects.add(new Subject(214201, "Nhập môn tin học", 3, false, true, true));
	    subjects.add(new Subject(214321, "Lập trình cơ bản", 4, false, true, true));

	    subjects.add(new Subject(214362, "Giao tiếp người - máy", 4, false, true, true));
	    subjects.add(new Subject(214389, "Toán rời rạc", 3, false, true, false));
	    subjects.add(new Subject(214441, "Cấu trúc dữ liệu", 4, false, true, true));

	    subjects.add(new Subject(214252, "Lập trình mạng", 4, false, true, true));
	    subjects.add(new Subject(214353, "Đồ họa máy tính", 3, false, false, true));
	    subjects.add(new Subject(214372, "Lập trình .NET", 4, false, false, true));
	    subjects.add(new Subject(214390, "Lập trình Python", 4, false, false, true));
	    subjects.add(new Subject(214451, "Hệ quản trị cơ sở dữ liệu", 3, false, false, true));

	    subjects.add(new Subject(214462, "Lập trình Web", 4, true, true, true));
	    subjects.add(new Subject(214463, "Nhập môn trí tuệ nhân tạo", 4, false, true, true));

	    subjects.add(new Subject(214271, "Quản trị mạng", 3, false, false, true));
	    subjects.add(new Subject(214291, "Xử lý ảnh và thị giác máy tính", 4, false, false, true));
	    subjects.add(new Subject(214293, "Thực tập lập trình trên thiết bị di động", 3, false, false, true));
	    subjects.add(new Subject(214379, "Đảm bảo chất lượng và kiểm thử phần mềm", 4, false, false, true));
	    subjects.add(new Subject(214388, "Lập trình Front End", 4, false, false, true));
	    subjects.add(new Subject(214485, "Data Mining", 4, false, false, true));
	    subjects.add(new Subject(214491, "Data Warehouse", 3, false, false, true));

	    return subjects;
	}
    
    public static void main(String[] args) {
		for (Subject s : initSubjects()) {
			System.out.println(s.toString());
		}
	}
   
}
