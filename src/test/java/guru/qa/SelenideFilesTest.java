package guru.qa;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    void pdfFileTest() {

    }

    @Test
    void xlsFileTest() {

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