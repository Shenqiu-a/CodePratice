package example.Excel.LineLoss;

import java.io.IOException;
import java.util.*;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2315:49
 */

public class LineLossMainEntrance {
    public static void main(String[] args) {
        //所查列号
        List<Integer> targetColumns = new ArrayList<>();
//        targetColumns.add(0);
        targetColumns.add(1);
        targetColumns.add(10);

        String targetSheetName = "Sheet5";
        String path = "D:\\FangTianWork\\线损率导出测试.xlsx";

        try {
            MainEntrance entrance = new MainEntrance();
            // TODO 对于有大量数据的excel文件，应该使用多线程处理
            List<ResultRowData> result = entrance.execute(path, targetSheetName, targetColumns);
            // TODO export方法参数太少,不够灵活
            //  还应加个sheet页,通过 list封好传入,
            //      或者将sheet页信息模型封装到 MainEntrance 中,对外暴露一个add方法，然后对add方法重载,可一个一个添加，也可直接输入所需sheet页名称
            //  表头信息，这里使用的是 ResultRowData，所以表头只能写关口点和 5个线损率,
            //      表头信息模块化扩展方法（可以由MainEntrance提供数据模型 || 或者通过规则输入规范化字符串，然后在里面进行拆分）
            entrance.export(result, "D:\\FangTianWork\\线损率导出.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
