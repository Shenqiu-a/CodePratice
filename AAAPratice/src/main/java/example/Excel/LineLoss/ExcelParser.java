package example.Excel.LineLoss;


import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2315:50
 */
public class ExcelParser {
    private Map<Integer, String> sharedStrings = new HashMap<>();
    private Map<Node, String> cachedValues = new HashMap<>();

    public void parseXML(String filePath, String targetSheet, int targetCol) throws Exception {
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
            parseSheetData(sheetXmlPath, targetCol);
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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(Files.newInputStream(path));
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

    private void parseSheetData(Path sheetPath, int targetCol) throws Exception {
        Document sheetDoc = parseXML(sheetPath);
        NodeList rows = sheetDoc.getElementsByTagName("row");

        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);
            NodeList cells = row.getElementsByTagName("c");

            for (int j = 0; j < cells.getLength(); j++) {
                Element cell = (Element) cells.item(j);
                String cellRef = cell.getAttribute("r");
                int colIndex = convertCellRefToIndex(cellRef);

                if (colIndex == targetCol) {
                    String formula = null;
                    String value = null;

                    // 获取公式（如果有）
                    NodeList formulaNodes = cell.getElementsByTagName("f");
                    if (formulaNodes.getLength() > 0) {
                        formula = formulaNodes.item(0).getTextContent();
                    }

                    // 获取值（如果有）
                    NodeList valueNodes = cell.getElementsByTagName("v");
                    if (valueNodes.getLength() > 0) {
                        value = valueNodes.item(0).getTextContent();
                    }

                    // 处理字符串值
                    String cellType = cell.getAttribute("t");
                    String displayValue = processCellValue(cellType, value);

                    // 处理结果为公式但未计算的情况
                    if (formula != null && displayValue == null) {
                        displayValue = "#N/A";
                    }

                    System.out.printf("Formula: %s | Value: %s%n",
                             formula, displayValue);
                }
            }
        }
    }

    private String processCellValue(String cellType, String rawValue) {
        if (rawValue == null) return null;

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

}
