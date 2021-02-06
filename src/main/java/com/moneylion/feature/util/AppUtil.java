package com.moneylion.feature.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
/**
 * This is a utility class uses JSOUP to sanitize client requests.
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

public class AppUtil {

    public static String sanitize(String input){

        return Jsoup.clean(
                StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeEcmaScript (StringUtils.replace(input, "'", "''")))
                , Whitelist.basic());
    }
}
