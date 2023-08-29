package com.example.calci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.regex.Pattern;
import java.lang.*;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private StringBuilder currentInput = new StringBuilder();
    private double result = Double.NaN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);

        setupNumberButtons();
        setupOperatorButtons();
        setupClearButton();
        setupEqualsButton();
        setupUndoButton();
    }

    private void setupNumberButtons() {
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonDecimal
        };

        for (int id : numberButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String buttonText = ((Button) v).getText().toString();
                    currentInput.append(buttonText);
                    inputEditText.setText(currentInput.toString());
                }
            });
        }
    }

    private void setupOperatorButtons() {
        int[] operatorButtonIds = {
                R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide
        };

        for (int id : operatorButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentInput.length() > 0) {
                        String operator = ((Button) v).getText().toString();
                        currentInput.append(" ").append(operator).append(" ");
                        inputEditText.setText(currentInput.toString());
                    }
                }
            });
        }
    }

    private void setupClearButton() {
        Button clearButton = findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput.setLength(0);
                result = Double.NaN;
                inputEditText.setText("");
            }
        });
    }

    private void setupEqualsButton() {
        Button equalsButton = findViewById(R.id.buttonEquals);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 0) {
                    String[] expressionParts = currentInput.toString().split(" ");

                    if (expressionParts.length != 3) {
                        // Invalid expression, clear input and show an error
                        inputEditText.setText("Error");
                        return;
                    }

                    double operand1 = Double.parseDouble(expressionParts[0]);
                    double operand2 = Double.parseDouble(expressionParts[2]);
                    String operator = expressionParts[1];

                    result = performCalculation(operand1, operand2, operator);
                    inputEditText.setText( String.valueOf(result));
                    currentInput.setLength(0);
                }
            }
        });
    }
    private void setupUndoButton() {
        Button undoButton = findViewById(R.id.buttonUndo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 0) {
                    currentInput.deleteCharAt(currentInput.length() - 1);
                    inputEditText.setText(currentInput.toString());
                }
            }
        });
    }


    private double performCalculation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    return Double.NaN; // Division by zero
                }
            default:
                return operand2;
        }
    }
}