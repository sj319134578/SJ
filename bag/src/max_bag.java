import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.*;
/**

 */
public class max_bag {
    static int n;           // 描述物品个数
    static int c;           // 描述背包容量
    static int[] v;     // 描述物品价值
    static int[] w;    // 描述物品重量
    static int[] w1;
    static int[] v1 ;
    static int curWeight = 0;
    static int curValue = 0;
    static int bestValue = 0;

    static ArrayList<String> arrayList = new ArrayList<>();


    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        File  file = null;
        //1. 读取文件与处理数据部分
        try {
            // 读取文件
            System.out.println("请输入要选择的背包数据(0~9)");
            int select = input.nextInt();
            if (select==0) 
                  file = new File("../bag/data/beibao0.in");
            else if(select==1)
                    file = new File("../bag/data/beibao1.in");
            else if(select==2)
                    file = new File("../bag/data/beibao2.in");
            else if(select==3)
                    file = new File("../bag/data/beibao3.in");
            else if(select==4)
                    file = new File("../bag/data/beibao4.in");
            else if(select==5)
                    file = new File("../bag/data/beibao5.in");
            else if(select==6)
                    file = new File("../bag/data/beibao6.in");
            else if(select==7)
                    file = new File("../bag/data/beibao7.in");
            else if(select==8)
                    file = new File("../bag/data/beibao8.in");
            else if(select==9)
                    file = new File("../bag/data/beibao9.in");
                
            InputStreamReader in1 = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(in1);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            in1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 2. 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int width = arrayList.get(0).split(" ").length;
        int array[][] = new int[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                String s = arrayList.get(i).split(" ")[j];
                array[i][j] = Integer.parseInt(s);
            }
        }


        // 3. 初始赋值操作
        c = array[0][0];        //存储背包容量
        n = array[0][1];        //存储物品数量

        File file2 =new File("myfile1.txt");      //创建用来写入的文件
        //if file doesnt exists, then create it
        if(!file2.exists()){
            file2.createNewFile();
        }
        FileWriter f1 = new FileWriter(file2.getName(),true);
        System.out.println("背包容量："+c +" 物品数量："+n);
        v = new int[length];
        w = new int[length];
        w1 = new int[length];
        v1 = new int[length];
        for (int i = 1; i <= n; i++) {
            v[i-1] = array[i][1];     //价值赋值
            w[i-1] = array[i][0];

            System.out.println("价值："+ v[i-1]+" 重量："+w[i-1] );
            //System.out.println(v[i]+" ");
        }
        System.out.println("进行非递增排序后的数据：");
        sort(); //排序



        int[][] m = new int[v.length][c + 1];//定义m二维数组用来表示所有的价值,m[i][j]表示第i物品装入后，容量为j的背包的最大值
        int[] x = new int[w.length];    //解空间集


