package com.blackfield.StockManagement.util;
import com.google.common.base.Strings;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class MethodUtils {

    private MethodUtils() {

    }

    private static final DateTimeFormatter DATETIME_FORMAT_CONVERTOR_V2 = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final DateTimeFormatter DATETIME_FORMAT_CONVERTOR = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm");

    private static final DateTimeFormatter DATEFORMAT_CONVERTOR = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATETIME_FORMAT_CONVERTOR_V3 = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DATEFORMAT_CONVERTOR_SEARCH = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATETIME_SMS_FORMAT_CONVERTOR = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final int NOMBRE_CHIFFRE_OTP = 4;

    public static String convertNumberToLetter(Double value) {

        return convertNumberToLetter(Long.toString(value.longValue()));
    }

    public static String getDateTimeToString(LocalDateTime date) {

        return date == null ? "" : DATETIME_FORMAT_CONVERTOR
                .format(date);
    }
    public static LocalDate getFirstDayOfCurrentMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    public static LocalDate getLastDayOfCurrentMonth() {
        YearMonth yearMonth = YearMonth.from(LocalDate.now());
        return yearMonth.atEndOfMonth();
    }

    public static LocalDate getFirstDayOfPreviousMonth() {
        return LocalDate.now().withDayOfMonth(1).minusMonths(1);
    }

    public static LocalDate getLastDayOfPreviousMonth() {
        YearMonth yearMonth = YearMonth.from(LocalDate.now().minusMonths(1));
        return yearMonth.atEndOfMonth();
    }
    private static String convertNumberToLetter(String str) {

        RuleBasedNumberFormat rbnf;

        rbnf = new RuleBasedNumberFormat(Locale.FRANCE,
                RuleBasedNumberFormat.SPELLOUT);

        return rbnf.format(Long.parseLong(str));
    }

    public static boolean isFrench(String language) {

        return Constants.DEFAULT_LANGUAGE.equalsIgnoreCase(language);
    }

    public static String getLocalDateTimeToString(LocalDateTime date) {

        return date == null ? "" : DATETIME_FORMAT_CONVERTOR_V2
                .format(date);
    }

    public static LocalDateTime convertorDateTime00H00(String dateText) {
        LocalDate l = convertorDate(dateText);
        return l != null ? l.atStartOfDay() : null;
    }
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
    public static LocalDateTime convertorDateTime24H00(String dateText) {
        LocalDate l = convertorDate(dateText);
        return l != null ? l.atTime(LocalTime.MAX) : null;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertorDate(String dateText) {

        LocalDateTime l1;

        LocalDate l = null;
        try {

            dateText = dateText == null ? "" : dateText;

            l1 = LocalDateTime.parse(dateText, DATETIME_FORMAT_CONVERTOR);

            l = l1.toLocalDate();

        } catch (Exception ignored) {
        }

        try {

            if (l == null) {

                l = LocalDate.parse(dateText, DATEFORMAT_CONVERTOR);
            }

        } catch (Exception ignored) {

        }

        try {

            if (l == null) {

                l = LocalDate.parse(dateText, DATEFORMAT_CONVERTOR_SEARCH);
            }

        } catch (Exception ignored) {

        }

        try {

            if (l == null) {

                l = LocalDate.parse(dateText, DATETIME_FORMAT_CONVERTOR_V3);
            }

        } catch (Exception ignored) {

        }

        return l;
    }

    public static LocalDateTime convertorDateTimeSms(String dateText) {

        LocalDateTime l = null;
        try {

            dateText = dateText == null ? "" : dateText;

            l = LocalDateTime.parse(dateText, DATETIME_SMS_FORMAT_CONVERTOR);

        } catch (Exception ignored) {

        }

        return l;
    }

    public static String generateOtp() {
        return RandomStringUtils.randomNumeric(NOMBRE_CHIFFRE_OTP);
    }

    public static String getDateToString(LocalDateTime date) {
        return date == null ? "" : DATETIME_FORMAT_CONVERTOR
                .format(date);
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String format(Integer value, int numberCharMax) {
        String v = value.toString();
        StringBuilder fixe = new StringBuilder();
        if (v.length() <= numberCharMax) {
            int nbreZero = numberCharMax - v.length();
            fixe.append("0".repeat(nbreZero));
            fixe.append(v);
        } else {
            fixe = new StringBuilder(v);
        }
        return fixe.toString();
    }


    public static String generateNoProduit(String prefixe, String addPrefixe) {

        StringBuilder sb = new StringBuilder();

        if (prefixe.length() == 1) {
            sb.append('0');
        }

        sb.append(prefixe).append(addPrefixe);

        int nbre = 8 - sb.length();

        sb.append(RandomStringUtils.randomNumeric(nbre));

        return sb.toString();
    }

    public static Pageable findAllByCriteria(boolean classement, String typeClassement, Integer nombreDeResultat, Object criteria) {
        Sort sort;
        if(Strings.isNullOrEmpty(typeClassement)) {
            typeClassement = "id";
        }
        if(Boolean.FALSE.equals(classement)) {
            sort = Sort.by(typeClassement).descending();
        } else {
            sort = Sort.by(typeClassement).ascending();
        }

        try {
            Field field = criteria.getClass().getDeclaredField("typeClassement");
            field.setAccessible(true);
            field.set(criteria, typeClassement);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return PageRequest.of(0, nombreDeResultat == null || nombreDeResultat <0
                ? Integer.MAX_VALUE : nombreDeResultat, sort);

    }

    public static String getPrefixDocumentByDate() {
        LocalDate today = LocalDate.now();
        Calendar c = Calendar.getInstance();
        String p = (today.getYear() + "").substring(2);
        p += format(today.getMonthValue(), 2);
        p += format(today.getDayOfMonth(), 2);
        p += "_";
        p += format(c.get(Calendar.HOUR_OF_DAY), 2);
        p += format(c.get(Calendar.MINUTE), 2);
        p += format(c.get(Calendar.SECOND), 2);
        p += "_";
        p += RandomStringUtils.randomNumeric(6);
        return p;
    }
}
