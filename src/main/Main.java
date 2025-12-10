package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.ScheduleController;
import model.Individual;
import print.SchedulePrint;
import service.InitPopulationService;
import view.TimeTable;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tạo JFrame chính
            JFrame frame = new JFrame("Test Thời Khóa Biểu - Lịch Dạy Của Giảng Viên (Gene)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 600);
            frame.setLocationRelativeTo(null);
            
            // Tạo TimeTable (bây giờ tự xử lý logic bên trong)
            TimeTable timeTable = new TimeTable();
            frame.add(timeTable);
            frame.setVisible(true);
            
            ScheduleController controller = new ScheduleController();
        	SchedulePrint printer = new SchedulePrint();
        	
        	// Tạo 1 cá thể để test
            Individual individual = controller.theBestIndividual();
          
            printer.printIndividual(individual);
            
        });
    }
}