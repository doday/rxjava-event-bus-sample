package com.doday.rxjavaeventbussample;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sessi on 19/10/16.
 */
public class DateUtils {
    public static final String D_MMMM_YYYY_LETTER_FORMAT = "d MMMM yyyy";
    private static final String DD_MM_YYYY_DIGIT_FORMAT = "dd-MM-yyyy";
    private static final String YYYY_MM_DD_DIGIT_FORMAT = "yyyy-MM-dd";

    public static final String FRENCH_DATE_FORMAT = "FRENCH_DATE_FORMAT";
    public static final String ENGLISH_DATE_FORMAT = "ENGLISH_DATE_FORMAT";


    private static final DateUtils ourInstance = new DateUtils();//TODO mettre les premièes lettres en majuscule
    private static final java.text.SimpleDateFormat simpleDateFormatLetterFormat = new java.text.SimpleDateFormat(D_MMMM_YYYY_LETTER_FORMAT,Locale.FRENCH);
    private java.text.SimpleDateFormat simpleDateFormatFrenchDigitFormat = new java.text.SimpleDateFormat(DD_MM_YYYY_DIGIT_FORMAT, Locale.FRENCH);
    private java.text.SimpleDateFormat simpleDateFormatEnglishDigitFormat = new java.text.SimpleDateFormat(YYYY_MM_DD_DIGIT_FORMAT, Locale.FRENCH);


    public static DateUtils getInstance() {
        return ourInstance;
    }
    private DateUtils() {
    }


    @NonNull
    public String getFormattedDate(String dateStringFormat, String format) throws ParseException {
        if(FRENCH_DATE_FORMAT.equals(format)) {
            return getFormattedFrenchFormat(dateStringFormat);
        }else{
            return getFormattedEnglishFormat(dateStringFormat);
        }
    }

    private String getFormattedFrenchFormat(String frenchDateStringFormat) throws ParseException {
        Date dateDateFormat = simpleDateFormatFrenchDigitFormat.parse(frenchDateStringFormat);//TODO c'est dommage de ne pas directement renvoyer un Date car c'est le type de l'objet qui est stocké....
        return simpleDateFormatFrenchDigitFormat.format(dateDateFormat);
    }


    private String getFormattedEnglishFormat(String frenchDateStringFormat) throws ParseException {
        Date dateDateFormat = simpleDateFormatEnglishDigitFormat.parse(frenchDateStringFormat);//TODO c'est dommage de ne pas directement renvoyer un Date car c'est le type de l'objet qui est stocké....
        return simpleDateFormatEnglishDigitFormat.format(dateDateFormat);
    }



    public Date getDateFromString(String date) throws ParseException {
        return simpleDateFormatEnglishDigitFormat.parse(date);
    }

    public String getDateLetterFormat(Date date) {
        return simpleDateFormatLetterFormat.format(date);
    }


}
