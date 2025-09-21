public class MergeSort {

    private static final int CUTOFF = 10;

    public static void sort(int[] arr) {
        int[] buffer = new int[arr.length];
        mergeSorting(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSorting(int[] arr, int[] buf, int left, int right) {
        if (right - left + 1 <= CUTOFF) {
            insertionSort(arr, left, right);
            return;
        }

        if (left < right) {
            int mid = (left + right) >>> 1;
            mergeSorting(arr, buf, left, mid);
            mergeSorting(arr, buf, mid + 1, right);
            merge(arr, buf, left, mid, right);
        }
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }

        while (i <= mid) buffer[k++] = arr[i++];

        while (j <= right) buffer[k++] = arr[j++];

        if (right + 1 - left >= 0)
            System.arraycopy(buffer, left, arr, left, right + 1 - left);
    }
    
    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        int[] a = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        MergeSort.sort(a);
        for (int x : a) System.out.print(x + " ");
    }
}
