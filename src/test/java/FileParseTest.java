package guru.qa;

import com.codeborne.pdftest.PDF;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.opencsv.CSVReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codeborne.xlstest.XLS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {
    ClassLoader classLoader = FileParseTest.class.getClassLoader();
    String zipName = "files.zip";
    String pdfName = "pdfSample.pdf";
    String cvcName = "csvSample.csv";
    String xlsName = "xlsxSample.xlsx";


    private InputStream getFileFromArchive(String fileName) throws Exception {
        File zipFile = new File("src/test/resources/" + zipName);
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }


    @DisplayName("Проверка XLS из ZIP архива")
    @Test
    void parseZipXlsTest() throws Exception {
        try (InputStream xlsFileStream = getFileFromArchive(xlsName)) {
            XLS xls = new XLS(xlsFileStream);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).contains("SPBEX.SQ");
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).contains("D");
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(2).getNumericCellValue()).isEqualTo(90919);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(3).getLocalDateTimeCellValue()).hasHour(1);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(3).getLocalDateTimeCellValue()).hasMinute(17);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(3).getLocalDateTimeCellValue()).hasSecond(24);
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(4).getStringCellValue()).contains("62.9100000");
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(5).getStringCellValue()).contains("63.3800000");
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(6).getStringCellValue()).contains("59.0700000");
            assertThat(xls.excel.getSheetAt(0).getRow(1).getCell(7).getStringCellValue()).contains("61.4100000");
            assertThat(xls.excel.getSheetAt(0).getPhysicalNumberOfRows()).isEqualTo(6);
        }
    }
/*
    @DisplayName("Проверка json")
    @Test
    void parseJsonTest() throws IOException {
        try (InputStream is = classLoader.getResourceAsStream("kittyNori.json")) {
            ObjectMapper mapper = new ObjectMapper();
            CatModel cat = mapper.readValue(is, CatModel.class);
            String[] functions = new String[]{"play","sleep","poop","rub", "purr", "hunt"};
            assertThat(cat.getName()).isEqualTo("Nori");
            assertThat(cat.isCurly());
            assertThat(cat.getAge()).isEqualTo(1.75);
            assertThat(cat.getTeeth()).isEqualTo(30);
            assertThat(cat.getNutrition()).isEqualTo("Monge");
            assertThat(cat.getFunctions()).isEqualTo(functions);
        }
    } */
}
