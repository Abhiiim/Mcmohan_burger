
public class linkedList {

	ListNode head = null;

	void insert(linkedList currList, int x) {
		ListNode temp = new ListNode(x);
		temp.next = head;
		currList.head = temp;
	}

	void delete(linkedList currList, int x) {
		ListNode curr = currList.head;
		if (curr.val == x) {
			currList.head = curr.next;
		} else {
			while (curr.next != null) {
				if (curr.next.val == x) {
					curr.next = curr.next.next;
					break;
				}
				curr = curr.next;
			}
		}
	}

	int size(linkedList currList) {
		int x = 0;
		ListNode curr = currList.head;
		while (curr != null) {
			x++;
			curr = curr.next;
		}
		return x;
	}

	void printList(linkedList list) {
		ListNode head = list.head;
		while (head != null) {
			System.out.println(head.val);
			head = head.next;
		}
	}
}