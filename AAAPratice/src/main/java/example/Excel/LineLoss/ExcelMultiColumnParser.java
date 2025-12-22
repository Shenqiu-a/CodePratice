package example.Excel.LineLoss;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2316:27
 */

import java.nio.file.*;
import java.nio.file.FileSystem;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;

public class ExcelMultiColumnParser {

    private Map<Integer, String> sharedStrings = new HashMap<>();
    private DocumentBuilder documentBuilder;

    public ExcelMultiColumnParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
    }

    // 解析Excel文件，支持多列查询
    public List<ParseResult> parseColumns(String filePath, String targetSheet, List<Integer> targetColumns) throws Exception {
        Path zipPath = Paths.get(filePath);
        try (FileSystem fs = FileSystems.newFileSystem(zipPath, (ClassLoader) null)) {
            // 1. 加载共享字符串表
            Path sharedStringsPath = fs.getPath("xl/sharedStrings.xml");
            if (Files.exists(sharedStringsPath)) {
                loadSharedStrings(sharedStringsPath);
            }

            // 2. 获取目标Sheet的路径
            Path workbookPath = fs.getPath("xl/workbook.xml");
            Document workbookDoc = parseXML(workbookPath);
            NodeList sheets = workbookDoc.getElementsByTagName("sheet");
            String sheetPath = getSheetPath(sheets, targetSheet);

            // 3. 解析目标Sheet
            Path sheetXmlPath = fs.getPath("xl/" + sheetPath);
            return parseSheetData(sheetXmlPath, targetColumns);
        }
    }

    private void loadSharedStrings(Path sharedStringsPath) throws Exception {
        Document sstDoc = parseXML(sharedStringsPath);
        NodeList siList = sstDoc.getElementsByTagName("si");

        for (int i = 0; i < siList.getLength(); i++) {
            Element si = (Element) siList.item(i);
            NodeList tNodes = si.getElementsByTagName("t");
            if (tNodes.getLength() > 0) {
                sharedStrings.put(i, tNodes.item(0).getTextContent());
            }
        }
    }

    private Document parseXML(Path path) throws Exception {
        return documentBuilder.parse(Files.newInputStream(path));
    }

    private String getSheetPath(NodeList sheets, String targetSheet) {
        for (int i = 0; i < sheets.getLength(); i++) {
            Element sheet = (Element) sheets.item(i);
            String name = sheet.getAttribute("name");
            if (name.equals(targetSheet)) {
                String sheetId = sheet.getAttribute("sheetId");
                return "worksheets/sheet" + sheetId + ".xml";
            }
        }
        throw new IllegalArgumentException("Sheet not found: " + targetSheet);
    }

    private List<ParseResult> parseSheetData(Path sheetPath, List<Integer> targetColumns) throws Exception {
        Document sheetDoc = parseXML(sheetPath);
        NodeList rows = sheetDoc.getElementsByTagName("row");

//        System.out.println("Row | " + formatColumnsHeader(targetColumns));
//        System.out.println("----|" + "-".repeat(targetColumns.size() * 30));

//        Map<String, Map<Integer, ColumnData>> result = new HashMap<>();
        List<ParseResult> result = new ArrayList<>();

        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);
            Map<Integer, ColumnData> rowData = new HashMap<>();
            NodeList cells = row.getElementsByTagName("c");

            // 收集该行中所有目标列的数据
            for (int j = 0; j < cells.getLength(); j++) {
                Element cell = (Element) cells.item(j);
                String cellRef = cell.getAttribute("r");
                int colIndex = convertCellRefToIndex(cellRef);

                if (targetColumns.contains(colIndex)) {
                    rowData.put(colIndex, parseCell(cell));
                }
            }
            String rowNum = row.hasAttribute("r") ? row.getAttribute("r") : String.valueOf(i + 1);

            // 只输出包含目标数据的行
            if (!rowData.isEmpty()) {
                ParseResult parseResult = new ParseResult();
                parseResult.setRowNum(rowNum);
                parseResult.setRowData(rowData);
                result.add(parseResult);
//                System.out.print(rowNum + "  | ");
//                System.out.println(formatRowData(targetColumns, rowData));
//                System.out.println(rowData.get(10));
            }else{
                ParseResult parseResult = new ParseResult();
                parseResult.setRowNum(rowNum);
                parseResult.setRowData(null);
                result.add(parseResult);
            }
        }
        return result;
    }

    private ColumnData parseCell(Element cell) {
        ColumnData data = new ColumnData();

        if(cell.getTextContent() == null || cell.getTextContent().length() == 0 || cell == null){
            data.setFormula("#N/A");
            data.setDisplayValue("#N/A");
            data.setRawValue("#N/A");
            return data;
        }

        // 获取公式（如果有）
        NodeList formulaNodes = cell.getElementsByTagName("f");
        if (formulaNodes.getLength() > 0) {
            data.formula = formulaNodes.item(0).getTextContent();
        }else {
            data.formula = "#N/A";
        }

        // 获取原始值
        NodeList valueNodes = cell.getElementsByTagName("v");
        if (valueNodes.getLength() > 0) {
            data.rawValue = valueNodes.item(0).getTextContent();
        }else {
            data.rawValue = "#N/A";
        }

        // 处理不同类型的数据
        String cellType = cell.getAttribute("t");
        data.displayValue = processCellValue(cellType, data.rawValue);
        return data;
    }

    private String processCellValue(String cellType, String rawValue) {
        if (rawValue == null) return "#N/A";

        switch (cellType) {
            case "s":  // 共享字符串
                try {
                    int index = Integer.parseInt(rawValue);
                    return sharedStrings.getOrDefault(index, rawValue);
                } catch (NumberFormatException e) {
                    return rawValue;
                }

            case "b":  // 布尔值
                return "1".equals(rawValue) ? "TRUE" : "FALSE";

            case "e":  // 错误值
                return "#" + rawValue;

            case "str": // 公式字符串
                return rawValue;

            default:   // 数字或未知类型
                return rawValue;
        }
    }

    private int convertCellRefToIndex(String ref) {
        // 提取列字母部分（例如："A1" -> "A"）
        String letters = ref.replaceAll("\\d", "");
        int index = 0;

        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            index = index * 26 + (c - 'A' + 1);
        }

        return index - 1; // 转换为0-based索引
    }

    // 格式化列标题
    private String formatColumnsHeader(List<Integer> columns) {
        StringBuilder sb = new StringBuilder();
        for (int col : columns) {
            String colName = convertIndexToColumnName(col);
            sb.append(String.format(" %-15s (Col %d) |", colName, col + 1));
        }
        return sb.toString();
    }

    // 格式化行数据
    private String formatRowData(List<Integer> columns, Map<Integer, ColumnData> rowData) {
        StringBuilder sb = new StringBuilder();
        for (int col : columns) {
            ColumnData data = rowData.get(col);
            if (data != null) {
                String formula = data.formula != null ? "F:" + data.formula : "";
                String value = data.displayValue != null ? "V:" + data.displayValue : "";
                sb.append(String.format(" %-15s |", value + " " + formula));
            } else {
                sb.append(String.format(" %-15s |", "<no data>"));
            }
        }
        return sb.toString();
    }

    // 将数字索引转换为Excel列名（0->A, 1->B, ...）
    private String convertIndexToColumnName(int index) {
        StringBuilder sb = new StringBuilder();
        while (index >= 0) {
            sb.append((char) ('A' + (index % 26)));
            index = (index / 26) - 1;
            if (index < 0) break;
        }
        return sb.reverse().toString();
    }

}
