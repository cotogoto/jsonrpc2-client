package jp.livlog.normalizeNumexp.normalizerUtility.impl;

import jp.livlog.normalizeNumexp.normalizerUtility.LimitedExpressionTemplate;
import jp.livlog.normalizeNumexp.normalizerUtility.NormalizerUtility;

public class LimitedExpressionTemplateImpl extends LimitedExpressionTemplate {


    private final NormalizerUtility normalizerUtility = new NormalizerUtilityImpl();

    @Override
    public void setTotalNumberOfPlaceHolder() {

        // patternが含むPLACE_HOLDERの数（ *月*日 -> 2個）
        this.totalNumberOfPlaceHolder = 0;

        for (final char c1 : this.pattern.toCharArray()) {
            if (this.normalizerUtility.isPlaceHolder(c1)) {
                this.totalNumberOfPlaceHolder++;
            }
        }
    }


    @Override
    public void setLengthOfStringsAfterFinalPlaceHolder() {

        // pattern中の最後のPLACE_HOLDERの後に続く文字列の長さ（*月*日 -> 1） positionの同定に必要
        this.lengthOfStringsAfterFinalPlaceHolder = 0;

        final var a = this.pattern.lastIndexOf(String.valueOf(NormalizerUtility.PLACE_HOLDER));
        final var str = this.pattern.substring(a);
        this.lengthOfStringsAfterFinalPlaceHolder = str.length();
    }
}