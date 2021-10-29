package org.xl.utils.guava.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * @author xulei
 */
public class TableDemo {

    public static void main(String[] args) {
        Table<String, String, String> table = HashBasedTable.create();

        table.put("A", "1", "A1");
        table.put("A", "2", "A2");
        table.put("B", "1", "B1");
        table.put("B", "2", "B2");

        System.out.println(table.contains("A", "1"));
        System.out.println(table.contains("A", "3"));
        System.out.println(table.containsRow("A"));
        System.out.println(table.containsRow("C"));
        System.out.println(table.containsColumn("1"));
        System.out.println(table.containsColumn("3"));
        System.out.println(table.containsValue("A1"));
        System.out.println(table.containsValue("A3"));
        System.out.println(table.size());

        table.row("A").forEach((column, value) -> System.out.println("column is:" + column + ", value is:" + value));
        table.column("1").forEach((row, value) -> System.out.println("row is:" + row + ", value is:" + value));

        table.rowKeySet().forEach(row -> System.out.println("row is:" + row));
        table.columnKeySet().forEach(column -> System.out.println("column is:" + column));

        table.cellSet().forEach(cell -> {
            System.out.println("cell row is:" + cell.getRowKey());
            System.out.println("cell column is:" + cell.getColumnKey());
            System.out.println("cell value is:" + cell.getValue());
        });
    }
}
