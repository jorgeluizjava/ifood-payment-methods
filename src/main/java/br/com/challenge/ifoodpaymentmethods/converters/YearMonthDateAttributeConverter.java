package br.com.challenge.ifoodpaymentmethods.converters;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

public class YearMonthDateAttributeConverter implements AttributeConverter<YearMonth, Date> {
    @Override
    public Date convertToDatabaseColumn(YearMonth yearMonth) {
        if (yearMonth != null) {
            return java.sql.Date.valueOf(yearMonth.atDay(1));
        }
        return null;
    }

    @Override
    public YearMonth convertToEntityAttribute(Date dbData) {
        return YearMonth
                .from(Instant.ofEpochMilli(dbData.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
    }
}
