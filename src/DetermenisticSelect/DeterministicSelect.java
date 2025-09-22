package DetermenisticSelect;

import java.util.Arrays;

public final class DeterministicSelect {
    public static int select(int[] a, int k) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        int l = 0, r = a.length - 1;
        while (true) {
            if (l == r) return a[l];
            int pivot = medianOfMedians(a, l, r);
            int[] band = partition3(a, l, r, pivot);
            int lt = band[0], gt = band[1];
            if (k < lt) {
                r = lt - 1;
            } else if (k > gt) {
                l = gt + 1;
            } else {
                return a[k];
            }
        }
    }

    private static int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) {
            insertionSort(a, l, r);
            return a[l + n / 2];
        }
        int write = l;
        for (int i = l; i <= r; i += 5) {
            int subL = i;
            int subR = Math.min(i + 4, r);
            insertionSort(a, subL, subR);
            int medianIdx = subL + (subR - subL) / 2;
            swap(a, write++, medianIdx);
        }
        return selectRangeValue(a, l, write - 1, l + (write - l - 1) / 2);
    }

    private static int selectRangeValue(int[] a, int l, int r, int kIndex) {
        while (true) {
            if (l == r) return a[l];
            int pivot = medianOfMedians(a, l, r);
            int[] band = partition3(a, l, r, pivot);
            int lt = band[0], gt = band[1];
            if (kIndex < lt) {
                r = lt - 1;
            } else if (kIndex > gt) {
                l = gt + 1;
            } else {
                return a[kIndex];
            }
        }
    }

    private static int[] partition3(int[] a, int l, int r, int pivot) {
        int i = l, lt = l, gt = r;
        while (i <= gt) {
            if (a[i] < pivot) {
                swap(a, lt++, i++);
            } else if (a[i] > pivot) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {9, 1, 7, 7, 3, 2, 6, 5, 4, 8, 0, 7};
        for (int k = 0; k < a.length; k++) {
            int kth = select(Arrays.copyOf(a, a.length), k);
            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);
            assert kth == copy[k] : "Mismatch for k=" + k;
        }
        System.out.println("OK MoM5");
    }
}
