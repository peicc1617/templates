package cn.edu.xjtu.cad.templates.dea;

import lpsolve.LpSolveException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class Dea {

    public Dea() {
//        System.setProperty("java.library.path", System.getProperty("java.library.path")
//                + ";"+Dea.class.getResource("/").getPath());
//        Field fieldSysPath = null;
//        try {
//            fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
//            fieldSysPath.setAccessible(true);
//            fieldSysPath.set(null, null);
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        System.loadLibrary("lpsolve55");


    }

    public Map<Long, Double> depotsEfficiency(List<Long> names,Map<Long,double[]> inputsMap,Map<Long,double[]> outputsMap)  {
        Map<String, DeaRecord> records = new LinkedHashMap<>();
        for (Long name : names) {
            records.put(name+"", new DeaRecord(inputsMap.get(name), outputsMap.get(name)));
        }
        DataEnvelopmentAnalysis dea = new DataEnvelopmentAnalysis();
        Map<Long,Double> result = null;
        try {
            result = dea.estimateEfficiency(records)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(e->Long.parseLong(e.getKey()),e->e.getValue()));
        } catch (LpSolveException e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }
}
