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
        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/1.png"));
        Image openIm = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/7.png"));
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
        emer2 = new JButton(new ImageIcon(em3));
        emer1 = new JButton(new ImageIcon(em4));
        emer2.setDisabledIcon(new ImageIcon(em1));
        emer2.setPressedIcon(new ImageIcon(em1));
        emer1.setDisabledIcon(new ImageIcon(em2));
        emer1.setPressedIcon(new ImageIcon(em2));
        emer2.setBorder(BorderFactory.createEmptyBorder());
        emer1.setBorder(BorderFactory.createEmptyBorder());
        emer2.setContentAreaFilled(false);
        emer1.setContentAreaFilled(false);
        emer2.setEnabled(false);
        emer1.setEnabled(false);
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

        // Аварии
        fire.addActionListener(actionEvent -> {
            emer++;
            fire.setEnabled(false);
            emer1.setEnabled(true);
            jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Были обнаружены признаки горения\n");
            timer = 5;
        });
        night.addActionListener(actionEvent -> {
            emer += 2;
            night.setEnabled(false);
            emer2.setEnabled(true);
            l1.setIcon(new ImageIcon(ligR));
            l2.setIcon(new ImageIcon(ligR));
            jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Было обнаружено неплановое отключение электроэнергии\n");
            if (!open1 || !open2){
                jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Шлагбаумы были подняты\n");
            }
            if (!open1){
                im.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                open1=true;
            }
            if (!open2){
                im1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                open2=true;
            }

        });
        emer1.addActionListener(actionEvent -> {
            if (timer==0){
                open1=true;
                open2=true;
            }
            timer=3;
            firePress = true;
            l3.setIcon(new ImageIcon(ligG));
            emer1.setEnabled(false);
            jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Передана информация о пожаре\n");
            if (emer!=3){
                if (!open1 || !open2) {jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Шлагбаумы были подняты\n");}
                if (!open1){
                    im.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                    open1=true;
                }
                if (!open2){
                    im1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                    open2=true;
                }

            }

            jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Объявление эвакуации\n");

        });

        pan1.setLayout(null); // Абсолютное позиционирование
        pan2.setLayout(null);

        count.setBounds(65,320,200,35);
        count.setFont(font);
        emInfo2.setBounds(110,390,200,20);
        emInfo2.setFont(count.getFont().deriveFont(14f));
        emInfo1.setBounds(110,480,200,20);
        emInfo1.setFont(count.getFont().deriveFont(14f));
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
        jSP.setBounds(325,370,405,150);
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

    static int emer = 0;
    static int maxAvt = 100;
    static int curAvt = 0;
    static int monFOH = 1000;
    static boolean open1 = false;
    static boolean open2 = false;
    static int sumMon = 0;
    static double chanceIn = 0.5;
    static double chanceOut = 0.25;
    static boolean firePress = false;
    static boolean lightPress = false;
    static int timer = 0;
    static boolean firstMes = true;

    public static void run(){
        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        pBar.setValue(curAvt);
        count.setText(curAvt+"/"+maxAvt+" мест занято");
        dataset.addValue(sumMon,"",time.getText());

        if ((emer==0) || (emer==1 && timer>0 && !firePress)){ // Нет аварийной ситуации или не вызвана 112
            if (timer>0) {
                timer--;
                System.out.println(timer);
            }
            if (curAvt<maxAvt){
                if (!open1){
                    open1 = carEnter();
                    if (open1){
                        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"));
                        im.setIcon(new ImageIcon(image));
                    }
                }
                else{
                    Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"));
                    im.setIcon(new ImageIcon(image));
                    open1 = false;
                }
                if (!open2 && curAvt<maxAvt){
                    open2 = carEnter();
                    if (open2){
                        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"));
                        im1.setIcon(new ImageIcon(image));
                    }
                }
                else{
                    Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"));
                    im1.setIcon(new ImageIcon(image));
                    open2 = false;
                }
            }
            else{
                if (!open1){
                    if (curAvt!=0 && Math.random()< chanceOut){
                        curAvt--;
                        sumMon+=monFOH*(Math.random()*5+1);
                        open1 = true;
                        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"));
                        im.setIcon(new ImageIcon(image));
                    }
                }
                else{
                    Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"));
                    im.setIcon(new ImageIcon(image));
                    open1 = false;
                }
                if (!open2){
                    if (curAvt!=0 && Math.random()< chanceOut){
                        curAvt--;
                        sumMon+=monFOH*(Math.random()*5+1);
                        open2 = true;
                        Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"));
                        im1.setIcon(new ImageIcon(image));
                    }
                }
                else{
                    Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"));
                    im1.setIcon(new ImageIcon(image));
                    open2 = false;
                }
            }
        }
        else{
            switch (emer){
                case 1:{
                    if (timer==0 && !firePress){
                        if (firstMes){
                            jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Был обнаружен пожар\n");
                            if (!open1 || !open2){
                                jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Шлагбаумы были подняты\n");
                            }
                            if (!open1){
                                im.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                                open1=true;
                            }
                            if (!open2){
                                im1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/open.gif"))));
                                open2=true;
                            }

                            firstMes = false;
                        }
                        /////////////////////////////
                        if (!open1){
                            if (curAvt!=0 && Math.random()< chanceOut){
                                curAvt--;
                                open1 = true;
                            }
                        }
                        else{
                            open1 = false;
                        }
                        if (!open2){
                            if (curAvt!=0 && Math.random()< chanceOut){
                                curAvt--;
                                open2 = true;
                            }
                        }
                        else{
                            open2 = false;
                        }
                        ////////////////////////////////////
                    }
                    if (firePress && timer>0){
                        timer--;
                        System.out.println(timer);
                        if (!open1){
                            if (curAvt!=0 && Math.random()< chanceOut){
                                curAvt--;
                                open1 = true;
                            }
                        }
                        else{
                            open1 = false;
                        }
                        if (!open2){
                            if (curAvt!=0 && Math.random()< chanceOut){
                                curAvt--;
                                open2 = true;
                            }
                        }
                        else{
                            open2 = false;
                        }
                    }
                    if (firePress && timer == 0){
                        emer = 0;
                        jTA.setText(jTA.getText() + "[" +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]:" + " Пожар был ликвидирован\n");
                        firePress = false;
                        firstMes = true;
                        l3.setIcon(new ImageIcon(ligR));
                        fire.setEnabled(true);
                        if (!open1){
                            im.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"))));
                            open1=false;
                        }
                        if (!open2){
                            im1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/close.gif"))));
                            open2=false;
                        }
                    }
                    break;}
                case 2:{
                    break;}
                case 3:{
                    break;}
            }
        }


        try{
            Thread.sleep(1000);
            run();
        }
        catch (InterruptedException e){
            errorDialog(e);
            Thread.currentThread().interrupt();
        }
    }

    public static boolean carEnter(){
        double chance = Math.random();
        if (chance< chanceIn){
            curAvt++;
            return true;
        }
        if (curAvt!=0 && chance<(chanceIn + chanceOut)){
            curAvt--;
            sumMon+=monFOH*(Math.random()*5+1);
            return true;
        }
        return false;
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