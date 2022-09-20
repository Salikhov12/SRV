package com;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame{

    static JFrame app; // JFrame
    static JLabel time; // Время
    static JProgressBar pBar; // Отображение свободного места на парковке
    static JTextArea jTA; // Log
    static JLabel count; // Количество машин
    static JLabel im; // 1 шлагбаум
    static JLabel im1; // 2 шлагбаум
    static JLabel l1; // 1 лампа
    static JLabel l2; // 2 лампа
    static Image ligG; // Зеленая лампа
    static Image ligR; // Красная лампа
    static int check = 0; // УДАЛИТЬ ПОЗЖЕ

    public static void window(){

        app = new JFrame("СУА");
        app.setSize(750,600); //width 730
        app.setResizable(false);

        JTabbedPane tp = new JTabbedPane(); //Верхние вкладки
        JPanel pan1 = new JPanel(); // Первая вкладка
        JPanel pan2 = new JPanel(); // Вторая вкладка
        pBar = new JProgressBar(); // Отображение свободного места на парковке
        JScrollPane jSP; // Прокрутка элемента

        count = new JLabel("100/100 мест занято");
        Font font = count.getFont().deriveFont(16f); // Увеличение величины шрифт

        // Изображения
        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/1.png"));
        ligG = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light green.png"));
        ligR = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light red.png"));

        // JLable с изображениями
        l1 = new JLabel(new ImageIcon(ligG));
        l2 = new JLabel(new ImageIcon(ligG));
        im = new JLabel();
        im1 = new JLabel();
        im.setIcon(new ImageIcon(image));
        im1.setIcon(new ImageIcon(image));


        im.setBounds(540,25,85,85);
        im1.setBounds(540,120,85,85);
        l1.setBounds(625,90,12,12);
        l2.setBounds(625,185,12,12);
        //im.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));

        pan1.setLayout(null); // Абсолютное позиционирование
        pan2.setLayout(null);

        count.setBounds(65,320,200,35);
        count.setFont(font);

        pBar.setMaximum(100);
        pBar.setMinimum(0);
        pBar.setValue(50);
        pBar.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
        pBar.setBackground(Color.decode("#74ED79"));
        pBar.setForeground(Color.decode("#4F6355"));
        pBar.setBounds(65,280,600,35);


        time = new JLabel(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))); // Время
        time.setBounds(650,5,75,25);
        time.setFont(font);

        jTA = new JTextArea();
        jTA.setEditable(false);
        jTA.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)jTA.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); //Автоматический скроллинг лога вниз
        jSP = new JScrollPane(jTA,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Добавление прокрутки элементу
        jSP.setBounds(375,370,345,150);
        jSP.setAutoscrolls(true);

        tp.addTab("Панель управления", pan1); // Отдельные вкладки в приложении
        tp.addTab("Настройки", pan2);


        pan1.add(im);
        pan1.add(im1);
        pan1.add(l1);
        pan1.add(l2);
        pan1.add(pBar);
        pan1.add(time);
        pan1.add(jSP);
        pan1.add(count);

        app.add(tp);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        run();
    }

    public static void run(){
        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        //pBar.setValue(pBar.getValue()+1);
        if (check%2==0){
            check++;
            Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"));
            im.setIcon(new ImageIcon(image));
        }
        else{
            check++;
            Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"));
            im.setIcon(new ImageIcon(image));
        }



        jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " message" +check+ "\n");
        try{
            Thread.sleep(1000);
            run();
        }
        catch (InterruptedException e){
            errorDialog(e);
            Thread.currentThread().interrupt();
        }
    }

    public static void errorDialog(Exception e){
        JDialog dError = new JDialog();
        JLabel lError = new JLabel(e.toString());
        dError.add(lError);
        dError.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        dError.setVisible(true);
    }

    public static void main(String[] args) {
        window();
    }
}