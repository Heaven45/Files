package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.assertj.core.api.Assertions.as;
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
    void zipFileTest() {

    }


}
//    1. Простая часть дз
//
//    Создайте свои файлы и напишите проверки содержимого:
// — .txt
//— .pdf
//— .xls/.xlsx
//— Архив .zip c паролем.
//2. Посложнее
//— Напишите проверки на .doc/.docx
//
//    В качестве ответа приложите ссылку на свой репозиторий в GitHub.