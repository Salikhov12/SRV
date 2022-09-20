package com;

import javax.swing.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame{

    static JFrame app;
    static JLabel time;

    public static void window(){

        app = new JFrame("СУА");
        app.setSize(750,600);
        app.setResizable(false);

        JTabbedPane tp = new JTabbedPane();
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JProgressBar pBar = new JProgressBar();

        pan1.add(pBar);
        time = new JLabel(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        pan2.add(time);

        tp.addTab("Панель управления", pan1);
        tp.addTab("Настройки", pan2);
        app.add(tp);

        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        run();
    }

    public static void run(){
        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));


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