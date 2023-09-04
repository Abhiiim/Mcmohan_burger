public class sorting {

	static void sort(myVector arr) {
		int n = arr.size();
		for (int i=1; i<n; i++) {
			int j = i-1;
			while ( j >= 0 && arr.get(j) > arr.get(j+1)) {
				int tmp = arr.get(j);
				int tmp2 = arr.get(j+1);
				arr.get(j, tmp2) ;
				arr.get(j+1, tmp);
				j--;
			}
		}
	}
}