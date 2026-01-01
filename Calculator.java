import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";
    private boolean operatorPressed = false;

    public Calculator() {
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Калькулятор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Дисплей
        createDisplay();
        mainPanel.add(display, BorderLayout.NORTH);

        // Панель кнопок
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void createDisplay() {
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createLoweredBevelBorder());
        display.setPreferredSize(new Dimension(280, 50));
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));

        // Кнопки калькулятора
        String[] buttons = {
            "C", "±", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "=", ""
        };

        for (String text : buttons) {
            if (!text.isEmpty()) {
                JButton button = createButton(text);
                panel.add(button);
            } else {
                panel.add(new JLabel()); // Пустое место
            }
        }

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.addActionListener(this);

        // Цветовая схема
        if (text.matches("[0-9.]")) {
            // Цифры и точка
            button.setBackground(new Color(245, 245, 245));
            button.setForeground(Color.BLACK);
        } else if (text.matches("[+\\-×÷=]")) {
            // Операторы
            button.setBackground(new Color(255, 149, 0));
            button.setForeground(Color.WHITE);
        } else {
            // Функциональные кнопки
            button.setBackground(new Color(165, 165, 165));
            button.setForeground(Color.BLACK);
        }

        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9]")) {
                handleNumber(command);
            } else if (command.equals(".")) {
                handleDecimal();
            } else if (command.matches("[+\\-×÷]")) {
                handleOperator(command);
            } else if (command.equals("=")) {
                handleEquals();
            } else if (command.equals("C")) {
                handleClear();
            } else if (command.equals("±")) {
                handlePlusMinus();
            } else if (command.equals("%")) {
                handlePercent();
            }
        } catch (Exception ex) {
            display.setText("Ошибка");
            clearCalculator();
        }
    }

    private void handleNumber(String number) {
        if (operatorPressed || display.getText().equals("0")) {
            display.setText(number);
            operatorPressed = false;
        } else {
            display.setText(display.getText() + number);
        }
    }

    private void handleDecimal() {
        if (operatorPressed) {
            display.setText("0.");
            operatorPressed = false;
        } else if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
        }
    }

    private void handleOperator(String op) {
        if (!operator.isEmpty() && !operatorPressed) {
            handleEquals();
        }
        
        num1 = Double.parseDouble(display.getText());
        operator = op;
        operatorPressed = true;
    }

    private void handleEquals() {
        if (!operator.isEmpty()) {
            num2 = Double.parseDouble(display.getText());
            
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "×":
                    result = num1 * num2;
                    break;
                case "÷":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        display.setText("Ошибка: деление на ноль");
                        clearCalculator();
                        return;
                    }
                    break;
            }
            
            // Форматируем результат
            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                display.setText(String.valueOf(result));
            }
            
            operator = "";
            operatorPressed = true;
        }
    }

    private void handleClear() {
        clearCalculator();
        display.setText("0");
    }

    private void handlePlusMinus() {
        double current = Double.parseDouble(display.getText());
        current = -current;
        
        if (current == (long) current) {
            display.setText(String.valueOf((long) current));
        } else {
            display.setText(String.valueOf(current));
        }
    }

    private void handlePercent() {
        double current = Double.parseDouble(display.getText());
        current = current / 100;
        display.setText(String.valueOf(current));
    }

    private void clearCalculator() {
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = "";
        operatorPressed = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}