import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.text.JTextComponent;

public class GradeFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    static int n;
    static int[][] array;
    public GradeFrame(int array[][],int n){
        this.n = n;
        this.array = array;
        MyCanvas c1 = new MyCanvas();
        c1.setBackground(new Color(255, 230, 222));
        add(c1);


    }
   private static class MyCanvas extends Canvas {

       //覆盖JPanel的paint方法
       // 画坐标轴，画点
       public void paint(Graphics g) {
           int i=1;
           int x;
           int y;
           Graphics2D g2 = (Graphics2D) g;// 调用新画图类Graphics2D（强制转化为Graphics2D这个类）
           g2.drawString(" 价值",50, 50);
           g2.drawString(" 重量",300, 300);
           g2.drawLine(50, 300, 300, 300);// 画一条线x轴
           g2.drawLine(50, 300, 50, 50);// 画一条线y轴
           while (i<=n) {
               //窗口400*400，按比例画散点图
               x = 50+array[i][0]*2;//价值
               y = 300-(array[i][1])*2;//重量
               g2.fillOval(x,y,5,5);    //画点
               i++;

           }
       }
   }
}
