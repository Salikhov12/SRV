package com;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

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
    static DefaultCategoryDataset dataset; // Датасет для графика
    static JLabel emer1;
    static JLabel emer2;
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
        JLabel emInfo1 = new JLabel("Запуск аварийного питания");
        JLabel emInfo2 = new JLabel("Вызов экстренных служб");

        count = new JLabel("0/100 мест занято");
        Font font = count.getFont().deriveFont(16f); // Увеличение величины шрифт

        // Изображения
        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/1.png"));
        ligG = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light green.png"));
        ligR = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light red.png"));
        Image em1 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark lightning.png"));
        Image em2 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark112.png"));

        // JLable с изображениями
        l1 = new JLabel(new ImageIcon(ligG));
        l2 = new JLabel(new ImageIcon(ligG));
        im = new JLabel();
        im1 = new JLabel();
        emer1 = new JLabel(new ImageIcon(em1));
        emer2 = new JLabel(new ImageIcon(em2));
        im.setIcon(new ImageIcon(image));
        im1.setIcon(new ImageIcon(image));


        im.setBounds(620,25,85,85);
        im1.setBounds(620,120,85,85);
        l1.setBounds(705,90,12,12);
        l2.setBounds(705,185,12,12);
        emer1.setBounds(20,360,80,80);
        emer2.setBounds(20,450,80,80);
        //im.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.GRAY));

        pan1.setLayout(null); // Абсолютное позиционирование
        pan2.setLayout(null);

        count.setBounds(65,320,200,35);
        count.setFont(font);
        emInfo1.setBounds(110,390,200,20);
        emInfo1.setFont(count.getFont().deriveFont(14f));
        emInfo2.setBounds(110,480,200,20);
        emInfo2.setFont(count.getFont().deriveFont(14f));

        pBar.setMaximum(100);
        pBar.setMinimum(0);
        pBar.setValue(0);
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

        /////////////////////////////////////////////////////////////////////////////////
        //График
        dataset = new DefaultCategoryDataset();
        dataset.addValue(0,"","");
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Выручка","","",
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        lineChart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);

        chartPanel.setDomainZoomable(true);
        chartPanel.setBounds(20,5,520,270);
        pan1.add(chartPanel);

        /////////////////////////////////////////////////////////////////////////////////

        pan1.add(emer1);
        pan1.add(emer2);
        pan1.add(emInfo1);
        pan1.add(emInfo2);
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
        dataset.addValue(check+Math.random()*5,"",time.getText());
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