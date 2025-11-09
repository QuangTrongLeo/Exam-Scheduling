package data;

import model.Subject;
import model.SubjectClassCount;
import java.util.*;

public class SubjectClassCountData {

//    public static List<SubjectClassCount> initClassCounts(List<Subject> subjects) {
//        List<SubjectClassCount> result = new ArrayList<>();
//
//        for (Subject s : subjects) {
//            int theory = 0;
//            int lab = 0;
//
//            // Môn có thực hành
//            if (s.getLabLessons() > 0) {
//                if (s.isCore() && s.isCompulsory()) {       // Cốt lõi + bắt buộc
//                    theory = 2;
//                    lab = 4;
//                } else if (!s.isCore() && s.isCompulsory()) { // Bắt buộc + có lab
//                    theory = 2;
//                    lab = 3;  
//                } else { // Tự chọn + có lab
//                    theory = 1;
//                    lab = 2;
//                }
//            } else { // Môn không có lab
//                if (s.isCompulsory()) { // Bắt buộc + không lab
//                    theory = 2;
//                    lab = 0;
//                }
//            }
//
//            result.add(new SubjectClassCount(s, theory, lab));
//        }
//
//        return result;
//    }
	
	public static List<SubjectClassCount> initClassCounts(List<Subject> subjects) {
        List<SubjectClassCount> result = new ArrayList<>();

        // Tạo map để tra nhanh Subject theo tên (để tránh phải dò ID)
        Map<String, Subject> map = new HashMap<>();
        for (Subject s : subjects) {
            map.put(s.getName(), s);
        }

        // Môn cốt lõi bắt buộc
        result.add(new SubjectClassCount(map.get("Lập trình Web"), 2, 4));
        
        // Môn bắt buộc
        result.add(new SubjectClassCount(map.get("Nhập môn tin học"), 2, 3));
        result.add(new SubjectClassCount(map.get("Lập trình cơ bản"), 2, 3));
        result.add(new SubjectClassCount(map.get("Giao tiếp người - máy"), 2, 3));
        result.add(new SubjectClassCount(map.get("Cấu trúc dữ liệu"), 2, 4));
        result.add(new SubjectClassCount(map.get("Lập trình mạng"), 2, 3));
        result.add(new SubjectClassCount(map.get("Nhập môn trí tuệ nhân tạo"), 2, 3));
        
        // Môn tự chọn
        result.add(new SubjectClassCount(map.get("Hệ quản trị cơ sở dữ liệu"), 2, 3));
        result.add(new SubjectClassCount(map.get("Lập trình Python"), 2, 3));
        result.add(new SubjectClassCount(map.get("Đồ họa máy tính"), 1, 2));
        result.add(new SubjectClassCount(map.get("Lập trình .NET"), 1, 2));
        result.add(new SubjectClassCount(map.get("Quản trị mạng"), 1, 2));
        result.add(new SubjectClassCount(map.get("Xử lý ảnh và thị giác máy tính"), 1, 2));
        result.add(new SubjectClassCount(map.get("Thực tập lập trình trên thiết bị di động"), 1, 2));
        result.add(new SubjectClassCount(map.get("Đảm bảo chất lượng và kiểm thử phần mềm"), 1, 3));
        result.add(new SubjectClassCount(map.get("Lập trình Front End"), 1, 2));
        result.add(new SubjectClassCount(map.get("Data Mining"), 1, 2));
        result.add(new SubjectClassCount(map.get("Data Warehouse"), 1, 2));
        
        // Môn bắt buộc không thực hành
        result.add(new SubjectClassCount(map.get("Toán rời rạc"), 3, 0));

        return result;
    }

    public static void main(String[] args) {
        List<Subject> subjects = data.SubjectData.initSubjects();
        List<SubjectClassCount> counts = initClassCounts(subjects);

        for (SubjectClassCount count : counts) {
            System.out.println(count);
        }
    }
}
