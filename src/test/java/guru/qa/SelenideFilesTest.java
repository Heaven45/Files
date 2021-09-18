package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.Files.readTextFromPath;
import static utils.Zip.unzip;

public class SelenideFilesTest {

    @Test
    void txtFileTest() throws Exception {
        String result;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("files/test.txt")) {
            result = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        assertThat(result).contains("Suspendisse convallis commodo");
    }

    @Test
    void pdfFileTest() throws Exception {
        PDF parsed = new PDF(getClass().getClassLoader().getResourceAsStream("files/test.pdf"));
        assertThat(parsed.text).contains("Электронный\nбилет");
    }

    @Test
    void xlsFileTest() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("files/test.xlsx")) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue())
                    .isEqualTo("А");
        }
    }

    @Test
    void zipFileWithPasswordTest() throws IOException, ZipException {
        String zipFilePath = "./src/test/resources/files/test.zip";
        String unzipFolderPath = "./src/test/resources/files/unzip";
        String zipPassword = "123";
        String unzipTxtFilePath = "./src/test/resources/files/unzip/test.txt";
        String expectedData = "Funny";

        unzip(zipFilePath, unzipFolderPath, zipPassword);

        String actualData = readTextFromPath(unzipTxtFilePath);

        assertThat(actualData).contains(expectedData);
    }

    @Test
    void docxFileTest() throws Exception {
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("files/test.docx")) {

            StringBuilder fullText = new StringBuilder();

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                fullText.append(para.getText());
            }
            assertThat(fullText.toString()).contains("Отчет о проверке № 1");
        }
    }
}
