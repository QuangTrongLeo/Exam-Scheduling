package main;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import model.*;
import data.DataInitializer;
import view.TimeTable;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Khởi tạo dữ liệu
            List<Subject> subjects = DataInitializer.initSubjects();
            List<TimeSlot> timeSlots = DataInitializer.initTimeSlots();
            
            // Tạo thời khóa biểu mẫu (demo)
            Chromosome sampleChromosome = createSampleSchedule(subjects, timeSlots);
            
            // Tạo giao diện
            JFrame frame = new JFrame("Thời Khóa Biểu - Genetic Algorithm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 800);
            frame.setLocationRelativeTo(null);
            
            // Tạo panel chính
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Tạo TimeTable
            TimeTable timeTable = new TimeTable();
            mainPanel.add(timeTable, BorderLayout.CENTER);
            
            // Tạo panel thông tin
            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setPreferredSize(new Dimension(300, 0));
            
            JTextArea infoArea = new JTextArea();
            infoArea.setEditable(false);
            infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            infoArea.setMargin(new Insets(10, 10, 10, 10));
            
            // Hiển thị thông tin về thời khóa biểu
            StringBuilder info = new StringBuilder();
            info.append("===== THÔNG TIN THời KHóA BIểU =====\n\n");
            info.append("Fitness: ").append(String.format("%.2f", sampleChromosome.getFitness())).append("\n\n");
            info.append("===== DANH SÁCH MÔN HọC =====\n");
            for (Subject subject : subjects) {
                info.append("\n• ").append(subject.getName())
                    .append("\n  Mã: ").append(subject.getId())
                    .append("\n  Tín chỉ: ").append(subject.getCredits())
//                    .append("\n  Loại: ").append(subject.isRequired() ? "Bắt buộc" : "Tự chọn")
                    .append("\n");
            }
            infoArea.setText(info.toString());
            
            JScrollPane infoScroll = new JScrollPane(infoArea);
            infoPanel.add(infoScroll, BorderLayout.CENTER);
            
            // Tạo panel buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JButton btnLoad = new JButton("Load Lịch");
//            JButton btnClear = new JButton("Xóa Lịch");
            JButton btnGenerate = new JButton("Tạo Lịch Mới");
            
            btnLoad.addActionListener(e -> {
                timeTable.loadSchedule(sampleChromosome);
                JOptionPane.showMessageDialog(frame, "Đã load thời khóa biểu!", 
                                             "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            });
            
//            btnClear.addActionListener(e -> {
//                timeTable.clearSchedule();
//                JOptionPane.showMessageDialog(frame, "Đã xóa thời khóa biểu!", 
//                                             "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            });
            
            btnGenerate.addActionListener(e -> {
                Chromosome newChromosome = createSampleSchedule(subjects, timeSlots);
                timeTable.loadSchedule(newChromosome);
                JOptionPane.showMessageDialog(frame, 
                    "Đã tạo thời khóa biểu mới!\nFitness: " + String.format("%.2f", newChromosome.getFitness()), 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            });
            
            buttonPanel.add(btnLoad);
//            buttonPanel.add(btnClear);
            buttonPanel.add(btnGenerate);
            
            infoPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            mainPanel.add(infoPanel, BorderLayout.EAST);
            
            frame.add(mainPanel);
            frame.setVisible(true);
            
            // Load lịch ban đầu
            timeTable.loadSchedule(sampleChromosome);
        });
    }
    
    /**
     * Tạo thời khóa biểu mẫu để demo
     * Trong thực tế, bạn sẽ dùng Genetic Algorithm để tạo
     */
    private static Chromosome createSampleSchedule(List<Subject> subjects, List<TimeSlot> timeSlots) {
        List<Gene> genes = new ArrayList<>();
        Random random = new Random();
        
        // Tạo lịch cho từng môn học
        for (Subject subject : subjects) {
            // Random chọn ca học cho lý thuyết
            TimeSlot theorySlot = timeSlots.get(random.nextInt(timeSlots.size()));
            
            // Random chọn ca học cho thực hành (nếu cần)
            TimeSlot practiceSlot = null;
            if (subject.getCredits() >= 3) {
                // Chọn slot khác với lý thuyết
                do {
                    practiceSlot = timeSlots.get(random.nextInt(timeSlots.size()));
                } while (practiceSlot.getDay() == theorySlot.getDay() && 
                         practiceSlot.getPeriod() == theorySlot.getPeriod());
            }
            
//            genes.add(new Gene(subject, theorySlot, practiceSlot));
        }
        
        // Tính fitness (đơn giản: random từ 70-100)
        double fitness = 70 + random.nextDouble() * 30;
        
        return new Chromosome(genes, fitness);
    }
}