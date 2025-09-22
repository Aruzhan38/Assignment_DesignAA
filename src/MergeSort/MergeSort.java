package MergeSort;

public class MergeSort {
    private static final int CUTOFF = 10;

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int[] buffer = new int[arr.length];
        mergeSorting(arr, buffer, 0, arr.length - 1, 0, null);
    }
    static void sortWithMetrics(int[] arr, Metrics m) {
        if (arr == null || arr.length < 2) return;
        int[] buffer = new int[arr.length];
        mergeSorting(arr, buffer, 0, arr.length - 1, 0, m);
    }
    private static void mergeSorting(int[] arr, int[] buf, int left, int right, int depth, Metrics m) {
        if (m != null) m.maxDepth = Math.max(m.maxDepth, depth);
        int size = right - left + 1;

        if (size <= CUTOFF) {
            insertionSort(arr, left, right, m);
            return;
        }

        int mid = (left + right) >>> 1;
        mergeSorting(arr, buf, left, mid, depth + 1, m);
        mergeSorting(arr, buf, mid + 1, right, depth + 1, m);

        if (arr[mid] <= arr[mid + 1]) {
            if (m != null) m.comparisons++;
            return;
        }
        if (m != null) m.comparisons++;

        merge(arr, buf, left, mid, right, m);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics m) {
        int nLeft = mid - left + 1;
        System.arraycopy(arr, left, buffer, left, nLeft);
        if (m != null) m.copies += nLeft;

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (m != null) m.comparisons++;
            if (buffer[i] <= arr[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = arr[j++];
            }
            if (m != null) m.copies++;
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
            if (m != null) m.copies++;
        }

    }

    private static void insertionSort(int[] arr, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            if (m != null) m.copies++;
            while (j >= left) {
                if (m != null) m.comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    if (m != null) m.copies++;
                    j--;
                } else break;
            }
            arr[j + 1] = key;
            if (m != null) m.copies++;
        }
    }


    static class Metrics {
        long comparisons;
        long copies;
        int maxDepth;

        @Override
        public String toString() {
            return "comparisons=" + comparisons + ", copies=" + copies + ", maxDepth=" + maxDepth;
        }
    }
}
