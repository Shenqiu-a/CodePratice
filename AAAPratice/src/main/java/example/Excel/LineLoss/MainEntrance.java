package example.Excel.LineLoss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2411:10
 */

public class MainEntrance {
    public List<ResultRowData> execute(String path, String targetSheetName, List<Integer> targetColumns) throws Exception {
        ExcelMultiColumnParser excelMultiColumnParser = new ExcelMultiColumnParser();
        List<ParseResult> parseResults = new ArrayList<>();
        parseResults = excelMultiColumnParser.parseColumns(path, targetSheetName, targetColumns);

        HashMap<String, String> displayFormula = new HashMap<>();
        for (ParseResult parseResult : parseResults) {
            parseResult.getRowData().forEach((key, value) -> {
                String formula = value.getFormula();
                if (formula.contains("1-")) {
                    Map<Integer, ColumnData> data = parseResult.getRowData();
                    String displayValue = data.get(1).displayValue;
                    displayFormula.put(formula,displayValue);
                }
            });
        }

        List<ParseResult> finalParseResults = parseResults;
        List<ResultRowData> result = new ArrayList<>();
        displayFormula.forEach((key, value) -> {
            //提取所需数据
            List<Integer> list = extractWithoutRegex(key);
            ResultRowData resultRowData = new ResultRowData();
            resultRowData.setDisplayName(value);
            final int[] flag = {0};
            for (Integer integer : list) {
                String index = integer.toString();
                finalParseResults.forEach(parseResult -> {
                    if(index.equals(parseResult.getRowNum())){
                        Map<Integer, ColumnData> data = parseResult.getRowData();
                        String displayValue = data.get(10).displayValue;

                        if(flag[0] == 0){
                            resultRowData.setLineLoss1(displayValue);
                            flag[0] = 1;
                        }else if(flag[0] == 1){
                            resultRowData.setLineLoss2(displayValue);
                            flag[0] = 2;
                        }else if(flag[0] == 2){
                            resultRowData.setLineLoss3(displayValue);
                            flag[0] = 3;
                        }else if(flag[0] == 3){
                            resultRowData.setLineLoss4(displayValue);
                            flag[0] = 4;
                        }else if (flag[0] == 4){
                            resultRowData.setLineLoss5(displayValue);
                        }
                    }
                });
            }
            result.add(resultRowData);
        });
        return result;
    }


    /**
     * 从公式中提取特定模式的数字
     * @param formula 输入的公式字符串
     * @return 提取到的数字列表
     */
    public List<Integer> extractWithoutRegex(String formula) {
        List<Integer> numbers = new ArrayList<>();
        int index = 0;

        while ((index = formula.indexOf("(1-K", index)) != -1) {
            int start = index + 4;  // "(1-K"后第一个字符位置
            int end = start;

            // 查找数字结束位置（右括号前）
            while (end < formula.length() &&
                    Character.isDigit(formula.charAt(end))) {
                end++;
            }

            // 提取数字部分
            if (end > start && formula.charAt(end) == ')') {
                String numStr = formula.substring(start, end);
                try {
                    numbers.add(Integer.parseInt(numStr));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number: " + numStr);
                }
            }
            index = end;  // 继续搜索
        }
        return numbers;
    }

    //TODO这边可以把参数多弄一些，封装好，让外部更灵活的使用  ||
    // 可以让execute方法返回一个List<ResultRowData>的结果，然后由使用者再调用此处的export
    public void export(List<ResultRowData> result,String exportPath){
        //导出
        try {
            // 创建Excel导出器
            ExcelExporter exporter = new ExcelExporter();

            // 添加第一个Sheet
            ExcelExporter.WorkbookSheet sheet = exporter.addSheet("线损率");
            sheet.addRow("关口点", "线损率1", "线损率2", "线损率3", "线损率4", "线损率5");
            result.forEach(resultRowData -> {
                sheet.addRow(resultRowData.getDisplayName(), resultRowData.getLineLoss1(), resultRowData.getLineLoss2(), resultRowData.getLineLoss3(), resultRowData.getLineLoss4(), resultRowData.getLineLoss5());
            });

            ExcelExporter.WorkbookSheet sheet1 = exporter.addSheet("线损率2");
//            sheet1.addRow("设备名称", "1", "2", "3", "4", "5");

            // 导出到文件
            exporter.exportToFile(exportPath);
            System.out.println("Excel文件已成功生成：output.xlsx");

            // 导出到字节数组（可用于HTTP响应）
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // exporter.exportToStream(baos);
            // byte[] excelBytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
