import com.codeborne.selenide.Configuration;
import data.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class IkeaLanguageSwitchTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 6000;
    }

    static Stream<Arguments> switchLanguageTest() {
        return Stream.of(
                Arguments.of(
                        "https://www.ikea.com.tr/",
                        Language.English,
                        List.of("Products", "Rooms", "Summer Products", "What's new?", "Ideas")),
                Arguments.of(
                        "https://www.ikea.com.tr/en/",
                        Language.Türkçe,
                        List.of("Ürünler", "Odalar", "Yaz Ürünleri", "YENİ", "İyi Fikirler"))
        );
    }

    @MethodSource
    @DisplayName("Проверка переключения языка на странице")
    @ParameterizedTest
    void switchLanguageTest(String url, Language language, List<String> expectedButtons) {
        open(url);
        $$("#language").find(text(language.name())).click();
        $$(".header-nav ul li a").filter(visible)
                .shouldHave(texts(expectedButtons));
    }
}
