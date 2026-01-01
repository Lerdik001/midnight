import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TodoApp extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton;
    private JButton removeButton;
    private JButton clearButton;
    private ArrayList<String> tasks;

    public TodoApp() {
        tasks = new ArrayList<>();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Менеджер задач - Todo App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Создаем основную панель
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Заголовок
        JLabel titleLabel = new JLabel("Мои задачи", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Панель ввода
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Список задач
        createTaskList();
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Список задач"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
        
        // Устанавливаем фокус на поле ввода
        taskInput.requestFocus();
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        
        JLabel inputLabel = new JLabel("Новая задача:");
        taskInput = new JTextField();
        taskInput.setFont(new Font("Arial", Font.PLAIN, 14));
        
        addButton = new JButton("Добавить");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        
        // Добавляем обработчик для Enter
        taskInput.addActionListener(e -> addTask());
        addButton.addActionListener(e -> addTask());
        
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        return inputPanel;
    }

    private void createTaskList() {
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));
        taskList.setCellRenderer(new TaskCellRenderer());
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        removeButton = new JButton("Удалить");
        removeButton.setBackground(new Color(244, 67, 54));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> removeTask());
        
        clearButton = new JButton("Очистить все");
        clearButton.setBackground(new Color(255, 152, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearAllTasks());
        
        JButton exitButton = new JButton("Выход");
        exitButton.setBackground(new Color(96, 125, 139));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        
        return buttonPanel;
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            listModel.addElement(task);
            taskInput.setText("");
            taskInput.requestFocus();
            updateButtonStates();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, введите текст задачи!", 
                "Пустая задача", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            listModel.remove(selectedIndex);
            updateButtonStates();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Выберите задачу для удаления!", 
                "Задача не выбрана", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearAllTasks() {
        if (!tasks.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите удалить все задачи?",
                "Подтверждение",
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                tasks.clear();
                listModel.clear();
                updateButtonStates();
            }
        }
    }

    private void updateButtonStates() {
        boolean hasTasks = !tasks.isEmpty();
        removeButton.setEnabled(hasTasks);
        clearButton.setEnabled(hasTasks);
    }

    // Кастомный рендерер для красивого отображения задач
    private class TaskCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            setText("• " + value.toString());
            setBorder(new EmptyBorder(5, 10, 5, 10));
            
            if (isSelected) {
                setBackground(new Color(63, 81, 181));
                setForeground(Color.WHITE);
            } else {
                setBackground(index % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                setForeground(new Color(51, 51, 51));
            }
            
            return this;
        }
    }

    public static void main(String[] args) {
        // Запускаем приложение в EDT
        SwingUtilities.invokeLater(() -> {
            new TodoApp().setVisible(true);
        });
    }
}