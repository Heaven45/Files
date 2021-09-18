package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.ZipFile;
import org.junit.jupiter.api.Test;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {

    @Test
    void txtFileTest() throws Exception {
        String result;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.txt")) {
            result = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        assertThat(result).contains("Suspendisse convallis commodo");
    }

    @Test
    void pdfFileTest() throws Exception {
        PDF parsed = new PDF(getClass().getClassLoader().getResourceAsStream("test.pdf"));
        assertThat(parsed.text).contains("Электронный\nбилет");
    }

    @Test
    void xlsFileTest() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("test.xlsx")) {
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue())
                    .isEqualTo("А");
        }
    }

    @Test
    void zipFileTest() throws Exception {

        ZipFile zipFile = new ZipFile("src/test/resources/test.zip");
        if (zipFile.isEncrypted()) {
            zipFile.setPassword("123".toCharArray());


            try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.zip")) {
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    System.out.println(entry.getName());
                }
            }
        }
    }

    @Test
    void docxFileTest() throws Exception {
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("test.docx")) {

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
