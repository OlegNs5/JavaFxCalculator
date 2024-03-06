package ro.ctrln.javafx.calculator.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import ro.ctrln.javafx.calculator.operations.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class CalculatorController {
  
    @FXML
    public Label errorsLabel;
    @FXML
    public TextArea calculatorOperationAria;


    public void writeZero(ActionEvent event) {
        checkNewOperation();
        if (!calculatorOperationAria.getText().equalsIgnoreCase("0")) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("0"));
        }
        setPositionCaret();
    }

    private void setPositionCaret() {
        calculatorOperationAria.positionCaret(calculatorOperationAria.getText().length());//cursorul mereu in dreapta
    }

    @FXML
    public void writeOne(ActionEvent event) {
        writeDigit("1");
    }
    private void writeDigit(String digit){
        checkNewOperation();
        if (replaceZero(digit)) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat(digit));
        }
        setPositionCaret();
    }

    private boolean replaceZero(String replacement) {
        boolean zeroReplaced = false;

        if (calculatorOperationAria.getText().equalsIgnoreCase("0")){
            calculatorOperationAria.setText(replacement);
            zeroReplaced = true;
        }
        return !zeroReplaced;
    }

    @FXML
    public void writeTwo(ActionEvent event) {
        writeDigit("2");
    }
    @FXML
    public void writeThree(ActionEvent event) {
        writeDigit("3");
    }
    @FXML
    public void writeFour(ActionEvent event) {
        writeDigit("4");
    }
    @FXML
    public void writeFive(ActionEvent event) {
        writeDigit("5");
    }
    @FXML
    public void writeSix(ActionEvent event) {
        writeDigit("6");
    }
    @FXML
    public void writeSeven(ActionEvent event) {
        writeDigit("7");

    }
    @FXML
    public void writeEight(ActionEvent event) {
        writeDigit("8");

    }
    @FXML
    public void writeNine(ActionEvent event) {
        writeDigit("9");

    }

    private void checkNewOperation(){
        if (calculatorOperationAria.getText().contains("=")){
            calculatorOperationAria.setText("");
        }
    }

    private boolean commaAlreadyPresentOnOperand(String text){
        if (mathOperationsNoPresentsOnCalculatorTextArea()){
            return text.contains(".");//Verificam operandul din partea stinga

        }else {//verificam operandul din partea dreapta
            String[] operands = {};
            for ( String mathOperation :operationCharacters ){
                if (operands.length == 2 ){
                    break;
                }

                operands = splitOperation(text,mathOperation);
            }
            return operands[1].contains(".");
        }

    }
    @FXML
    public void addition(ActionEvent event) {
        if (mathOperationsNoPresentsOnCalculatorTextArea()) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("+"));
        }
    }

    @FXML
    public void substraction(ActionEvent event) {
        if (mathOperationsNoPresentsOnCalculatorTextArea()) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("-"));
        }
    }
    @FXML
    public void division(ActionEvent event) {
        if (mathOperationsNoPresentsOnCalculatorTextArea()) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("/"));
        }
    }
    @FXML
    public void multiplication(ActionEvent event) {
        if (mathOperationsNoPresentsOnCalculatorTextArea()) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("*"));
        }
    }
    private boolean mathOperationsNoPresentsOnCalculatorTextArea(){
        return !calculatorOperationAria.getText().contains("+") &&
                !calculatorOperationAria.getText().contains("-") &&
                !calculatorOperationAria.getText().contains("*") &&
                !calculatorOperationAria.getText().contains("/");
    }

    @FXML
    public void clearCalculatorOperationsArea(ActionEvent event) {
        calculatorOperationAria.setText("");
    }

    @FXML
    public void evaluate(ActionEvent event) {
        String operation = calculatorOperationAria.getText();
        if (!operation.isEmpty()) {
            if (operation.contains("+")) {
                performAddition(operation);
            } else if (operation.contains("-")) {
                performSubtraction(operation);
            } else if (operation.contains("*")) {
                performMultplication(operation);
            } else if (operation.contains("/")) {
                performDivision(operation);
            } else {
                errorsLabel.setText("Avem o operatie necunoscuta !");
            }
        }}

    @FXML
    public void writeComma(ActionEvent event) {
        if (!commaAlreadyPresentOnOperand(calculatorOperationAria.getText())) {
            calculatorOperationAria.setText(calculatorOperationAria.getText().concat("."));
        }
    }

    private void performAddition(String operation) {
        String[] operands = splitOperation(operation,"+");
        if (operands.length ==2){
            doOperation(operands, Operation.ADDITION);
        }
    }

    private void performSubtraction(String operation) {
        String[] operands = splitOperation(operation,"-");
        if (operands.length == 2){
            doOperation(operands, Operation.SUBTRACTION);
        }
    }

    private void performMultplication(String operation) {
        String[] operands = splitOperation(operation,"*");
        if (operands.length == 2){
            doOperation(operands, Operation.MULTIPLICATION);
        }
    }

    private void performDivision(String operation) {
        String[] operands = splitOperation(operation,"/");
        if (operands.length == 2){
            doOperation(operands, Operation.DIVISION);
        }
    }
    private String[] splitOperation(String operation, String splitter) {
        String[] operands = {};
        try {
            if(Arrays.asList("+","-","*","/").contains(splitter)) {
            operation = operation.replace(splitter,"----");
            }
        operands = operation.split("----");
        }catch (Exception ex){
            errorsLabel.setText("Operanzi nedetectati !");
            ex.printStackTrace();
        }
        return operands;
    }

    private void doOperation(String[] operands, Operation operation) {
        try {
            BigDecimal firstOperation = new BigDecimal(cleanOperation(operands[0]));
            BigDecimal secondOperation = new BigDecimal(cleanOperation(operands[1]));

            switch (operation){
                case ADDITION: writeResult(firstOperation.add(secondOperation));
                break;
                case SUBTRACTION:writeResult(firstOperation.subtract(secondOperation));
                break;
                case DIVISION:writeResult(firstOperation.divide(secondOperation, RoundingMode.HALF_DOWN));
                break;
                case MULTIPLICATION:writeResult(firstOperation.multiply(secondOperation));
                    break;
            }

        }catch (NumberFormatException nfe){
        errorsLabel.setText("Opereanzii sunt gresiti !");
        }
    }

    private String cleanOperation(String operand){
        return operand.replaceAll("\n","");
    }

    private void writeResult(BigDecimal result) {
        calculatorOperationAria.setText(calculatorOperationAria.getText()
                        .replaceAll("\n","")
                        .replaceAll("\r","")
                        .concat("=").concat(result.toString()));

    }
    @FXML
    public void handleKeyTyped(KeyEvent keyEvent) {
        if (allowedCharacter(keyEvent.getCharacter())) {
//            checkNewOperation();

            //am inlocuit fiecare if cu o metoda
            handleDigitCharacter(keyEvent);

            handleComma(keyEvent);

            handleOperation(keyEvent);
            handleEvalutionKeys(keyEvent);

        }else{
            keyEvent.consume();
        }
    }

    private void handleEvalutionKeys(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase("=") || keyEvent.getCharacter().equalsIgnoreCase("\r")){
            keyEvent.consume();
            evaluate(new ActionEvent());
        }
    }

    private void handleOperation(KeyEvent keyEvent) {
        if (operationCharacters(keyEvent.getCharacter())){
            if (!mathOperationsNoPresentsOnCalculatorTextArea()){
                keyEvent.consume();
            }
        }
    }

    private void handleComma(KeyEvent keyEvent) {
        if (keyEvent.getCharacter().equalsIgnoreCase(".")){
            writeComma(new ActionEvent());
            keyEvent.consume();
        }
    }

    private void handleDigitCharacter(KeyEvent keyEvent) {
        if (isDigitCharater(keyEvent.getCharacter())){

            switch (keyEvent.getCharacter()){
                case ("0"):
                    writeZero(new ActionEvent());
                    break;
                case ("1"):
                    writeOne(new ActionEvent());
                    break;
                case ("2"):
                    writeTwo(new ActionEvent());
                    break;
                case ("3"):
                    writeThree(new ActionEvent());
                    break;
                case ("4"):
                    writeFour(new ActionEvent());
                    break;
                case ("5"):
                    writeFive(new ActionEvent());
                    break;
                case ("6"):
                    writeSix(new ActionEvent());
                    break;
                case ("7"):
                    writeSeven(new ActionEvent());
                    break;
                case ("8"):
                    writeEight(new ActionEvent());
                    break;
                case ("9"):
                    writeNine(new ActionEvent());
                    break;
            }
            keyEvent.consume();

        }
    }

    private List<String> allowedCharacters = Arrays
            .asList("0","1","2","3","4","5","6","7","8","9",".","=","+","-","*","/","\r","\n");
    private boolean allowedCharacter(String character){
        return allowedCharacters.contains(character);
    }

    private List<String> operationCharacters = Arrays.asList("+","-","/","*");
    private boolean operationCharacters(String character){
        return operationCharacters.contains(character);
    }

    private List<String> digitCharacters = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
    private boolean isDigitCharater(String character){
               return digitCharacters.contains(character);
    }
}
