package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import data.LecturerData;
import model.ClassSession;
import model.Gene;
import model.Lecturer;
import model.Subject;
import service.InitPopulationService;

public class Main {
    private static List<Gene> geneHistory = new ArrayList<>(); // Lưu lịch sử Gene để Back
    private static int currentIndex = -1; // Index hiện tại trong history
    private static List<Lecturer> lecturers; // Danh sách giảng viên
    private static InitPopulationService initService; // Service để createGene
    private static Random random = new Random(); // Để chọn Lecturer ngẫu nhiên

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Khởi tạo dữ liệu
            lecturers = LecturerData.initLectutes();
            initService = new InitPopulationService();

            // Tạo JFrame chính
            JFrame frame = new JFrame("Test Thời Khóa Biểu - Lịch Dạy Của Giảng Viên (Gene)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 600);
            frame.setLocationRelativeTo(null);

            // Tạo panel chính với BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Tạo table để hiển thị lịch Gene
            TimeTableForGene timeTable = new TimeTableForGene();
            mainPanel.add(timeTable, BorderLayout.CENTER);

            // Panel cho nút Next/Back ở dưới phải
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnBack = new JButton("Back");
            JButton btnNext = new JButton("Next");

            // Action cho Next: Generate Gene mới và hiển thị
            btnNext.addActionListener(e -> {
                // Chọn Lecturer ngẫu nhiên
                Lecturer randomLecturer = lecturers.get(random.nextInt(lecturers.size()));
                // Tạo Gene mới sử dụng hàm createGene từ InitPopulationService
                Gene newGene = initService.createGene(randomLecturer);

                // Thêm vào history và cập nhật index
                geneHistory.add(newGene);
                currentIndex = geneHistory.size() - 1;

                // Load vào table
                timeTable.loadGene(newGene);

                // Cập nhật tiêu đề bảng với tên giảng viên
                timeTable.updateHeader("Lịch Dạy Của: " + newGene.getLecturer().getName());
            });

            // Action cho Back: Quay lại Gene cũ
            btnBack.addActionListener(e -> {
                if (currentIndex > 0) {
                    currentIndex--;
                    Gene previousGene = geneHistory.get(currentIndex);
                    timeTable.loadGene(previousGene);
                    timeTable.updateHeader("Lịch Dạy Của: " + previousGene.getLecturer().getName());
                }
            });

            buttonPanel.add(btnBack);
            buttonPanel.add(btnNext);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);
            frame.setVisible(true);

            // Generate Gene đầu tiên để test
            btnNext.doClick();
        });
    }

    // Lớp nội bộ để hiển thị table cho Gene (adapt từ TimeTable.java)
    static class TimeTableForGene extends JPanel {
        private static final Color HEADER_COLOR = new Color(46, 184, 92);
        private static final Color CELL_COLOR = Color.WHITE;
        private static final Color BORDER_COLOR = Color.BLACK;

        private JTable table;
        private DefaultTableModel model;
        private JLabel titleLabel; // Tiêu đề bảng (JLabel)

        public TimeTableForGene() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            // Tạo JLabel cho tiêu đề bảng
            titleLabel = new JLabel("Lịch Dạy Của: ", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setForeground(Color.BLACK);
            add(titleLabel, BorderLayout.NORTH); // Đặt ở trên cùng

            // Tạo table model với 4 Ca
            String[] columnNames = {"", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
            String[][] data = {
                {"Ca 1", "", "", "", "", "", ""},
                {"Ca 2", "", "", "", "", "", ""},
                {"Ca 3", "", "", "", "", "", ""},
                {"Ca 4", "", "", "", "", "", ""}
            };

            model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(model);
            table.setRowHeight(100);
            table.setFont(new Font("Arial", Font.PLAIN, 12));
            table.setGridColor(BORDER_COLOR);
            table.setShowGrid(true);
            table.setIntercellSpacing(new Dimension(1, 1));

            // Tùy chỉnh header
            JTableHeader header = table.getTableHeader();
            header.setBackground(HEADER_COLOR);
            header.setForeground(Color.BLACK);
            header.setFont(new Font("Arial", Font.BOLD, 16));
            header.setPreferredSize(new Dimension(header.getWidth(), 50));

            add(new JScrollPane(table), BorderLayout.CENTER);
        }

        // Load dữ liệu từ Gene vào table
        public void loadGene(Gene gene) {
            clearTable(); // Xóa table cũ

            for (ClassSession cs : gene.getClassSessions()) {
                int row = cs.getTimeSlot().getPeriod() - 1; // Ca 1-4 -> row 0-3
                int col = cs.getTimeSlot().getDay() - 1; // Thứ 2-7 -> col 1-6

                Subject subject = cs.getSubject();
                String info = String.format("<html>%s (%s)<br/>Phòng: %s</html>",
                                            subject.getName(),
                                            subject.getId(),
                                            cs.getRoom().getName());

                // Thêm vào cell (nếu đã có, append)
                String current = (String) model.getValueAt(row, col);
                if (current == null || current.trim().isEmpty()) {
                    model.setValueAt(info, row, col);
                } else {
                    model.setValueAt(current + "<br/><br/>" + info, row, col);
                }
            }
        }

        // Xóa table
        private void clearTable() {
            for (int row = 0; row < 4; row++) {
                for (int col = 1; col <= 6; col++) {
                    model.setValueAt("", row, col);
                }
            }
        }

        // Cập nhật tiêu đề bảng
        public void updateHeader(String title) {
            titleLabel.setText(title);
        }
    }
}