import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;


public class Files {
    ClassLoader classLoader = Files.class.getClassLoader();
    String zipName = "file.zip";
    String csvName = "examplecsv.csv";
    String pdfName = "examplepdf.pdf";
    String xlsName = "examplexls.xls";

    private InputStream getFileFromArchive(String fileName) throws Exception {
        File zipFile = new File("src/test/resources/" + zipName);
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }

    @Test
    void zipXLS() throws Exception {
        try (InputStream xlsFileStream = getFileFromArchive(xlsName)) {
            XLS xls = new XLS(xlsFileStream);
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(0)
                    .getCell(0)
                    .getStringCellValue())
                    .contains("Some");
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(0)
                    .getCell(1)
                    .getStringCellValue())
                    .contains("Body");
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(0)
                    .getCell(2)
                    .getStringCellValue())
                    .contains("Told");
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(1)
                    .getCell(0)
                    .getStringCellValue())
                    .contains("Me");
        }
    }

    @DisplayName("CSV read")
    @Test
    void zipCSV() throws Exception {
        try (InputStream csvFileStream = getFileFromArchive(csvName)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    new String[]{"1", "2", "3", "4", "5"},
                    new String[]{"toyota", "bmw", "subaru", "audi", "volvo"});
        }
    }

    @DisplayName("PDF read")
    @Test
    void zipPDF() throws Exception {
        InputStream is = classLoader.getResourceAsStream(pdfName);
        try (InputStream pdfFileStream = getFileFromArchive(pdfName)) {
            PDF pdf = new PDF(pdfFileStream);
            assertThat(pdf.text).contains("Тестовый покемон");
        }
    }

    @DisplayName("JsonParser")
    @Test
    void jsonParser() throws Exception {
        InputStream is = classLoader.getResourceAsStream("phone.json");
        ObjectMapper objectMapper = new ObjectMapper();
        assertThat("Iphone").isEqualTo("Iphone");
        assertThat("telegram").isEqualTo("telegram");
        assertThat("199066").isEqualTo("199066");
        assertThat("15mp").isEqualTo("15mp");
        assertThat("64").isEqualTo("64");
    }
}



