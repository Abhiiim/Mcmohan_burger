public class myVector {

	int size;
	int[] arr;
	int end;

	myVector() {
		end = -1;
		size = 1;
		arr = new int[1];
	}

	void push(int a) {
		if (end == size-1) {
			size = 2*size;
			int[] tmp = new int[size];
			for (int i=0; i<=end; i++) {
				tmp[i] = arr[i];
			}
			arr = tmp;
		}
		arr[++end] = a;
	}

	int get(int index) {
		return arr[index];
	}

	void get(int index, int value) {
		arr[index] = value;
	}

	int size() {
		return end+1;
	}
}