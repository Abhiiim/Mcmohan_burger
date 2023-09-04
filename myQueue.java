public class myQueue {

	int size;
	int[] arr;
	int t, f;

	myQueue() {
		size = 1;
		t = -1;
		f = -1;
		arr = new int[1];
	}

	void push(int a) {
		if (t == size-1){
			size = 2*size;
			int[] tmp = new int[size];
			for (int i=f+1; i<=t; i++){
				tmp[i] = arr[i];
			}
			arr = tmp;
		}
		arr[++t] = a;
	}

	int front() {
		return arr[f+1];
	}

	int pop() {
		return arr[++f];
	}

	boolean isEmpty(){
		return (f == t);
	}

	int size() {
		return t-f;
	}
}