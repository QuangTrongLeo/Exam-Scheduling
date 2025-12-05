package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.ScheduleController;
import model.ClassSession;
import model.Gene;
import model.Individual;
import model.Subject;
import service.InitPopulationService;

public class TimeTable extends JPanel {
    private static final Color HEADER_COLOR = new Color(46, 184, 92);
    private static final Color CELL_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    
    private JTable table;
    private DefaultTableModel model;
    private JLabel titleLabel; // Tiêu đề bảng (JLabel)
    private JLabel fitnessLabel; // Dòng fitness mới thêm
    private Map<String, String> scheduleMap; // Key: "day-period", Value: Thông tin môn học
    
    // Thêm logic cho Individual và duyệt Gene
    private Individual individual; // Cá thể hiện tại
    private List<Gene> genes; // Danh sách Gene trong Individual
    private int currentGeneIndex = -1; // Index Gene hiện tại
    private InitPopulationService initService;
    
    public TimeTable() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        scheduleMap = new HashMap<>();
        
        // Khởi tạo service và tạo Individual
        initService = new InitPopulationService();
        individual = initService.createIndividual(); // Tạo cá thể mới
        genes = individual.getGenes(); // Lấy list Gene
        
        // Tính fitness cho Individual (sử dụng ScheduleController)
        ScheduleController controller = new ScheduleController();
        double fitness = controller.fitness(individual);
        individual.setFitness(fitness); // Set để lưu nếu cần
        
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
        
        // Tùy chỉnh giao diện table
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
        
        // Panel cho SOUTH: Sử dụng BorderLayout để chia trái-phải
        JPanel southPanel = new JPanel(new BorderLayout());
        
        // JLabel cho fitness (bên trái)
        fitnessLabel = new JLabel("Fitness : " + fitness);
        fitnessLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fitnessLabel.setForeground(Color.BLUE); // Màu để nổi bật, có thể thay đổi
        southPanel.add(fitnessLabel, BorderLayout.WEST);
        
        // Panel cho nút Next/Back (bên phải)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("Back");
        JButton btnNext = new JButton("Next");
        
        // Action cho Next: Chuyển sang Gene tiếp theo trong Individual
        btnNext.addActionListener(e -> {
            if (currentGeneIndex < genes.size() - 1) {
                currentGeneIndex++;
                Gene currentGene = genes.get(currentGeneIndex);
                loadGene(currentGene); // Load lịch Gene vào table
                updateHeader("Lịch Dạy Của: " + currentGene.getLecturer().getName());
                // Fitness không thay đổi vì là của Individual, nhưng nếu cần update, có thể set lại text ở đây
            } else {
                JOptionPane.showMessageDialog(this, "Đã hết lịch giảng viên trong cá thể này!");
            }
        });
        
        // Action cho Back: Quay lại Gene trước
        btnBack.addActionListener(e -> {
            if (currentGeneIndex > 0) {
                currentGeneIndex--;
                Gene currentGene = genes.get(currentGeneIndex);
                loadGene(currentGene); // Load lịch Gene vào table
                updateHeader("Lịch Dạy Của: " + currentGene.getLecturer().getName());
                // Fitness không thay đổi
            } else {
                JOptionPane.showMessageDialog(this, "Không còn lịch trước đó!");
            }
        });
        
        buttonPanel.add(btnBack);
        buttonPanel.add(btnNext);
        southPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(southPanel, BorderLayout.SOUTH);
        
        // Load Gene đầu tiên để test
        if (!genes.isEmpty()) {
            currentGeneIndex = 0;
            Gene firstGene = genes.get(currentGeneIndex);
            loadGene(firstGene);
            updateHeader("Lịch Dạy Của: " + firstGene.getLecturer().getName());
        }
    }
    
    // Load dữ liệu từ Gene vào table (adapt từ code cũ)
    public void loadGene(Gene gene) {
        clearSchedule(); // Xóa table cũ
        
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
    
    // Xóa toàn bộ lịch hiện tại
    public void clearSchedule() {
        for (int row = 0; row < 4; row++) {
            for (int col = 1; col <= 6; col++) {
                model.setValueAt("", row, col);
            }
        }
        scheduleMap.clear();
    }
    
    // Cập nhật tiêu đề bảng
    public void updateHeader(String title) {
        titleLabel.setText(title);
    }
    
    // Lấy table để có thể tùy chỉnh thêm
    public JTable getTable() {
        return table;
    }
    
    // Lấy Individual hiện tại (nếu cần cho debug)
    public Individual getIndividual() {
        return individual;
    }
}