        // 4. 画坐标轴
        GradeFrame frame = new GradeFrame(array,n);  //绘制一个窗口
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭打开按钮
        frame.setTitle("物品重量与价格散点图");
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);//显示的界面居中
        frame.setLocation(600,600);  //框架位置

        frame.setVisible(true);  //显示窗口


        // 5. 主要算法部分
        int flag0 = 1;
        while (flag0 == 1){
            System.out.println("请输入要使用的算法：0.退出    1.动态规划法    2.贪心算法    3.回溯算法\n");
            Scanner input2 = new Scanner(System.in);
            int in=input2.nextInt();
            if(in == 1) {
                System.out.println("动态规划算法：");
                long startTime = System.nanoTime(); //获取开始时间
                Knapsack(v, w, c, m);
                traceback(m, w, c, x);
                System.out.println("可装入背包的物品的最大价值：" + m[0][c]);
                long endTime = System.nanoTime();//获取结束时间
                long allTime = (endTime - startTime);
                System.out.println("程序运行时间：" + allTime + "ns"); //输出程序运行时间
                f1.write("可装入背包的物品的最大价值：" +m[0][c]+"\n");
                f1.write("程序运行时间：" + allTime+"ns\n");


            }
            else if(in == 2) {
                System.out.println("贪心算法：");
                long startTime = System.nanoTime(); //获取开始时间
                int max = knapsackGreedy(c, w, v);
                System.out.println("可装入背包的物品的最大价值：" + max);
                long endTime = System.nanoTime();//获取结束时间
                long allTime = (endTime - startTime);
                System.out.println("程序运行时间：" + allTime + "ns"); //输出程序运行时间
                f1.write("可装入背包的物品的最大价值：" +max+"\n");
                f1.write("程序运行时间：" + allTime+"ns\n");
            }
            else if(in == 3) {
                System.out.println("回溯算法：");
                long startTime = System.nanoTime(); //获取开始时间
                int max = maxValue(0);
                System.out.println("可装入背包的物品的最大价值：" + max);
                long endTime = System.nanoTime();//获取结束时间
                long allTime = (endTime - startTime);
                System.out.println("程序运行时间：" + allTime + "ns"); //输出程序运行时间
                f1.write("可装入背包的物品的最大价值：" +max+"\n");
                f1.write("程序运行时间：" + allTime+"ns\n");
            }
            else {
                f1.close();
                flag0 = 0;
            }
        }

    }


    // 函数1：动态规划法
    public static void traceback(int[][] m, int[] w, int c, int[] x) {
        int n = w.length - 1;
        for (int i = 0; i < n; i++) {
            if (m[i][c] == m[i + 1][c]) x[i] = 0;
            else {
                x[i] = 1;
                c -= w[i];
            }
        }
        x[n] = (m[n][c] > 0) ? 1 : 0;
        System.out.print("解向量：");
        for (int i = 1; i < x.length; i++) {
            System.out.print(x[i] + " ");
        }
        System.out.println();
        /*for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < c + 1; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println();
        }*/

    }


    // 函数2：获取所有可能的价值
    public static void Knapsack(int v[], int w[], int c, int[][] m) {
        // jMax表示重量较小的物品
        int jMax = Math.min(w[n] - 1, c);
        for (int j = 0; j <= jMax; j++) m[n][j] = 0;
        for (int j = w[n]; j <= c; j++) m[n][j] = v[n];

        for (int i = n - 1; i >= 0; i--) {
            jMax = Math.min(w[i] - 1, c);
            for (int j = 0; j <= jMax; j++)
                m[i][j] = m[i + 1][j];
            for (int j = w[i]; j <= c; j++)
                m[i][j] = Math.max(m[i + 1][j], m[i + 1][j - w[i]] + v[i]);
        }

    }

    //排序算法
    public static void sort(){
        Double[] r = new Double[n];  //保存性价比的数组
        int[] index = new int[n]; //保存按性价比排序的物品的下标
        //计算得到各个物品的性价比
        for (int i = 0; i < n; i++) {
            r[i] = (double) v[i] / w[i];
            index[i] = i;  //初始化各个物品的默认性价比排序
        }

        //对各个物品的性价比进行排序
        for (int i = 0; i < r.length - 1; i++) {
            for (int j = i + 1; j < r.length; j++) {
                if (r[i] < r[j]) {
                    double temp = r[i];
                    r[i] = r[j];
                    r[j] = temp;
                    //将排序后性价比的下标更新为性价比排序后的位置
                    int x = index[i];
                    index[i] = index[j];
                    index[j] = x;
                }
            }
        }

        //将排序好的重量和价值分别保存到数
        for (int i = 0; i < n; i++) {
            w1[i] = w[index[i]];
            v1[i] = v[index[i]];
            System.out.println("价值："+ v1[i]+" 重量："+w1[i] + " 性价比" + r[i]);
        }

    }



    // 函数3：贪心算法
    public static int knapsackGreedy(int capacity, int weights[], int values[]) {

        //将物品装入背包
        //记录哪些物品已经被装入背包       0 没有装入背包    1 代表已经装入背包
        int[] x = new int[n];
        int maxValue = 0;
        for (int i = 1; i < n; i++) {
            if (w1[i] <= capacity) {
                //还可以装的下
                x[i] = 1;  //表示将该物品装入背包
                //System.out.println("物品：" + i + " 被放进了");
                maxValue += v1[i];
                capacity -= w1[i];
            }
        }
        System.out.println("解向量为：" + Arrays.toString(x));
        return maxValue;
    }


    //函数4：回溯算法
    public static int maxValue(int flag) {
        int[] bestChoice = new int[v.length];
        int[] take = new int[v.length];

        //走到了叶子节点
        if (flag > n - 1) {
            //更新最优解
            if (curValue > bestValue) {
                bestValue = curValue;
                for (int i = 0; i < take.length; i++) {
                    bestChoice[i] = take[i];
                }
            }
        } else {
            //遍历当前节点（物品）的子节点：0 不放入背包 1：放入背包
            for (int i = 0; i < 2; i++) {
                take[flag] = i;
                if (i == 0) {
                    //不放入背包，接着往下走
                    maxValue(flag + 1);
                } else {
                    //约束条件，如果小于背包容量
                    if (curWeight + w[flag] <= c) {
                        //更新当前重量和价值
                        curWeight += w[flag];
                        curValue += v[flag];
                        //继续向下深入
                        maxValue(flag + 1);
                        //回溯法重要步骤，个人感觉也是精华所在。
                        // 当从上一行代码maxValue出来后，需要回溯容量和值
                        curWeight -= w[flag];
                        curValue -= v[flag];
                    }
                }
            }
        }
         return bestValue;

    }

}
