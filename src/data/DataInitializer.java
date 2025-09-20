package data;

import model.*;
import java.util.*;

public class DataInitializer {

    // KHỞI TẠO DANH SÁCH MÔN HỌC
    public static List<Subject> initSubjects() {
        List<Subject> subjects = new ArrayList<>();

        // Năm 1
        subjects.add(new Subject(200101, "Triết học Mác Lênin", 3, true, 1));
        subjects.add(new Subject(202108, "Toán A1", 3, true, 1));
        subjects.add(new Subject(202109, "Toán A2", 3, true, 1));
        subjects.add(new Subject(202206, "Vật lý 2", 2, true, 1));
        subjects.add(new Subject(202501, "Giáo dục thể chất 1*", 1, true, 1));
        subjects.add(new Subject(213603, "Anh văn 1*", 4, true, 1));
        subjects.add(new Subject(214201, "Nhập môn tin học", 3, true, 1));
        subjects.add(new Subject(214321, "Lập trình cơ bản", 4, true, 1));

        // Năm 2
        subjects.add(new Subject(200103, "Chủ nghĩa xã hội khoa học", 2, true, 2));
        subjects.add(new Subject(202121, "Xác suất thống kê", 3, true, 2));
        subjects.add(new Subject(202620, "Kỹ năng giao tiếp", 2, false, 2));
        subjects.add(new Subject(202622, "Pháp luật đại cương", 2, true, 2));
        subjects.add(new Subject(208453, "Marketing căn bản", 2, false, 2));
        subjects.add(new Subject(214362, "Giao tiếp người-máy", 4, true, 2));
        subjects.add(new Subject(214389, "Toán rời rạc", 4, true, 2));
        subjects.add(new Subject(214441, "Cấu trúc dữ liệu", 3, true, 2));

        // Năm 3
        subjects.add(new Subject(200105, "Lịch sử Đảng Cộng sản Việt Nam", 2, true, 3));
        subjects.add(new Subject(214252, "Lập trình mạng", 4, true, 3));
        subjects.add(new Subject(214353, "Đồ họa máy tính", 3, false, 3));
        subjects.add(new Subject(214372, "Lập trình .NET", 4, false, 3));
        subjects.add(new Subject(214390, "Lập trình Python", 4, false, 3));
        subjects.add(new Subject(214451, "Hệ quản trị cơ sở dữ liệu", 3, false, 3));
        subjects.add(new Subject(214462, "Lập trình Web", 4, true, 3));
        subjects.add(new Subject(214463, "Nhập môn trí tuệ nhân tạo", 4, true, 3));
        
        // Năm 4
        subjects.add(new Subject(214271, "Quản trị mạng", 3, false, 4));
        subjects.add(new Subject(214273, "Lập trình mạng nâng cao", 4, false, 4));
        subjects.add(new Subject(214291, "Xử lý ảnh và thị giác máy tính", 4, false, 4));
        subjects.add(new Subject(214292, "An ninh mạng", 3, false, 4));
        subjects.add(new Subject(214293, "Thực tập lập trình trên thiết bị di động", 3, false, 4));
        subjects.add(new Subject(214379, "Đảm bảo chất lượng và kiểm thử phần mềm", 3, false, 4));
        subjects.add(new Subject(214383, "Quản lý dự án phần mềm", 3, false, 4));
        subjects.add(new Subject(214388, "Lập trình Front End", 4, false, 4));
        subjects.add(new Subject(214485, "Data Mining", 4, false, 4));
        subjects.add(new Subject(214490, "Phân tích dữ liệu lớn", 3, false, 4));
        subjects.add(new Subject(214491, "Data Warehouse", 3, false, 4));

        return subjects;
    }

    // KHỞI TẠO DANH SÁCH CA THI (2 tuần, mỗi tuần từ thứ 2->7, mỗi ngày 4 ca)
    public static List<TimeSlot> initTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        int id = 1;
        for (int week = 1; week <= 2; week++) {
            for (int day = 2; day <= 7; day++) {
                for (int slot = 1; slot <= 4; slot++) {
                    slots.add(new TimeSlot(id++, week, day, slot));
                }
            }
        }
        return slots;
    }
}
