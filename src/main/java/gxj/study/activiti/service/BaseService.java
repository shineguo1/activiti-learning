//package gxj.study.activiti.service;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.util.NumberUtil;
//import com.github.pagehelper.Page;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.ibatis.binding.MapperProxy;
//import tk.mybatis.mapper.entity.Example;
//import tk.mybatis.mapper.weekend.Fn;
//import tk.mybatis.mapper.weekend.WeekendSqls;
//import tk.mybatis.mapper.weekend.reflection.Reflections;
//
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.text.ParseException;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.function.BinaryOperator;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class BaseService implements Serializable {
//
//    /**
//     * 转化为分页对象
//     */
//    public <T, R, P extends PageRes<R>> P toPage(Collection<T> list, Class<R> clazz, P res) {
//        res.setDatas(convert(list, clazz));
//        res.setPageNo((long) ((Page) list).getPageNum());
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//
//    /**
//     * 转化为分页对象
//     */
//    public <T, R, P extends PageRes<R>> PageRes<R> toPage(Collection<T> list, Class<R> clazz, boolean convert, P res) {
//        if (convert) {
//            res.setDatas(convert(list, clazz));
//        }
//        res.setPageNo((long) ((Page) list).getPageNum());
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//
//    /**
//     * 转化为分页对象
//     */
//    public <T> PageRes<T> toPage(List<T> list) {
//        PageRes<T> res = new PageRes();
//        res.setDatas(list);
//        res.setPageNo((long) ((Page) list).getPageNum());
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//
//    /**
//     * 转化为分页对象
//     */
//    public <T, R> PageRes<R> toPage(Collection<T> list, Class<R> clazz) {
//        PageRes<R> res = new PageRes();
//        res.setDatas(convert(list, clazz));
//        res.setPageNo((long) ((Page) list).getPageNum());
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//
//    /**
//     * 转化为分页对象
//     */
//    public <T, R> PageRes<R> toPage(Collection<T> list, Class<R> clazz, boolean convert) {
//        PageRes<R> res = new PageRes();
//        if (convert) {
//            res.setDatas(convert(list, clazz));
//        }
//        res.setPageNo((long) ((Page) list).getPageNum());
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//
//    /**
//     * 转化 List 类型
//     */
//    public <T, R> List<R> convert(Collection<T> list, Class<R> clazz) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Lists.newArrayList();
//        }
//        return list.stream().map(o -> BeanUtil.copyProperties(o, clazz)).filter(Objects::nonNull).collect(Collectors.toList());
//    }
//
//    /**
//     * 提取列表对象中指定字段的值
//     */
//    public <T, R> List<R> list(Collection<T> list, Function<T, R> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Lists.newArrayList();
//        }
//        return list.stream().map(function).filter(Objects::nonNull).collect(Collectors.toList());
//    }
//
//    /**
//     * 提取列表对象中指定字段的值
//     */
//    public <T, R> List<R> list(Collection<T> list, Function<T, R> function, R defaultValue) {
//        List<R> res = this.list(list, function);
//        if (CollectionUtils.isEmpty(res)) {
//            res = Lists.newArrayList(defaultValue);
//        }
//        return res;
//    }
//
//    /**
//     * 提取列表对象中指定字段的值
//     */
//    public <T, R> Set<R> set(Collection<T> list, Function<T, R> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Sets.newHashSet();
//        }
//        return list.stream().map(function).filter(Objects::nonNull).collect(Collectors.toSet());
//    }
//
//    /**
//     * 提取列表对象中指定字段的值
//     */
//    public <T, R> Set<R> set(Collection<T> list, Function<T, R> function, R defaultValue) {
//        Set<R> res = this.set(list, function);
//        if (CollectionUtils.isEmpty(res)) {
//            res = Sets.newHashSet(defaultValue);
//        }
//        return res;
//    }
//
//    /**
//     * 将List转化成Map, key为指定字段
//     */
//    public <T, R> Map<R, T> map(Collection<T> list, Function<T, R> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Maps.newHashMap();
//        }
//        return list.stream().filter(Objects::nonNull).collect(Collectors.toMap(function, o -> o));
//    }
//
//    /**
//     * 将List转化成Map, key为指定字段
//     */
//    public <T, K, V> Map<K, V> map(Collection<T> list, Function<T, K> keyFunction, Function<T, V> valueFunction) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Maps.newHashMap();
//        }
//        return list.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyFunction, valueFunction));
//    }
//
//    /**
//     * 将List转化成Map, key为指定字段
//     */
//    public <T, K, V> Map<K, V> map(Collection<T> list, Function<T, K> keyFunction, Function<T, V> valueFunction, BinaryOperator<V> distinct) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Maps.newHashMap();
//        }
//        BinaryOperator<V> di;
//        return list.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyFunction, valueFunction, distinct));
//    }
//
//    /**
//     * 根据指定字段对列表进行分组
//     */
//    public <T, R> Map<R, List<T>> group(Collection<T> list, Function<T, R> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Maps.newLinkedHashMap();
//        }
//        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(function));
//    }
//
//    /**
//     * 根据指定字段对列表进行分组
//     */
//    public <T, K, V> Map<K, List<V>> group(Collection<T> list, Function<T, K> keyFunction, Function<T, V> valueFunction) {
//        if (CollectionUtils.isEmpty(list)) {
//            return Maps.newLinkedHashMap();
//        }
//        return list.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(keyFunction, Collectors.mapping(valueFunction,  Collectors.toList())));
//    }
//
//    /**
//     * 对指定字段进行汇总
//     */
//    public <T> Integer sum(Collection<T> list, Function<T, Integer> function, Integer def) {
//        if (CollectionUtils.isEmpty(list)) {
//            return def;
//        }
//        return list.stream().map(function).filter(Objects::nonNull).reduce(def, Integer::sum);
//    }
//
//    /**
//     * 对指定字段进行汇总
//     */
//    public <T> Long sum(Collection<T> list, Function<T, Long> function, Long def) {
//        if (CollectionUtils.isEmpty(list)) {
//            return def;
//        }
//        return list.stream().map(function).filter(Objects::nonNull).reduce(def, Long::sum);
//    }
//
//    /**
//     * 对指定字段进行汇总
//     */
//    public <T> BigDecimal sum(Collection<T> list, Function<T, BigDecimal> function, BigDecimal def) {
//        if (CollectionUtils.isEmpty(list)) {
//            return def;
//        }
//        return list.stream().map(function).filter(Objects::nonNull).reduce(def, BigDecimal::add);
//    }
//
//    /**
//     * 对指定字段取最大值
//     */
//    public <T> BigDecimal max(Collection<T> list, Function<T, BigDecimal> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return BigDecimal.ZERO;
//        }
//        return list.stream().map(function).filter(Objects::nonNull).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
//    }
//
//    /**
//     * 对指定字段取最大值
//     */
//    public <T> BigDecimal min(Collection<T> list, Function<T, BigDecimal> function) {
//        if (CollectionUtils.isEmpty(list)) {
//            return BigDecimal.ZERO;
//        }
//        return list.stream().map(function).filter(Objects::nonNull).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
//    }
//
//    protected <M extends MyMapper<E>, E, K> Map<K, List<E>> groupEntity(M mapper, Fn<E, K> getKey, Collection list) {
//        return group(mapper.selectByCondition(getExample(mapper, getKey, list)), getKey);
//    }
//
//    protected <M extends MyMapper<E>, E, ID> Map<ID, E> mapEntity(M mapper, Fn<E, ID> getId, Collection list) {
//        if(CollectionUtils.isEmpty(list)) {
//            return Maps.newHashMap();
//        }
//        return map(mapper.selectByCondition(getExample(mapper, getId, list)), getId);
//    }
//
//    protected <M extends MyMapper<E>, E, ID> List<E> listEntity(M mapper, Fn<E, ID> getId, Collection<ID> list) {
//        return mapper.selectByCondition(getExample(mapper, getId, list));
//    }
//
//    protected <K, V> V getMapV(Map<K, V> map, K key, V def) {
//        V value = map.get(key);
//        if (Objects.isNull(value)) {
//            return def;
//        }
//        return value;
//    }
//
//    protected <K, V, ID> ID getMapP(Map<K, V> map, K key, Fn<V, ID> getProp) {
//        V value = map.get(key);
//        if (Objects.isNull(value)) {
//            return null;
//        }
//        return getProp.apply(value);
//    }
//
//    private <M extends MyMapper<E>, E, ID> Example getExample(M mapper, Fn<E, ID> field, Collection list) {
//        WeekendSqls<E> con = WeekendSqls.custom();
//        Example.Builder builder = Example.builder(getEntityClass(mapper));
//        con.andIn(Reflections.fnToFieldName(field), list);
//        builder.andWhere(con);
//        return builder.build();
//    }
//
//    private <M extends MyMapper> Class getEntityClass(M mapper) {
//        try {
//            Field h = mapper.getClass().getSuperclass().getDeclaredField("h");
//            h.setAccessible(true);
//            MapperProxy mapperProxy = (MapperProxy) h.get(mapper);
//            Field mapperInterface = mapperProxy.getClass().getDeclaredField("mapperInterface");
//            mapperInterface.setAccessible(true);
//            Class mapperClass = (Class) mapperInterface.get(mapperProxy);
//            ParameterizedType parameterizedType = (ParameterizedType) mapperClass.getGenericInterfaces()[0];
//            return (Class) parameterizedType.getActualTypeArguments()[0];
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Date toDate(String data) {
//        try {
//            return DateUtils.parseDate(data, "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
//        } catch (ParseException e) {
//            throw new CustomException("日期解析异常");
//        }
//    }
//
//    public Integer toInteger(String data) {
//        String value = toDecimal(data).setScale(0, RoundingMode.HALF_UP).toString();
//        return Integer.valueOf(value);
//    }
//
//    public BigDecimal toDecimal(String data) {
//        if (!NumberUtil.isNumber(data)) {
//            throw new CustomException(data + "不是数字");
//        }
//        return new BigDecimal(data);
//    }
//
//    public void checkTx(int tx, String msg) {
//        if(tx <1) {
//            throw new CustomException(msg);
//        }
//    }
//
//    private static volatile String IP_ADDRESS = "";
//    private static final String LOCAL_IP = "127.0.0.1";
//    public static String getLocalIP() {
//        if(StringUtils.isNotBlank(IP_ADDRESS)) {
//            return IP_ADDRESS;
//        } else {
//            try {
//                Enumeration e = NetworkInterface.getNetworkInterfaces();
//
//                while(e.hasMoreElements()) {
//                    NetworkInterface netInterface = (NetworkInterface)e.nextElement();
//                    Enumeration addresses = netInterface.getInetAddresses();
//
//                    while(addresses.hasMoreElements()) {
//                        InetAddress ip = (InetAddress)addresses.nextElement();
//                        if(ip != null && ip instanceof Inet4Address) {
//                            String tip = ip.getHostAddress();
//                            if(!"127.0.0.1".equals(tip)) {
//                                IP_ADDRESS = tip;
//                                return IP_ADDRESS;
//                            }
//                        }
//                    }
//                }
//            } catch (SocketException var5) {
//                log.error("获取本机IP Socket异常:{}", var5);
//            } catch (Exception var6) {
//                log.error("获取本机IP异常:{}", var6);
//            }
//
//            return "127.0.0.1";
//        }
//    }
//
//
//    /**
//     * 转化为分页对象
//     */
//    public <T, R> PageRes<R> toPageNew(Collection<T> list, Class<R> clazz,PageRes res) {
//        res.setDatas(convert(list, clazz));
//        res.setTotalSize(((Page) list).getTotal());
//        return res;
//    }
//}
