/**
 * @Title: ListSortUtil.java 
 * @Package com.gome.hive
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author WANGSHICHAO
 * @date 2016年5月24日 下午2:33:44 
 * @version V1.0
 * 
 */
package com.gome.wa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 
 * ListSortUtil
 * @Title: ListSortUtil.java 
 * @Package com.gome.hive
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author wangsc
 * @date 2016年5月24日 下午2:33:44 
 * @version V1.0
 * 
 */
public class ListSortUtil<T> {  
	
	
    /** 
     * @param targetList 目标排序List 
     * @param sortField 排序字段(实体类属性名) 
     * @param sortMode 排序方式（asc or  desc） 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public void sort(List<T> targetList, final String sortField, final String sortMode) {  
      
        Collections.sort(targetList, new Comparator() {  
            public int compare(Object obj1, Object obj2) {   
                int retVal = 0;  
                try {  
                    //首字母转大写  
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
                    String methodStr="get"+newStr;  
                      
                    Method method1 = ((T)obj1).getClass().getMethod(methodStr, null);  
                    Method method2 = ((T)obj2).getClass().getMethod(methodStr, null);  
                    if (sortMode != null && "desc".equals(sortMode)) {  
                        retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序  
                    } else {  
                        retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序  
                    }  
                } catch (Exception e) {  
                    throw new RuntimeException();  
                }  
                return retVal;  
            }  
        });  
    }  
    
    
    /** 
     * 对List对象按照某个成员变量进行排序 
     * @param list       List对象 
     * @param sortField  排序的属性名称 
     * @param sortMode   排序方式：ASC，DESC 任选其一 
     */  
    public static <T> void sortList(List<T> list, final String sortField, final String sortMode) {  
        if(list == null || list.size() < 2) {  
            return;  
        }  
        Collections.sort(list, new Comparator<T>() {  
            @Override  
            public int compare(T o1, T o2) {  
                try {  
                    Class clazz = o1.getClass();  
                    Field field = clazz.getDeclaredField(sortField); //获取成员变量  
                    field.setAccessible(true); //设置成可访问状态  
                    String typeName = field.getType().getName().toLowerCase(); //转换成小写  
      
                    Object v1 = field.get(o1); //获取field的值  
                    Object v2 = field.get(o2); //获取field的值  
      
                    boolean ASC_order = (sortMode == null || "ASC".equalsIgnoreCase(sortMode));  

                    // 当某个对象的此属性没有set值的时候，这样处理
                    if(v1==null){
                    	return ASC_order ? -1 :1;
                    }
                    if(v2==null){
                    	return ASC_order ? 1 :-1;
                    }
                    //判断字段数据类型，并比较大小  
                    if(typeName.endsWith("string")) {
                        String value1 = v1.toString();  
                        String value2 = v2.toString();
                        // 针对百分数
                        if(value1.endsWith("%")&&value2.endsWith("%")){
                        	Double double1 = Double.parseDouble(value1.substring(0, value1.length() - 1));
                        	Double double2 = Double.parseDouble(value2.substring(0, value2.length() - 1));  
                            return ASC_order ? double1.compareTo(double2) : double2.compareTo(double1);
                        } else {
                            return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                        }
                    }  
                    else if(typeName.endsWith("short")) {  
                        Short value1 = Short.parseShort(v1.toString());  
                        Short value2 = Short.parseShort(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("byte")) {  
                        Byte value1 = Byte.parseByte(v1.toString());  
                        Byte value2 = Byte.parseByte(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("char")) {  
                        Integer value1 = (int)(v1.toString().charAt(0));  
                        Integer value2 = (int)(v2.toString().charAt(0));  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("int") || typeName.endsWith("integer")) {  
                        Integer value1 = Integer.parseInt(v1.toString());  
                        Integer value2 = Integer.parseInt(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("long")) {  
                        Long value1 = Long.parseLong(v1.toString());  
                        Long value2 = Long.parseLong(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("float")) {  
                        Float value1 = Float.parseFloat(v1.toString());  
                        Float value2 = Float.parseFloat(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("double")) {  
                        Double value1 = Double.parseDouble(v1.toString());  
                        Double value2 = Double.parseDouble(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("boolean")) {  
                        Boolean value1 = Boolean.parseBoolean(v1.toString());  
                        Boolean value2 = Boolean.parseBoolean(v2.toString());  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    }  
                    else if(typeName.endsWith("date")) {  
                        Date value1 = (Date)(v1);  
                        Date value2 = (Date)(v2);  
                        return ASC_order ? value1.compareTo(value2) : value2.compareTo(value1);  
                    } 
                    else {  
                        //调用对象的compareTo()方法比较大小  
                        Method method = field.getType().getDeclaredMethod("compareTo", new Class[]{field.getType()});  
                        method.setAccessible(true); //设置可访问权限  
                        int result  = (Integer)method.invoke(v1, new Object[]{v2});  
                        return ASC_order ? result : result*(-1);  
                    }  
                }  
                catch (Exception e) {  
                    String err = e.getLocalizedMessage();  
                    System.out.println(err);  
                    e.printStackTrace();  
                }  
      
                return 0; //未知类型，无法比较大小  
            }  
        });
    }
      
}  
