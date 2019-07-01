package com.arabbit.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;

import com.arabbit.entity.GetSeatListEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommonUtils {
    /**
     * 获取unix时间戳
     *
     * @return
     */
    public static long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String transferLongToDate(Long millSec) {
        return serverToClientTime(millSec + "");
    }

    public static String getStrUnixTime() {
        String timestamp = getUnixTime() + "";
        if (timestamp.length() > 10)
            timestamp = timestamp.substring(0, 10);
        return timestamp;
    }


    //将一个map 转换成 字符串    并按key值 排序
    public static String map2Str(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            map = sortMapByKey(map);// KEY排序
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            String s = sb.toString();
            if (s.endsWith("&")) {
                s = s.substring(0, s.length() - 1);
            }
            return s;
        } else {
            return null;
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                });

        sortMap.putAll(map);
        return sortMap;
    }

    private static String serverToClientTime(String times) {
        if (times == null)
            return "";
        Calendar serverNow = Calendar.getInstance();
        try {
            serverNow.setTime(new Date(Long.parseLong(times + "000")));
        } catch (NumberFormatException e) {
            return times;
        }
        int year = serverNow.get(Calendar.YEAR);
        int mouth = serverNow.get(Calendar.MONTH);
        int day = serverNow.get(Calendar.DAY_OF_MONTH);
        int serverHour = serverNow.get(Calendar.HOUR_OF_DAY);
        int serverMinute = serverNow.get(Calendar.MINUTE);
        int second = serverNow.get(Calendar.SECOND);
        return year + "-" + mouth + "-" + day + " " + serverHour + ":" + serverMinute + ":" + second;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (uri == null)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static String nullToEmpty(String str) {
        if (str == null)
            str = "";
        return str;
    }

    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }


    //高德转百度
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }

    //百度转高德
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    public static boolean isNull(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof String) {
            if (((String) obj).equals("")) {
                return true;
            }
        } else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回一个不为NULL的字符串
     *
     * @param object
     * @return
     * @author Tan
     */
    public static String formatNull(Object object) {
        if (isEmpty(object)) {
            return "";
        } else {
            return object.toString();
        }
    }

    /**
     * 返回一个不为NULL的整数
     *
     * @param object
     * @return
     * @author Tan
     */
    public static int formatInt(Object object) {
        if (isEmpty(object)) {
            return 0;
        } else {
            return Integer.parseInt(object.toString());
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @return boolean
     * @author Tan
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof String) {
            if ("NULL".equals(obj.toString().trim().toUpperCase())
                    || "".equals(obj.toString().trim())) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            try {
                return isEmpty(obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    /*
    * 转为二维数组
    * */
    public static String[][] changeList(List list, Integer row) {
        Integer Row = row == null || row < 1 ? 20 : row;
//            System.out.println(ye);
        int listLength = list.size();
        int yuShu = listLength % Row;
        int ye = (yuShu == 0) ? (int) Math.ceil(listLength / Row) : ((int) Math.ceil(listLength / Row) + 1);
        String[][] arrList = new String[ye][];
        for (int i = 0; i < ye; i++) {
            if (ye == 1) {
                arrList[i] = (String[]) list.subList(0, listLength).toArray(new String[listLength]);
            } else {
                if (i == ye - 1) {
                    arrList[i] = (String[]) list.subList(i * Row, listLength).toArray(new String[listLength - i * Row]);
                } else {
                    arrList[i] = (String[]) list.subList(i * Row, (i + 1) * Row).toArray(new String[Row]);
                }
            }
        }
        return arrList;
    }

    /*
    * 转为二维数组(指定对象 )
    * */
    public static GetSeatListEntity.ListsBean[][] changeListForEntity(List<GetSeatListEntity.ListsBean> oldlist, Integer row) {
        boolean isHave = false;
        if (!CommonUtils.isNull(oldlist)) {
            isHave = true;
        }

        List<GetSeatListEntity.ListsBean> mList = new ArrayList<>();
        int size = row * row;
        for (int i = 1; i <= size; i++) {
            GetSeatListEntity.ListsBean bean = new GetSeatListEntity.ListsBean();
            if (isHave) {
                for (GetSeatListEntity.ListsBean oleBean : oldlist) {
                    int num = oleBean.getNo();
                    if (num == i) {
                        bean = oleBean;
                    }
                }
            }
            mList.add(bean);
        }
        Integer Row = row == null || row < 1 ? 20 : row;
//            System.out.println(ye);
        int listLength = mList.size();
        int yuShu = listLength % Row;
        int ye = (yuShu == 0) ? (int) Math.ceil(listLength / Row) : ((int) Math.ceil(listLength / Row) + 1);
        GetSeatListEntity.ListsBean[][] arrList = new GetSeatListEntity.ListsBean[ye][];
        for (int i = 0; i < ye; i++) {
            if (ye == 1) {
                arrList[i] = (GetSeatListEntity.ListsBean[]) mList.subList(0, listLength).toArray(new GetSeatListEntity.ListsBean[listLength]);
            } else {
                if (i == ye - 1) {
                    arrList[i] = (GetSeatListEntity.ListsBean[]) mList.subList(i * Row, listLength).toArray(new GetSeatListEntity.ListsBean[listLength - i * Row]);
                } else {
                    arrList[i] = (GetSeatListEntity.ListsBean[]) mList.subList(i * Row, (i + 1) * Row).toArray(new GetSeatListEntity.ListsBean[Row]);
                }
            }
        }
        return arrList;
    }


}
