package Phasing;

public class ValleyFinder {

    public static void main(String[] args) {
        // 示例密度数组
        double[] densityArray = {0.1, 0.3, 0.8, 1.2, 1.5, 1.9, 1.7, 1.3, 0.9, 0.4, 0.2};

        // 找到两个峰值的索引
        int peak1Index = findPeak(densityArray);
        int peak2Index = findPeak(densityArray);

        // 找到谷口的索引
        int valleyIndex = findValley(densityArray, peak1Index, peak2Index);

        // 输出结果
        System.out.println("峰值1位置：" + peak1Index);
        System.out.println("峰值2位置：" + peak2Index);
        System.out.println("谷口位置：" + valleyIndex);
    }

    // 寻找数组中的峰值
    private static int findPeak(double[] array) {
        // 在实际应用中，你可能需要使用更复杂的算法来找到峰值
        // 这里简单地返回最大值的索引
        int peakIndex = 0;
        double max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                peakIndex = i;
            }
        }

        return peakIndex;
    }

    // 在两个峰值之间寻找谷口
    private static int findValley(double[] array, int peak1Index, int peak2Index) {
        // 在两个峰值之间找到最小值的索引
        int valleyIndex = peak1Index;
        for (int i = peak1Index + 1; i < peak2Index; i++) {
            if (array[i] < array[valleyIndex]) {
                valleyIndex = i;
            }
        }

        return valleyIndex;
    }
}
