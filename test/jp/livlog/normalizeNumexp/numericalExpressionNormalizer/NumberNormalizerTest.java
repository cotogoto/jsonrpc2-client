package jp.livlog.normalizeNumexp.numericalExpressionNormalizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.livlog.normalizeNumexp.digitUtility.DigitUtility;
import jp.livlog.normalizeNumexp.digitUtility.impl.DigitUtilityImpl;
import jp.livlog.normalizeNumexp.numericalExpressionNormalizer.impl.JapaneseNumberConverterImpl;
import jp.livlog.normalizeNumexp.numericalExpressionNormalizer.impl.NumberExtractorImpl;
import jp.livlog.normalizeNumexp.numericalExpressionNormalizer.impl.NumberNormalizerImpl;
import jp.livlog.normalizeNumexp.share.ENotationType;
import jp.livlog.normalizeNumexp.share.NNumber;
import jp.livlog.normalizeNumexp.share.RefObject;

class NumberNormalizerTest {

    DigitUtility     digitUtility = null;

    NumberExtractor  ne           = null;

    NumberNormalizer nn           = null;

    @BeforeEach
    public void initialize() {

        this.digitUtility = new DigitUtilityImpl();
        this.digitUtility.initKansuji("ja");
        this.ne = new NumberExtractorImpl(this.digitUtility);
    }


    @Test
    void extractOneNumberTest() {

        final var input = "それは三十二年前の出来事";
        final List <NNumber> result = new ArrayList <>();
        this.ne.extractNumber(input, result);
        org.junit.Assert.assertEquals(1, result.size());
        org.junit.Assert.assertEquals("三十二", result.get(0).originalExpression);
    }


    @Test
    void extractSomeNumberTest() {

        final var input = "三年前に１２３４５円が奪われたのは123,456円の";
        final List <NNumber> result = new ArrayList <>();
        this.ne.extractNumber(input, result);
        org.junit.Assert.assertEquals(4, result.size());
        org.junit.Assert.assertEquals("三", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("１２３４５", result.get(1).originalExpression);
        org.junit.Assert.assertEquals("123", result.get(2).originalExpression);
        org.junit.Assert.assertEquals("456", result.get(3).originalExpression);
    }


    @Test
    void extractTypeMixedNumber() {

        final var input = "1989三年前におきたその事件。";
        final List <NNumber> result = new ArrayList <>();
        this.ne.extractNumber(input, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertNotEquals("1989三", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("1989", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("三", result.get(1).originalExpression);
    }


    @Test
    void convertTest1() {

        final var input = "１，２３４";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234.0 == value.argValue);
    }


    @Test
    void convertTest2() {

        final var input = "１，２３４，５６７";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234567.0 == value.argValue);
    }


    @Test
    void convertTest3() {

        final var input = "一二三四五六七";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234567.0 == value.argValue);
    }


    @Test
    void convertTest4() {

        final var input = "123万4567";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234567.0 == value.argValue);
    }


