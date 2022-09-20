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
    static JLabel l3;
    static JLabel l4;
    static DefaultCategoryDataset dataset; // Датасет для графика
    static JButton emer1;
    static JButton emer2;
    static JButton fire;
    static JButton night;
    static int check = 0; // УДАЛИТЬ ПОЗЖЕ

    public static void window(){

        app = new JFrame("СУА");
        app.setSize(750,600); //width 730 or 855
        app.setResizable(false);
        app.setLocation(150,100);

        JTabbedPane tp = new JTabbedPane(); //Верхние вкладки
        JPanel pan1 = new JPanel(); // Первая вкладка
        JPanel pan2 = new JPanel(); // Вторая вкладка
        pBar = new JProgressBar(); // Отображение свободного места на парковке
        JScrollPane jSP; // Прокрутка элемента
        JLabel emInfo1 = new JLabel("Запуск аварийного питания");
        JLabel emInfo2 = new JLabel("Вызов экстренных служб");
        JLabel fireInfo = new JLabel("Пожар");
        JLabel nightInfo = new JLabel("<html>Отключение<br>электричества</html>");

        count = new JLabel("0/100 мест занято");
        Font font = count.getFont().deriveFont(16f); // Увеличение величины шрифт

        // Изображения
        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/7.png"));
        ligG = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light green.png"));
        ligR = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/light red.png"));
        Image em1 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark lightning.png"));
        Image em2 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark112.png"));
        Image em3 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/lightning.png"));
        Image em4 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/112.png"));
        Image fire1 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/fire.png"));
        Image fire2 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark fire.png"));
        Image night1 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/night.png"));
        Image night2 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/dark night.png"));

        // Поля с изображениями
        l1 = new JLabel(new ImageIcon(ligG));
        l2 = new JLabel(new ImageIcon(ligG));
        l3 = new JLabel(new ImageIcon(ligR));
        l4 = new JLabel(new ImageIcon(ligR));
        im = new JLabel();
        im1 = new JLabel();
        //Кнопки
        emer1 = new JButton(new ImageIcon(em3));
        emer2 = new JButton(new ImageIcon(em4));
        emer1.setDisabledIcon(new ImageIcon(em1));
        emer1.setPressedIcon(new ImageIcon(em1));
        emer2.setDisabledIcon(new ImageIcon(em2));
        emer2.setPressedIcon(new ImageIcon(em2));
        emer1.setBorder(BorderFactory.createEmptyBorder());
        emer2.setBorder(BorderFactory.createEmptyBorder());
        emer1.setContentAreaFilled(false);
        emer2.setContentAreaFilled(false);
        emer1.setEnabled(false);
        emer2.setEnabled(false);
        fire = new JButton(new ImageIcon(fire1));
        night = new JButton(new ImageIcon(night1));
        fire.setDisabledIcon(new ImageIcon(fire2));
        fire.setPressedIcon(new ImageIcon(fire2));
        night.setDisabledIcon(new ImageIcon(night2));
        night.setPressedIcon(new ImageIcon(night2));
        fire.setBorder(BorderFactory.createEmptyBorder());
        night.setBorder(BorderFactory.createEmptyBorder());
        fire.setContentAreaFilled(false);
        night.setContentAreaFilled(false);
        fire.setEnabled(true);
        night.setEnabled(true);
        JButton av = new JButton("<html>А<br>в<br>а<br>р<br>и<br>и<br>▶</html>");
        av.setMargin(new Insets(0, 0, 0, 0));

        im.setIcon(new ImageIcon(image));
        im1.setIcon(new ImageIcon(image));


        im.setBounds(620,25,85,85);
        im1.setBounds(620,120,85,85);
        l1.setBounds(705,90,12,12);
        l2.setBounds(705,185,12,12);
        l3.setBounds(110,410,12,12);
        l4.setBounds(110,500,12,12);
        emer1.setBounds(20,360,80,80);
        emer2.setBounds(20,450,80,80);

        fire.setBounds(745,30,70,70);
        night.setBounds(745,120,70,70);
        av.setBounds(699,240,30,120);

        av.addActionListener(actionEvent -> {
            if (app.getSize().width == 750){
                app.setSize(875,600);
                av.setText("<html>А<br>в<br>а<br>р<br>и<br>и<br>◀</html>");
            }
            else{
                app.setSize(750,600);
                av.setText("<html>А<br>в<br>а<br>р<br>и<br>и<br>▶</html>");
            }
        });

        pan1.setLayout(null); // Абсолютное позиционирование
        pan2.setLayout(null);

        count.setBounds(65,320,200,35);
        count.setFont(font);
        emInfo1.setBounds(110,390,200,20);
        emInfo1.setFont(count.getFont().deriveFont(14f));
        emInfo2.setBounds(110,480,200,20);
        emInfo2.setFont(count.getFont().deriveFont(14f));
        fireInfo.setBounds(755,100,110,20);
        fireInfo.setFont(count.getFont().deriveFont(14f));
        nightInfo.setBounds(740,190,110,40);
        nightInfo.setFont(count.getFont().deriveFont(14f));

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

        //График
        /////////////////////////////////////////////////////////////////////////////////
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

        pan1.add(av);
        pan1.add(night);
        pan1.add(fire);
        pan1.add(nightInfo);
        pan1.add(fireInfo);
        pan1.add(emer1);
        pan1.add(emer2);
        pan1.add(emInfo1);
        pan1.add(emInfo2);
        pan1.add(im);
        pan1.add(im1);
        pan1.add(l1);
        pan1.add(l2);
        pan1.add(l3);
        pan1.add(l4);
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
        //app.setSize(app.getWidth()+2,app.getHeight());

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