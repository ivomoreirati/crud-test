package br.com.crud.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    public static final String DEFAULT_ZONEID = "America/Sao_Paulo";

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of(DEFAULT_ZONEID));
    }
}
