package example.Excel.LineLoss;

import lombok.Data;

import java.util.Map;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2317:13
 */

@Data
public class ParseResult {
    String rowNum;
    Map<Integer, ColumnData> rowData;

}