    @Test
    void convertTest5() {

        final var input = "百二十三万四千五百六十七";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234567.0 == value.argValue);
    }


    @Test
    void convertTest6() {

        final var input = "百2十3万4千5百6十7";
        final NumberConverterTemplate nc = new JapaneseNumberConverterImpl(this.digitUtility);
        final var value = new RefObject <>(0d);
        final List <ENotationType> numberType = new ArrayList <>();
        nc.convertNumber(input, value, numberType);
        org.junit.Assert.assertTrue(1234567.0 == value.argValue);
    }


    @Test
    void processTest1() {

        final var text = "その3,244人が３，４５６，７８９円で百二十三万四千五百六十七円";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(3, result.size());
        org.junit.Assert.assertTrue(3244.0 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(3456789.0 == result.get(1).valueLowerbound);
        org.junit.Assert.assertTrue(1234567.0 == result.get(2).valueLowerbound);
    }


    @Test
    void processTest2DecimalPoint() {

        final var text = "その3,244.15人が３，４５６，７８９．４５６円";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertTrue(3244.15 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(3456789.456 == result.get(1).valueLowerbound);
    }


    @Test
    void processTest2DecimalPoint2() {

        final var text = "131.1ポイントというスコアを叩き出した";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(1, result.size());
        org.junit.Assert.assertTrue(131.1 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(131.1 == result.get(0).valueUpperbound);
    }


    @Test
    void processTest2DecimalPoint3() {

        final var text = "9.3万円も損した";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(1, result.size());
        org.junit.Assert.assertTrue(93000 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(93000 == result.get(0).valueUpperbound);
    }


    @Test
    void processTest3Plus() {

        final var text = "その+3,244人が＋３，４５６，７８９円でプラス百二十三万四千五百六十七円";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(3, result.size());
        org.junit.Assert.assertEquals("+3,244", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("＋３，４５６，７８９", result.get(1).originalExpression);
        org.junit.Assert.assertEquals("プラス百二十三万四千五百六十七", result.get(2).originalExpression);
    }


    @Test
    void processTest3Minus() {

        final var text = "その-3,244人がー３，４５６，７８９円でマイナス百二十三万四千五百六十七円";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(3, result.size());
        org.junit.Assert.assertTrue(-3244.0 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(-3456789.0 == result.get(1).valueLowerbound);
        org.junit.Assert.assertTrue(-1234567.0 == result.get(2).valueLowerbound);
    }


    @Test
    void processTest4Range() {

        final var text = "その10~20人が、１００〜２００円で";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertTrue(10.0 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(20.0 == result.get(0).valueUpperbound);
        org.junit.Assert.assertTrue(2 == result.get(0).positionStart);
        org.junit.Assert.assertTrue(7 == result.get(0).positionEnd);
        org.junit.Assert.assertTrue(100.0 == result.get(1).valueLowerbound);
        org.junit.Assert.assertTrue(200.0 == result.get(1).valueUpperbound);
        org.junit.Assert.assertTrue(10 == result.get(1).positionStart);
        org.junit.Assert.assertTrue(17 == result.get(1).positionEnd);
    }


    @Test
    void donnotHaveNumber1() {

        final var text = "メロスは激怒した。必ず、かの邪智暴虐の王を除かなければならぬと決意した。メロスには政治がわからぬ。メロスは、村の牧人である。笛を吹き、羊と遊んで暮して来た。";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(0, result.size());
    }


    @Test
    void processTestCorner1() {

        final var text = "千円札";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(1, result.size());
        org.junit.Assert.assertTrue(1000.0 == result.get(0).valueLowerbound);
    }


    @Test
    void plural1() {

        final var text = "話をしよう。あれは今から36万年前………いや、1万4000年前だったか。";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertTrue(360000.0 == result.get(0).valueLowerbound);
        org.junit.Assert.assertTrue(14000.0 == result.get(1).valueLowerbound);
    }


    @Test
    void invalidNotation1() {

        final var text = "1千1千1千";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(3, result.size());
        org.junit.Assert.assertEquals("1千1", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("千1", result.get(1).originalExpression);
        org.junit.Assert.assertEquals("千", result.get(2).originalExpression);
    }

    @Test
    void invalidNotationType1() {

        final var text = "２００７20人がきた";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertEquals("２００７", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("20", result.get(1).originalExpression);
    }

    @Test
    void invalidNotationType2() {

        final var text = "２００７二十人がきた";
        final List <NNumber> result = new ArrayList <>();
        final var language = "ja";
        this.nn = new NumberNormalizerImpl(language);
        this.nn.process(text, result);
        org.junit.Assert.assertEquals(2, result.size());
        org.junit.Assert.assertEquals("２００７", result.get(0).originalExpression);
        org.junit.Assert.assertEquals("二十", result.get(1).originalExpression);
    }
}
