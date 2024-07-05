import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private double firstNumber = 0;
    private double secondNumber = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public CalculatorApp() {
        
        setTitle("Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        
        String[] buttonLabels = {
                "7", "8", "9", "/", 
                "4", "5", "6", "*", 
                "1", "2", "3", "-", 
                "√", "^", "M+", "+",
                "0", ".", "=",  "C"
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5, 5, 5));

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.addActionListener(this);
            panel.add(button);
        }

        
        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("\\d") || command.equals(".")) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            firstNumber = 0;
            secondNumber = 0;
            operator = "";
            startNewNumber = true;
        } else if (command.equals("=")) {
            secondNumber = Double.parseDouble(display.getText());
            switch (operator) {
                case "+":
                    display.setText(String.valueOf(firstNumber + secondNumber));
                    break;
                case "-":
                    display.setText(String.valueOf(firstNumber - secondNumber));
                    break;
                case "*":
                    display.setText(String.valueOf(firstNumber * secondNumber));
                    break;
                case "/":
                    if (secondNumber != 0) {
                        display.setText(String.valueOf(firstNumber / secondNumber));
                    } else {
                        display.setText("Error");
                    }
                    break;
                case "^":
                    display.setText(String.valueOf(Math.pow(firstNumber, secondNumber)));
                    break;
            }
            startNewNumber = true;
        } else if (command.equals("√")) {
            double value = Double.parseDouble(display.getText());
            if (value >= 0) {
                display.setText(String.valueOf(Math.sqrt(value)));
            } else {
                display.setText("Error");
            }
            startNewNumber = true;
        } else if (command.equals("M+")) {
            
            firstNumber += Double.parseDouble(display.getText());
            startNewNumber = true;
        } else {
            firstNumber = Double.parseDouble(display.getText());
            operator = command;
            startNewNumber = true;
        }
    }
}
