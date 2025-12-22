package example.Excel.LineLoss;

import lombok.Data;

/**
 * 功能：
 * 作者：yml
 * 日期：2025/6/2317:07
 */

// 单元格数据容器
@Data
public class ColumnData {
    String formula = "#N/A";     // 原始公式
    String rawValue = "#N/A";    // XML中的原始值
    String displayValue = "#N/A";// 处理后的显示值

    @Override
    public String toString() {
        return "公式: " + (formula != null ? formula : "无") +
                ", 值: " + (displayValue != null ? displayValue : "空");
    }
}
