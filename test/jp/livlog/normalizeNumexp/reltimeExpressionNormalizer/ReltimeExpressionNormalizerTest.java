package jp.livlog.normalizeNumexp.reltimeExpressionNormalizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jp.livlog.normalizeNumexp.reltimeExpressionNormalizer.impl.ReltimeExpressionNormalizerImpl;
import jp.livlog.normalizeNumexp.share.NTime;
import jp.livlog.normalizeNumexp.share.Symbol;

class ReltimeExpressionNormalizerTest {

    ReltimeExpressionNormalizer REN = null;

    private boolean isSameTime(final NTime a, final NTime b) {

        if (a.year == b.year && a.month == b.month && a.day == b.day && a.hour == b.hour && a.minute == b.minute && a.second == b.second) {
            return true;
        }

        return false;
    }


    @Test
    void simple1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "あの人は三時間前に生まれた";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, -3, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -3, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("三時間前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void simple2() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "それは3年5ヶ月後の出来事";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(3, 5, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(3, 5, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("3年5ヶ月後", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void plural1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "話をしよう。あれは今から36万年前………いや、1万4000年前だったか。";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);

        org.junit.Assert.assertEquals(2, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-360000, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-360000, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex2LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex2UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex2LowerRel = new NTime(-14000, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex2UpperRel = new NTime(-14000, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals("から36万年前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
        System.out.println(methodName + ":" + reltimeexps.get(1).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals("1万4000年前", reltimeexps.get(1).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex2LowerAbs, reltimeexps.get(1).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex2UpperAbs, reltimeexps.get(1).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex2LowerRel, reltimeexps.get(1).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex2UpperRel, reltimeexps.get(1).valueUpperboundRel));
    }


    @Test
    void suffixRange1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "1万年と2千年前から愛してる";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-2000, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-2000, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("2千年前から", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void range1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "1万2千年前から1億2千年後まで愛してる";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-12000, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(100002000.0, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("1万2千年前から1億2千年後まで", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void seiki1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "二世紀前に起こった悲劇";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-200, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-200, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("二世紀前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void han1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "二世紀半前に起こった悲劇";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-250, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-250, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("二世紀半前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void han2() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "あの人は三時間半前に生まれた";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, -3.5, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -3.5, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("三時間半前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void prefixCounter1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "それは昨年4月の出来事";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, 4, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, 4, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-1, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-1, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("昨年4月", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void prefixCounter2() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "来月三日にはできます";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, 3, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, 3, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(Symbol.INFINITY, 1, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-Symbol.INFINITY, 1, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("来月三日", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void abstimeExpression() {

        // final var methodName = new Object() {
        // }.getClass().getEnclosingMethod().getName();
        final var text = "それは4月の出来事";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        org.junit.Assert.assertEquals(0, reltimeexps.size());
    }


    @Test
    void about1() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "およそ1000年前";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(-1005, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-995, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("およそ1000年前", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }


    @Test
    void about2() {

        final var methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        final var text = "約3ヶ月後";
        final List <ReltimeExpression> reltimeexps = new ArrayList <>();
        final var language = "ja";
        this.REN = new ReltimeExpressionNormalizerImpl(language);
        this.REN.process(text, reltimeexps);
        System.out.println(methodName + ":" + reltimeexps.get(0).valueLowerboundRel.toDurationString(false));
        org.junit.Assert.assertEquals(1, reltimeexps.size());
        final var ex1LowerAbs = new NTime(Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperAbs = new NTime(-Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        final var ex1LowerRel = new NTime(Symbol.INFINITY, 2, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY, Symbol.INFINITY);
        final var ex1UpperRel = new NTime(-Symbol.INFINITY, 4, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY, -Symbol.INFINITY);
        org.junit.Assert.assertEquals("約3ヶ月後", reltimeexps.get(0).originalExpression);
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerAbs, reltimeexps.get(0).valueLowerboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperAbs, reltimeexps.get(0).valueUpperboundAbs));
        org.junit.Assert.assertTrue(this.isSameTime(ex1LowerRel, reltimeexps.get(0).valueLowerboundRel));
        org.junit.Assert.assertTrue(this.isSameTime(ex1UpperRel, reltimeexps.get(0).valueUpperboundRel));
    }
}
