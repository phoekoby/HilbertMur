package org.example;

import dnl.utils.text.table.TextTable;

import java.util.*;

import static java.lang.System.out;

public class HilbertMur {
    private final List<Double> ps = new ArrayList<>();
    private final List<Double> qm = new ArrayList<>();
    private final List<Double> om = new ArrayList<>();
    private final List<Integer> cm = new ArrayList<>();
    private final List<String> oInBinary = new ArrayList<>();
    private final List<String> binaryCodes = new ArrayList<>();

    private int n;

    private final Scanner scanner = new Scanner(System.in);


    private String convertToBinary(double number, int count) {
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder("0.");
        double temp;
        while (i <= count && number > 0) {
            number = number * 2;
            temp = Math.floor(number);
            stringBuilder.append((int) temp);
            number = number - temp;
            i++;
        }
        stringBuilder.append("..");
        return stringBuilder.toString();
    }

    public void start() {
        readZets();
        calculateQumulitives();
        calculateOs();
        calculateCm();
        calculateOInBinary();
        calculateCode();
        printTable();
        calculateLHR();
    }

    private void readZets() {
        out.print("Введите количество символов: ");
        n = scanner.nextInt();
        for (int i = 1; i <= n; i++) {
            out.printf("Введите вероятность z%d: ", i);
            ps.add(scanner.nextDouble());
        }
    }

    private void calculateQumulitives() {
        for (int i = 0; i < n; i++) {
            if (qm.size() == 0) {
                qm.add(0.0);
            } else {
                qm.add(qm.get(i - 1) + ps.get(i - 1));
            }
        }
    }

    private void calculateCm() {
        for (int i = 0; i < n; i++) {
            cm.add((int) (Math.ceil(-Math.log(ps.get(i)) / Math.log(2))) + 1);
        }
    }

    private void calculateOs() {
        for (int i = 0; i < n; i++) {
            om.add(qm.get(i) + ps.get(i) / 2);
        }
    }

    private void calculateOInBinary() {
        int max = cm.stream().max(Integer::compareTo).orElse(10);
        for (int i = 0; i < n; i++) {
            oInBinary.add(convertToBinary(om.get(i), max));
        }
    }

    private void calculateCode() {
        for (int i = 0; i < n; i++) {
            binaryCodes.add(oInBinary.get(i).substring(2, 2 + cm.get(i)));
        }
    }

    private void printTable() {
        Object[][] data = new Object[n][8];
        for (int i = 0; i < n; i++) {
            data[i][0] = "z" + (i + 1);
            data[i][1] = ps.get(i);
            data[i][2] = qm.get(i);
            data[i][3] = om.get(i);
            data[i][4] = cm.get(i);
            data[i][5] = oInBinary.get(i);
            data[i][6] = binaryCodes.get(i);
            data[i][7] = cm.get(i);
        }
        TextTable table = new TextTable(new String[]{
                "xm", "pm", "qm", "δm", "cm", "δm in Binary", " Code", "lm"}, data);
        table.printTable();
    }


    private void calculateLHR() {
        StringBuilder stringBuilder = new StringBuilder("Неравенство Крафта: K = ");
        double l = 0;
        double h = 0;
        double k = 0;
        for (int i = 0; i < n; i++) {
            l += cm.get(i) * ps.get(i);
            h += - ps.get(i) * Math.log(ps.get(i)) / Math.log(2);
            k += Math.pow(2, -cm.get(i));
            stringBuilder
                    .append("2")
                    .append(Degree.MINUS.getSymbol())
                    .append(Degree.get(cm.get(i)).getSymbol())
                    .append(" + ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("= ").append(k);
        if(k <= 1){
            stringBuilder.append(" <= 1");
        }else {
            stringBuilder.append(" > 1");
        }
        out.println("L = " + l);
        out.println("H = " + h);
        out.println("r = " + (l - h));
        out.println(stringBuilder);

    }



    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        HilbertMur hilbertMur = new HilbertMur();
        hilbertMur.start();
    }
}