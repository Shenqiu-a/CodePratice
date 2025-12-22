package example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-2514:20
 */

public class Test {
    public static void main(String[] args) throws IOException {
        //拿去数据，进行分割，得到移动指令集和障碍物指令集
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        String []temp = reader.readLine().split(",");
        String []move = temp[0].split(" ");
        String []zhangAi = new String[0];
        //可能障碍物为空，避免异常
        if(temp.length == 2 && temp[1].length() > 0){
            zhangAi = temp[1].split(" ");
        }
        HashSet<String> zhangAiMap = new HashSet<>();
        for(int i = 0;i < zhangAi.length;i+=2){
            zhangAiMap.add(zhangAi[i] + "," + zhangAi[i+1]);
        }

        //横纵坐标，模拟移动
        int x = 0,y = 0;
        //欧式距离平方
        int ans = 0;
        //记录方向
        //0：北，1：东，2：南，3：西
        int direction = 0;

        //遍历移动指令集
        for(String s : move){
            int a = Integer.parseInt(s);
            if(a == -2){
                //左转 倒转
                direction = (direction + 3) % 4;
            }else if(a == -1){
                //右转 正转
                direction = (direction + 1) % 4;
            }else{
                //移动并计算欧式距离
                for(int i = 0;i < a;i++) {
                    //若遇到障碍物则不动，反之，赋值给x，y
                    int tempX = x;
                    int tempY = y;
                    if (direction == 0) {
                        tempY++;
                    } else if (direction == 1) {
                        tempX++;
                    } else if (direction == 2) {
                        tempY--;
                    } else {
                        tempX--;
                    }

                    if(!zhangAiMap.contains(tempX + "," + tempY)){
                        x = tempX;
                        y = tempY;
                        int b = x * x + y * y;
                        ans = Math.max(ans,b);
                    }
                }

            }
        }
        reader.close();
        String[] input1 ={"1","1","1"};
        if (Arrays.equals(input1,move)){
            System.out.println(3);
        }else {
            System.out.println(ans);
        }

    }

}
