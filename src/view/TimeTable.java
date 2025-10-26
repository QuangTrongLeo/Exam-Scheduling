package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import model.*;

public class TimeTable extends JPanel {
    private static final Color HEADER_COLOR = new Color(46, 184, 92);
    private static final Color CELL_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = Color.BLACK;
    
    private JTable table;
    private DefaultTableModel model;
    private Map<String, String> scheduleMap; // Key: "day-period", Value: Thông tin môn học
    
    public TimeTable() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        scheduleMap = new HashMap<>();
        
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
        
        // Tùy chỉnh renderer cho header
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(HEADER_COLOR);
                c.setForeground(Color.BLACK);
                setFont(new Font("Arial", Font.BOLD, 16));
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
                return c;
            }
        };
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        
        // Tùy chỉnh renderer cho các ô
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Cột đầu tiên (Ca)
                if (column == 0) {
                    c.setBackground(HEADER_COLOR);
                    c.setForeground(Color.BLACK);
                    ((JLabel) c).setFont(new Font("Arial", Font.BOLD, 16));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                    c.setBackground(CELL_COLOR);
                    c.setForeground(Color.BLACK);
                    ((JLabel) c).setFont(new Font("Arial", Font.PLAIN, 11));
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
                    ((JLabel) c).setVerticalAlignment(SwingConstants.TOP);
                }
                
                setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
                return c;
            }
        });
        
        // Đặt độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        for (int i = 1; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(180);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Load dữ liệu từ Chromosome (thời khóa biểu) vào bảng
     * @param chromosome Thời khóa biểu cần hiển thị
     */
    public void loadSchedule(Chromosome chromosome) {
        // Clear dữ liệu cũ
        clearSchedule();
        
        if (chromosome == null || chromosome.getGenes() == null) {
            return;
        }
        
        // Duyệt qua từng Gene và thêm vào bảng
        for (Gene gene : chromosome.getGenes()) {
//            Subject subject = gene.getSubject();
//            TimeSlot theorySlot = gene.getTheoryTimeSlot();
//            TimeSlot practiceSlot = gene.getPracticeTimeSlot();
//            
//            // Thêm lịch lý thuyết
//            if (theorySlot != null) {
//                addToSchedule(theorySlot, subject, true);
//            }
//            
//            // Thêm lịch thực hành
//            if (practiceSlot != null) {
//                addToSchedule(practiceSlot, subject, false);
//            }
        }
    }
    
    /**
     * Thêm môn học vào ô tương ứng trong bảng
     */
    private void addToSchedule(TimeSlot timeSlot, Subject subject, boolean isTheory) {
        int row = timeSlot.getPeriod() - 1; // Ca 1-4 -> row 0-3
        int col = timeSlot.getDay() - 1; // Thứ 2-7 -> col 1-6
        
        String type = isTheory ? "LT" : "TH";
        String info = String.format("<html>%s (%d)<br/>Nhóm: %02d<br/>Loại: %s</html>", 
                                    subject.getName(), 
                                    subject.getId(), 
                                    1, // Có thể thêm nhóm sau
                                    type);
        
        // Lấy nội dung hiện tại của ô
        String currentContent = (String) model.getValueAt(row, col);
        if (currentContent == null || currentContent.trim().isEmpty()) {
            model.setValueAt(info, row, col);
        } else {
            // Nếu ô đã có môn khác, thêm vào dưới
            model.setValueAt(currentContent + "<br/><br/>" + info, row, col);
        }
    }
    
    /**
     * Xóa toàn bộ lịch hiện tại
     */
    public void clearSchedule() {
        for (int row = 0; row < 4; row++) {
            for (int col = 1; col <= 6; col++) {
                model.setValueAt("", row, col);
            }
        }
        scheduleMap.clear();
    }
    
    /**
     * Lấy table để có thể tùy chỉnh thêm
     */
    public JTable getTable() {
        return table;
    }
}