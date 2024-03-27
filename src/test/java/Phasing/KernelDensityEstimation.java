package Phasing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KernelDensityEstimation {

    public static void main(String[] args) {
//        double[] data = {1.2, 1.5, 1.8, 2.1, 2.2, 2.5, 3.0, 3.2, 3.5, 3.8};
        double[] data = {234,253,81,200,254	,240,240,162,240,219,232,169,223,239,227,237,220,224,240,242,91,224,80,211,235,208,220,247,203,225,241,217,239,235,250,241,246,183,185,193};
        double gap =calculateRange(data) + 1;
        double[] xValues = generateXValues(data,gap);
        double[] kdeResult = calculateKernelDensityEstimation(data, xValues, 1);

        System.out.println(xValues[findValleys(kdeResult).get(0)]);
        System.out.println(xValues[findPeaks(kdeResult).get(0)]);
        // 输出结果
        System.out.println("原始数据：" + Arrays.toString(data));
        System.out.println("X轴坐标：" + Arrays.toString(xValues));
        System.out.println("核密度估计结果：" + Arrays.toString(kdeResult));
    }

    public static double merge(double[] data, double bandwidth){
        int gap = (int) (calculateRange(data) + 1);
        double[] xValues = generateXValues(data, gap);
//        System.out.println(Arrays.toString(Arrays.stream(xValues).toArray()));
        double[] kdeResult = calculateKernelDensityEstimation(data, xValues, bandwidth);
//        System.out.println(Arrays.toString(Arrays.stream(kdeResult).toArray()));
        return xValues[findValleys(kdeResult).get(0)];

    }


    private static double calculateRange(double[] array) {
        // 使用Java 8的DoubleStream提供的max和min方法
        double max = Arrays.stream(array).max().getAsDouble();
        double min = Arrays.stream(array).min().getAsDouble();

        // 计算极差
        return max - min;
    }

    // 生成X轴坐标
    public static double[] generateXValues(double[] data, double numPoints) {
        // 在原始数据的最小值和最大值范围内生成一系列均匀分布的点
        double minValue = Arrays.stream(data).min().getAsDouble();
        double maxValue = Arrays.stream(data).max().getAsDouble();


        double[] xValues = new double[(int) numPoints];
        double stepSize = (maxValue - minValue) / (numPoints - 1);

        for (int i = 0; i < numPoints; i++) {
            xValues[i] = minValue + i * stepSize;
        }

        return xValues;
    }

    // 计算核密度估计
    public static double[] calculateKernelDensityEstimation(double[] data, double[] xValues, double bandwidth) {
        double[] kdeResult = new double[xValues.length];

        for (int i = 0; i < xValues.length; i++) {
            double x = xValues[i];
            double sum = 0;

            // 使用高斯核函数计算每个数据点对于x的贡献
            for (double dataPoint : data) {
                double kernelValue = gaussianKernel((x - dataPoint) / bandwidth);
                sum += kernelValue;
            }

            // 核密度估计值
            kdeResult[i] = sum / (data.length * bandwidth);
        }

        return kdeResult;
    }
    // 高斯核函数
    public static double gaussianKernel(double u) {
        return Math.exp(-0.5 * u * u) / Math.sqrt(2 * Math.PI);
    }

    public static List<Integer> findPeaks(double[] array) {
        List<Integer> peakIndexes = new ArrayList<>();

        for (int i = 1; i < array.length - 1; i++) {
            if (array[i] > array[i - 1] && array[i] > array[i + 1]) {
                peakIndexes.add(i);
                break;
            }
        }

        return peakIndexes;
    }

    // 寻找谷底
    public static List<Integer> findValleys(double[] array) {
        List<Integer> valleyIndexes = new ArrayList<>();

        for (int i = 1; i < array.length - 1; i++) {
            if (array[i] < array[i - 1] && array[i] < array[i + 1]) {
                valleyIndexes.add(i);
                break;
            }
        }

        if (valleyIndexes.isEmpty()){
            valleyIndexes.add(array.length - 1);
        }

        return valleyIndexes;
    }
}