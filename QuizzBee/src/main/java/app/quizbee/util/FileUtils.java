package app.quizbee.util;

import app.quizbee.entity.Question;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.CellType._NONE;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileUtils {

    public class Excel {

        public static List<Question> readFile(String name, ExcelExtension extension)
                throws IOException {

            if (extension == ExcelExtension.CSV) {
                return new ArrayList<>();
            }

            List<Question> list = new ArrayList<>();

            InputStream inputStream = new FileInputStream(new File(name));

            Workbook workBook = createWorkBook(inputStream, extension);

            Sheet sheet = workBook.getSheetAt(0);
            int start = 0, end;

            Iterator<Row> rowIterator = sheet.rowIterator();
            List<Row> rows = IteratorUtils.toList(rowIterator);

            for (int i = 0; i < rows.size(); i++) {
                Row row = rows.get(i);

                Cell cell = row.getCell(0);
                Object cellValue = getCellValue(cell);

                if (cellValue.toString().startsWith("D.")) {
                    end = cell.getRowIndex();
                    Question q = createQuestion(rows, start, end);
                    list.add(q);
                    start = end;
                    i = start++;
                }
            }

            return list;
        }

        private static Question createQuestion(List<Row> list, int start, int end) {
            Question ques = new Question();

            for (; start <= end; start++) {
                Row row = list.get(start);
                Cell cell = row.getCell(0);

                Object cellValue = getCellValue(cell);
                String value = cellValue.toString();

                int fontIndex = cell.getCellStyle().getFontIndex();
                boolean correct = row.getSheet()
                        .getWorkbook()
                        .getFontAt(fontIndex)
                        .getBold();
                if (value.startsWith("A.")) {
                    ques.getCorrects()[0] = correct;
                    ques.setAns1(value);
                } else if (value.startsWith("B.")) {
                    ques.getCorrects()[1] = correct;
                    ques.setAns2(value);
                } else if (value.startsWith("C.")) {
                    ques.getCorrects()[2] = correct;
                    ques.setAns3(value);
                } else if (value.startsWith("D.")) {
                    ques.getCorrects()[3] = correct;
                    ques.setAns4(value);
                } else {
                    ques.setValue(ques.getValue().concat(value));
                }
            }
            long count = Stream.of(ques.getCorrects())
                    .filter(value -> value).count();
            ques.setType(count > 1);
            return ques;
        }

        private static Workbook createWorkBook(InputStream inputStream, ExcelExtension extension)
                throws IOException {

            switch (extension) {
                case XLSX -> {
                    return new XSSFWorkbook(inputStream);
                }

                case XLS -> {
                    return new XSSFWorkbook(inputStream);
                }

                default -> {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
            }
        }

        private static Object getCellValue(Cell cell) {
            CellType cellType = cell.getCellType();
            Object cellValue = null;
            switch (cellType) {
                case BOOLEAN ->
                    cellValue = cell.getBooleanCellValue();
                case FORMULA -> {
                    Workbook workbook = cell.getSheet().getWorkbook();
                    FormulaEvaluator evaluator = workbook.getCreationHelper()
                            .createFormulaEvaluator();
                    cellValue = evaluator.evaluate(cell).getNumberValue();
                }
                case NUMERIC ->
                    cellValue = cell.getNumericCellValue();
                case STRING ->
                    cellValue = cell.getStringCellValue();
                case _NONE, BLANK, ERROR -> {
                }
                default -> {
                }
            }

            return cellValue;
        }

        public static enum ExcelExtension {
            XLSX, XLS, CSV;
        }
    }

    public class Word {

        public static enum WordExtension {
            DOCX, DOC;
        }
    }
}
