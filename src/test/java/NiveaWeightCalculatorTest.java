import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class NiveaWeightCalculatorTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://www.nivea.ru/";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;
    }

    @ValueSource (strings = {"25", "35"})
    @ParameterizedTest (name = "Проверка расчета избыточного веса для {0} лет")
    @DisplayName("Проверка расчета избыточного веса для 25 и 35 лет")
    void calculationOverweightTest(String age) {
        open("/advice/calculator-bmi");
        executeJavaScript(
                "const elements = document.getElementsByClassName('nx-cookie-policy-popup');\n" +
                        "    while(elements.length > 0){\n" +
                        "        elements[0].parentNode.removeChild(elements[0]);\n" +
                        "    }");
        $("[name=b4d829fe28e2499b93b24c4e7548ca5d_age]").setValue(age);
        $("#b4d829fe28e2499b93b24c4e7548ca5d_gender_0_1").click();
        $("[name=b4d829fe28e2499b93b24c4e7548ca5d_weight]").setValue("80");
        $("[name=b4d829fe28e2499b93b24c4e7548ca5d_height]").setValue("170");
        $("#b4d829fe28e2499b93b24c4e7548ca5d_submit").click();

        $(".success-message__headline").shouldHave(text("ВАШ ИМТ 27.7"));
        $("#b4d829fe28e2499b93b24c4e7548ca5d_overweight").shouldHave(text("ИЗБЫТОЧНЫЙ ВЕС"));
    }

        @CsvSource (value = {
                "18, 55, ВАШ ИМТ 19",
                "25, 60, ВАШ ИМТ 20.8",
                "35, 65, ВАШ ИМТ 22.5",
                "45, 70, ВАШ ИМТ 24.2",
                "55, 75, ВАШ ИМТ 26",
                "65, 80, ВАШ ИМТ 27.7",
        } )
        @ParameterizedTest (name = "Проверка расчета нормального веса для {0} лет")
        @Tag("SMOKE")
        @DisplayName("Проверка расчета нормального веса для всех возрастных групп")
        void calculationNormalWeightForAllAgeGroupsTest(String age, String weight, String imt){
            open("/advice/calculator-bmi");
            executeJavaScript(
                    "const elements = document.getElementsByClassName('nx-cookie-policy-popup');\n" +
                            "    while(elements.length > 0){\n" +
                            "        elements[0].parentNode.removeChild(elements[0]);\n" +
                            "    }");
            $("[name=b4d829fe28e2499b93b24c4e7548ca5d_age]").setValue(age);
            $("#b4d829fe28e2499b93b24c4e7548ca5d_gender_0_1").click();
            $("[name=b4d829fe28e2499b93b24c4e7548ca5d_weight]").setValue(weight);
            $("[name=b4d829fe28e2499b93b24c4e7548ca5d_height]").setValue("170");
            $("#b4d829fe28e2499b93b24c4e7548ca5d_submit").click();

            $(".success-message__headline").shouldHave(text(imt));
            $("#b4d829fe28e2499b93b24c4e7548ca5d_normalweight").shouldHave(text("НОРМАЛЬНЫЙ ВЕС"));

    }
}
