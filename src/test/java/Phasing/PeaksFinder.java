package Phasing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PeaksFinder {

    public static void main(String[] args) {
        double[] combinedArray = {0.1, 0.3, 0.8, 1.2, 1.5, 1.9, 1.7, 1.3, 0.9, 0.4, 0.2};

        // 寻找两个峰值的索引
        List<Integer> peakIndexes = findTwoPeaks(combinedArray);

        // 输出结果
        System.out.println("峰值1位置：" + peakIndexes.get(0));
        System.out.println("峰值2位置：" + peakIndexes.get(1));
    }

    // 寻找数组中的多个峰值
    private static List<Integer> findPeaks(double[] array, double threshold, int minPeakDistance) {
        List<Integer> peakIndexes = new ArrayList<>();

        for (int i = 1; i < array.length - 1; i++) {
            if (array[i] > array[i - 1] && array[i] > array[i + 1] && array[i] > threshold) {
                peakIndexes.add(i);
                // 跳过最小峰值距离
                i += minPeakDistance;
            }
        }

        return peakIndexes;
    }

    // 寻找数组中的两个峰值
    private static List<Integer> findTwoPeaks(double[] array) {
        List<Integer> peakIndexes = findPeaks(array, 0, 1);

        System.out.println(peakIndexes);
        // 如果找到的峰值数量大于等于2，保留前两个
        if (peakIndexes.size() >= 2) {
            return peakIndexes.subList(0, 2);
        } else {
            return Collections.emptyList(); // 返回空列表表示未找到足够的峰值
        }
    }
}
