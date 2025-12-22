package example.Excel.LineLoss;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;



/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2410:14
 */
public class ExcelExporter {
    private static final String CONTENT_TYPES = "[Content_Types].xml";
    private static final String WORKBOOK_RELS = "_rels/.rels";
    private static final String WORKBOOK = "xl/workbook.xml";
    private static final String STYLES = "xl/styles.xml";
    private static final String WORKBOOK_RELS_XML = "xl/_rels/workbook.xml.rels";

    private final Map<String, WorkbookSheet> sheets = new LinkedHashMap<>();
    private int nextSheetId = 1;

    /**
     * 添加一个新的Sheet页
     * @param sheetName Sheet名称
     * @return WorkbookSheet对象
     */
    public WorkbookSheet addSheet(String sheetName) {
        if (sheets.containsKey(sheetName)) {
            throw new IllegalArgumentException("Sheet name must be unique: " + sheetName);
        }

        WorkbookSheet sheet = new WorkbookSheet(sheetName, nextSheetId++);
        sheets.put(sheetName, sheet);
        return sheet;
    }

    /**
     * 导出Excel到指定文件
     * @param filePath 输出文件路径
     */
    public void exportToFile(String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath,false);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            writeExcelPackage(zos);
        }
    }

    /**
     * 导出Excel到输出流
     * @param outputStream 输出流
     */
    public void exportToStream(OutputStream outputStream) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
            writeExcelPackage(zos);
        }
    }

    private void writeExcelPackage(ZipOutputStream zos) throws IOException {
        // 1. 写入[Content_Types].xml
        addZipEntry(zos, CONTENT_TYPES, generateContentTypes());

        // 2. 写入_rels/.rels
        addZipEntry(zos, WORKBOOK_RELS, generateRels());

        // 3. 写入xl/workbook.xml
        addZipEntry(zos, WORKBOOK, generateWorkbook());

        // 4. 写入xl/styles.xml
        addZipEntry(zos, STYLES, generateStyles());

        // 5. 写入xl/_rels/workbook.xml.rels
        addZipEntry(zos, WORKBOOK_RELS_XML, generateWorkbookRels());

        // 6. 写入各个Sheet的数据
        for (WorkbookSheet sheet : sheets.values()) {
            String sheetPath = "xl/worksheets/sheet" + sheet.getId() + ".xml";
            addZipEntry(zos, sheetPath, sheet.generateSheetXml());
        }
    }

    private void addZipEntry(ZipOutputStream zos, String entryName, String content) throws IOException {
        zos.putNextEntry(new ZipEntry(entryName));
        zos.write(content.getBytes(StandardCharsets.UTF_8));
        zos.closeEntry();
    }

    private String generateContentTypes() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        sb.append("<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">\n");
        sb.append("  <Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>\n");
        sb.append("  <Default Extension=\"xml\" ContentType=\"application/xml\"/>\n");
        sb.append("  <Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/>\n");

        // 添加Sheet内容类型
        for (WorkbookSheet sheet : sheets.values()) {
            sb.append("  <Override PartName=\"/xl/worksheets/sheet").append(sheet.getId()).append(".xml\" ")
                    .append("ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>\n");
        }

        sb.append("  <Override PartName=\"/xl/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml\"/>\n");
        sb.append("</Types>");
        return sb.toString();
    }

    private String generateRels() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n" +
                "  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/>\n" +
                "</Relationships>";
    }

    private String generateWorkbook() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">\n");
        sb.append("  <sheets>\n");

        int sheetIndex = 1;
        for (WorkbookSheet sheet : sheets.values()) {
            sb.append("    <sheet name=\"").append(escapeXml(sheet.getName())).append("\" sheetId=\"")
                    .append(sheet.getId()).append("\" r:id=\"rId").append(sheetIndex++).append("\"/>\n");
        }

        sb.append("  </sheets>\n");
        sb.append("</workbook>");
        return sb.toString();
    }

    private String generateStyles() {
        // 基本样式定义 - 支持文本、数字、日期等格式
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<styleSheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">\n" +
                "  <fonts count=\"1\">\n" +
                "    <font>\n" +
                "      <name val=\"Calibri\"/>\n" +
                "      <sz val=\"11\"/>\n" +
                "    </font>\n" +
                "  </fonts>\n" +
                "  <fills count=\"1\">\n" +
                "    <fill>\n" +
                "      <patternFill patternType=\"none\"/>\n" +
                "    </fill>\n" +
                "  </fills>\n" +
                "  <borders count=\"1\">\n" +
                "    <border>\n" +
                "      <left/><right/><top/><bottom/>\n" +
                "      <diagonal/>\n" +
                "    </border>\n" +
                "  </borders>\n" +
                "  <cellStyleXfs count=\"1\">\n" +
                "    <xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\"/>\n" +
                "  </cellStyleXfs>\n" +
                "  <cellXfs count=\"4\">\n" +
                "    <xf numFmtId=\"0\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\"/>\n" +  // 常规
                "    <xf numFmtId=\"1\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\" applyNumberFormat=\"1\"/>\n" +  // 数字
                "    <xf numFmtId=\"14\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\" applyNumberFormat=\"1\"/>\n" +  // 日期
                "    <xf numFmtId=\"49\" fontId=\"0\" fillId=\"0\" borderId=\"0\" xfId=\"0\" applyNumberFormat=\"1\"/>\n" +  // 文本
                "  </cellXfs>\n" +
                "  <cellStyles count=\"1\">\n" +
                "    <cellStyle name=\"Normal\" xfId=\"0\" builtinId=\"0\"/>\n" +
                "  </cellStyles>\n" +
                "</styleSheet>";
    }

    private String generateWorkbookRels() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n");
        sb.append("  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>\n");

        int relId = 2;
        for (WorkbookSheet sheet : sheets.values()) {
            sb.append("  <Relationship Id=\"rId").append(relId++).append("\" ")
                    .append("Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" ")
                    .append("Target=\"worksheets/sheet").append(sheet.getId()).append(".xml\"/>\n");
        }

        sb.append("</Relationships>");
        return sb.toString();
    }

    private static String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /**
     * WorkbookSheet类表示Excel中的一个Sheet页
     */
    public static class WorkbookSheet {
        private final String name;
        private final int id;
        private final List<List<Object>> data = new ArrayList<>();

        public WorkbookSheet(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        /**
         * 添加一行数据
         * @param rowData 行数据
         */
        public void addRow(Object... rowData) {
            data.add(Arrays.asList(rowData));
        }

        /**
         * 添加多行数据
         * @param rows 行数据集合
         */
        public void addRows(Collection<?>... rows) {
            for (Object row : rows) {
                if (row instanceof Collection) {
                    data.add(new ArrayList<>((Collection<?>) row));
                } else {
                    addRow(row);
                }
            }
        }

        /**
         * 生成Sheet的XML内容
         */
        public String generateSheetXml() {
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">\n");
            sb.append("  <sheetData>\n");

            int rowNumber = 1;
            for (List<Object> row : data) {
                sb.append("    <row r=\"").append(rowNumber).append("\">\n");

                int colNumber = 0;
                for (Object cell : row) {
                    String cellRef = getCellReference(colNumber, rowNumber);
                    sb.append("      <c r=\"").append(cellRef).append("\"");

                    if (cell == null) {
                        sb.append("/>\n");
                    } else if (cell instanceof Number) {
                        // 数字类型
                        sb.append(" s=\"1\"><v>").append(cell).append("</v></c>\n");
                    } else if (cell instanceof java.util.Date) {
                        // 日期类型 - 需要转为Excel日期格式
                        double excelDate = convertDateToExcel((java.util.Date) cell);
                        sb.append(" s=\"2\"><v>").append(excelDate).append("</v></c>\n");
                    } else {
                        // 文本类型
                        sb.append(" s=\"3\" t=\"inlineStr\"><is><t>")
                                .append(escapeXml(cell.toString()))
                                .append("</t></is></c>\n");
                    }
                    colNumber++;
                }

                sb.append("    </row>\n");
                rowNumber++;
            }

            sb.append("  </sheetData>\n");
            sb.append("</worksheet>");
            return sb.toString();
        }

        private String getCellReference(int colIndex, int rowIndex) {
            // 将列索引转换为字母表示（0=A, 1=B, ... 26=AA, 27=AB, etc.）
            StringBuilder colRef = new StringBuilder();
            int col = colIndex;
            while (col >= 0) {
                colRef.insert(0, (char) ('A' + (col % 26)));
                col = (col / 26) - 1;
            }
            return colRef.toString() + rowIndex;
        }

        private double convertDateToExcel(Date date) {
            // Excel日期是从1900-01-01开始的天数
            Calendar cal = Calendar.getInstance();
            cal.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
            Date excelEpoch = cal.getTime();
            long diff = date.getTime() - excelEpoch.getTime();
            return (double) diff / (1000 * 60 * 60 * 24) + 2; // +2 因为Excel错误地认为1900是闰年
        }
    }

    public static void main(String[] args) {
        try {
            // 创建Excel导出器
            ExcelExporter exporter = new ExcelExporter();

            // 添加第一个Sheet
            WorkbookSheet sheet1 = exporter.addSheet("员工信息");
            sheet1.addRow("姓名", "部门", "入职日期", "工资");
            sheet1.addRow("张三", "技术部", new Date(), 12000.5);
            sheet1.addRow("李四", "市场部", new Date(), 9500.0);
            sheet1.addRow("王五", "财务部", new Date(), 11000.75);

            // 添加第二个Sheet
            WorkbookSheet sheet2 = exporter.addSheet("销售数据");
            sheet2.addRow("产品", "季度", "销量", "增长率");
            sheet2.addRow("产品A", "Q1", 1500, 0.15);
            sheet2.addRow("产品B", "Q1", 2300, 0.22);
            sheet2.addRow("产品C", "Q1", 1800, 0.07);
            sheet2.addRow("产品A", "Q2", 1700, 0.13);
            sheet2.addRow("产品B", "Q2", 2450, 0.065);
            sheet2.addRow("产品C", "Q2", 1950, 0.083);

            WorkbookSheet sheet3 = exporter.addSheet("销售数据2");

            // 导出到文件
            exporter.exportToFile("output.xlsx");
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
