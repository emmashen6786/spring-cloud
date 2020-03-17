package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final String PROVINCE_KEY = "province";
    public static final String CITY_KEY = "city";
    public static final String DISTRICT_KEY = "district";
    public static final String DETAILED_ADDRESS_KEY = "detailedAddress";
    public static final String NUMBER_REGEXP = "\\d+\\.?\\d*";

    public StringUtil() {
    }

    public static String formatStr(String data, int decimalNum) {
        return (new BigDecimal(data)).setScale(decimalNum, RoundingMode.HALF_EVEN).toString();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(str).matches();
    }

    public static BigDecimal percentToDecimal(String data) {
        if (!data.endsWith("%")) {
            throw new RuntimeException("the param is error type");
        } else {
            BigDecimal resutl = null;
            String s = data.substring(0, data.length() - 1);
            resutl = (new BigDecimal(s)).divide(new BigDecimal(100));
            return resutl;
        }
    }

    public static String decimalToPercent(Object data, int scale) {
        if (data == null) {
            return null;
        } else {
            BigDecimal bigDecimal = new BigDecimal(data.toString());
            BigDecimal result = bigDecimal.multiply(new BigDecimal("100"));
            return result.setScale(scale, RoundingMode.HALF_UP).toString() + "%";
        }
    }

    public static String getRandomString(int length) {
        if (length < 8) {
            throw new RuntimeException("长度不能小于8");
        } else {
            List<Character> list = new ArrayList();
            new StringBuilder();
            String base1 = "abcdefghijklmnopqrstuvwxyz";
            String base2 = "0123456789";
            String base3 = "!@#$%&";

            for(int i = 0; i < length; ++i) {
                if (i % 4 == 0) {
                    list.add(base1.charAt((new Random()).nextInt(base1.length())));
                } else if (i % 4 == 1) {
                    list.add(base2.charAt((new Random()).nextInt(base2.length())));
                } else if (i % 4 == 2) {
                    list.add(base1.toUpperCase().charAt((new Random()).nextInt(base1.length())));
                } else {
                    list.add(base3.charAt((new Random()).nextInt(base3.length())));
                }
            }

            Collections.shuffle(list);
            char[] arr = new char[length];

            for(int i = 0; i < length; ++i) {
                arr[i] = (Character)list.get(i);
            }

            return new String(arr);
        }
    }

    public static boolean isNum(Object object) {
        String s = String.valueOf(object);

        try {
            new BigDecimal(s);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static boolean lessThenZero(Object object) {
        String s = String.valueOf(object);
        BigDecimal bigDecimal = null;

        try {
            bigDecimal = new BigDecimal(s);
        } catch (Exception var4) {
            return false;
        }

        return bigDecimal.compareTo(BigDecimal.ZERO) == -1;
    }

    public static String getSubUUID() {
        String uuid = UUID.randomUUID().toString();
        String uuidSubstring = uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
        return uuidSubstring;
    }

    public static void validPicURL(String pic) {
        URL picURL = null;

        try {
            picURL = new URL(pic);
        } catch (Exception var7) {
            throw new RuntimeException("非正常url地址:" + pic);
        }

        try {
            InputStream in = picURL.openStream();
            Object var3 = null;
            if (in != null) {
                if (var3 != null) {
                    try {
                        in.close();
                    } catch (Throwable var5) {
                        ((Throwable)var3).addSuppressed(var5);
                    }
                } else {
                    in.close();
                }
            }

        } catch (Exception var6) {
            throw new RuntimeException("非正常url地址:" + pic);
        }
    }

    public static String parseObject(Object object) {
        return object == null ? null : object.toString();
    }

    public static String extractNumber(String str) {
        if (str == null) {
            return null;
        } else {
            Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
            Matcher matcher = pattern.matcher(str);
            return matcher.find() ? matcher.group() : null;
        }
    }

    public static String findAddressResolution(String address, String key) throws Exception {
        try {
            String result = null;
            if (StringUtils.isNotBlank(address)) {
                String regex = "(?<province>[^省]+省|.+自治区|[^市]+市|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<district>[^县]+县|.+区|.+镇|.+局)?(?<detailedAddress>.*)";
                Matcher m = Pattern.compile(regex).matcher(address);
                String province = null;
                String city = null;
                String district = null;
                String detailedAddress = null;
                List<Map<String, String>> table = new ArrayList();
                LinkedHashMap row = null;

                while(m.find()) {
                    row = new LinkedHashMap();
                    province = m.group("province");
                    row.put("province", province == null ? "" : province.trim());
                    city = m.group("city");
                    row.put("city", city == null ? "" : city.trim());
                    district = m.group("district");
                    row.put("district", district == null ? "" : district.trim());
                    detailedAddress = m.group("detailedAddress");
                    row.put("detailedAddress", detailedAddress == null ? "" : detailedAddress.trim());
                    table.add(row);
                }

                result = (String)((Map)table.get(0)).get(key);
            }

            return result;
        } catch (Exception var11) {
            throw new Exception("商户营业地址格式错误！");
        }
    }

    public static int getRadomNum(int start, int end) {
        int result;
        for(result = 0; result < start; result = (new Random()).nextInt(end)) {
            if (start == end) {
                result = start;
                break;
            }
        }

        return result;
    }
}

