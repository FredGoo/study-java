package gyqw.shardingsphere.datamask.asterisk;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.encrypt.strategy.spi.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public final class XHEncryptor implements Encryptor {
    private final Logger logger = LoggerFactory.getLogger(XHEncryptor.class);

    private Properties properties = new Properties();
    private static final String XINGHAO = "*";
    // 手机号
    private static final String TEL_REGEX = "(\\d{3})\\d*(\\d{4})";
    private static final String TEL_REGEX_MASK = "$1****$2";
    // 身份证号
    private static final String ID_15_REGEX = "(\\d{6})\\d*(\\w{4})";
    private static final String ID_15_REGEX_MASK = "$1*****$2";
    private static final String ID_18_REGEX = "(\\d{6})\\d*(\\w{4})";
    private static final String ID_18_REGEX_MASK = "$1********$2";
    // 银行卡
    private static final String BANK_16_REGEX = "(\\d{6})\\d*(\\d{4})";
    private static final String BANK_16_REGEX_MASK = "$1******$2";
    private static final String BANK_17_REGEX = "(\\d{6})\\d*(\\d{4})";
    private static final String BANK_17_REGEX_MASK = "$1*******$2";
    private static final String BANK_19_REGEX = "(\\d{6})\\d*(\\d{4})";
    private static final String BANK_19_REGEX_MASK = "$1*********$2";

    @Override
    public void init() {
        logger.info("XH Encryptor init");
    }

    @Override
    public String encrypt(Object o) {
        return o == null ? "" : o.toString();
    }

    @Override
    public Object decrypt(String s) {
        if (StringUtils.isNotEmpty(s)) {
            int sLen = s.length();
            switch (sLen) {
                // 手机号
                case 11:
                    return s.replaceAll(TEL_REGEX, TEL_REGEX_MASK);
                // 身份证
                case 15:
                    return s.replaceAll(ID_15_REGEX, ID_15_REGEX_MASK);
                case 18:
                    return s.replaceAll(ID_18_REGEX, ID_18_REGEX_MASK);
                // 银行卡
                case 16:
                    return s.replaceAll(BANK_16_REGEX, BANK_16_REGEX_MASK);
                case 17:
                    return s.replaceAll(BANK_17_REGEX, BANK_17_REGEX_MASK);
                case 19:
                    return s.replaceAll(BANK_19_REGEX, BANK_19_REGEX_MASK);
                // 姓名
                default:
                    if (sLen == 2) {
                        return s.substring(0, 1) + XINGHAO;
                    } else if (sLen > 2) {
                        return s.substring(0, 1) + String.join("", Collections.nCopies(sLen - 2, XINGHAO)) + s.substring(sLen - 1);
                    } else {
                        return s;
                    }
            }
        }

        return "";
    }

    @Override
    public String getType() {
        return "XH";
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
