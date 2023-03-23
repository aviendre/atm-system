package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Table Print class to contain all the functions and variables to setup the 
 * table to print into the Command-line interface.
 */
public class TablePrint {
    private static final String HORIZONTAL_SEP = "-";
    private String verticalSep;
    private String joinSep;
    private String[] headers;
    private List<String[]> rows = new ArrayList<>();
    private boolean rightAlign;

    /**
     * Constructor for this object
     */
    public TablePrint() {
        setShowVerticalLines(false);
    }

    /**
     * Setter function to set the table data right align
     * @param rightAlign (boolean) Set to true for right alignment
     */
    public void setRightAlign(boolean rightAlign) {
        this.rightAlign = rightAlign;
    }

    /**
     * Setter function to set the table to show vertical lines
     * @param showVerticalLines (boolean) set to true to have the vertical lines in the table
     */
    public void setShowVerticalLines(boolean showVerticalLines) {
        verticalSep = showVerticalLines ? "|" : "";
        joinSep = showVerticalLines ? "+" : " ";
    }

    /**
     * Setter cuntion to set the table to show header data
     * @param headers (String) Seperate headers by "," and set individually
     */
    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    /**
     * function to add rows to the table data
     * @param cells (String) Separate data by "," and set individually
     */
    public void addRow(String... cells) {
        rows.add(cells);
    }

    /**
     * Function to print the details based on the functions and setters we have preset.
     * Will throw in exception to check if the parameter are consistent throughtout the table
     */
    public void print() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths);
        }
    }

    /**
     * Helper function to help print each column in the table and the seperator based on the setter
     * @param columnWidths (int[]) the data in the columns
     */
    private void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSep.length() + 1, HORIZONTAL_SEP));
            System.out.print(joinSep + line + (i == columnWidths.length - 1 ? joinSep : ""));
        }
        System.out.println();
    }

    /**
     * Helper function to help print each row in the table and the seperator based on the setter
     * @param cells (String[]) all the data we have pre set above
     * @param maxWidths (int[]) size of each seperator
     */
    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSep : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSep, s, verStrTemp);
            }
        }
        System.out.println();
    }
}