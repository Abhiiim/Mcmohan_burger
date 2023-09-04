
public class OrgHierarchy implements OrgHierarchyInterface{

	// Root Node of Generic Tree
	Node root = null;
	int size = 0;
	avlTree tree = new avlTree();

	public boolean isEmpty(){
		return size == 0; 
	} 

	public int size(){
		return size;
	}

	public int level(int id) throws IllegalIDException{
		Node temp = tree.search(tree.root, id);
		if (temp == null) {
			throw new IllegalIDException("Current Id is not present");
		} else {
			return temp.level;
		}
	} 

	public void hireOwner(int id) throws NotEmptyException{
		
		if (!isEmpty()){
			throw new NotEmptyException("Tree is Not Empty");

		} else {

			Node temp = new Node(id);
			root = temp;
			size = 1;
			pair p = new pair(id, temp);
			tree.root = tree.insert(tree, p);
		}
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException{
		Node temp = new Node(id);
		Node temp_boss = tree.search(tree.root, bossid);

		if (temp_boss == null) {
			throw new IllegalIDException("bossid is not Present");

		} else {
			temp.boss = temp_boss;
			temp.level = 1 + temp_boss.level;
			temp_boss.children.insert(temp_boss.children, id);
			pair p = new pair(id, temp);
			tree.root = tree.insert(tree, p);
			size++;

			// System.out.print("AVLTree: ");
			// tree.preorder(tree.root);
			// System.out.println("");
		}
	} 

	public void fireEmployee(int id) throws IllegalIDException{
		Node curr = tree.search(tree.root, id);

		if (id == root.currentId){
			throw new IllegalIDException("Owner will not be fired");

		} else if (curr == null) {
			throw new IllegalIDException("Current Id is not present");

		} else if (curr.children.size(curr.children) != 0) {
			throw new IllegalIDException("Current Id is not present");

		} else {

			curr.boss.children.delete(curr.boss.children, id);
			tree.delete(tree, id);
			size--;

			// System.out.print("AVLTree: ");
			// tree.preorder(tree.root);
			// System.out.println("");
		}
	}
	public void fireEmployee(int id, int manageid) throws IllegalIDException{
		Node curr = tree.search(tree.root, id);
		Node manage = tree.search(tree.root, manageid);

		if (curr == null) {
			throw new IllegalIDException("Id is not present");

		} else if (manage == null) {
			throw new IllegalIDException("Manage Id is not present");

		} else if (curr.level != manage.level) {
			throw new IllegalIDException("Id and manageId are not in same level");

		} else {

			ListNode node = curr.children.head;
			while (node != null) {
				manage.children.insert(manage.children, node.val);
				tree.search(tree.root, node.val).boss = manage;
				node = node.next;
			}
			curr.boss.children.delete(curr.boss.children, id);
			tree.delete(tree, id);
			size--;

			// System.out.print("AVLTree: ");
			// tree.preorder(tree.root);
			// System.out.println("");
		}
	} 

	public int boss(int id) throws IllegalIDException{
		Node curr = tree.search(tree.root, id);

		if (curr == null) {
			throw new IllegalIDException("Current Id is not present");

		} else if (curr == root) {
			return -1;
		} else {
			return curr.boss.currentId;
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
		
		Node temp1 = tree.search(tree.root, id1);
		Node temp2 = tree.search(tree.root, id2);

		if (temp1 == null || temp2 == null) {
			throw new IllegalIDException("One of the Id is not present");

		} else if (id1 == id2) {
			return id1;

		} else if (id1 == root.currentId || id2 == root.currentId){
			return -1;

		} else {

			avlTree new_tree = new avlTree();
			while (temp1.boss != null) {
				pair p = new pair(temp1.boss.currentId, temp1.boss);
				new_tree.root = new_tree.insert(new_tree, p);
				temp1 = temp1.boss;
			}
			while(true) {
				temp2 = temp2.boss;
				if (new_tree.search(new_tree.root, temp2.currentId) != null) {
					return temp2.currentId;
				}
			}
		}
	}

	public String toString(int id) throws IllegalIDException{

		Node temp = tree.search(tree.root, id);
		if (temp == null) {
			throw new IllegalIDException("currentttt id is not Present");

		} else {

			String res = "" + id;
			myQueue q = new myQueue();
			q.push(id);
			while (!q.isEmpty()) {

				int q_size = q.size();
				myVector arr = new myVector();

				for (int j=0; j<q_size; j++) {
					int x = q.pop();
					temp = tree.search(tree.root, x);
					ListNode node = temp.children.head;
					int n = temp.children.size(temp.children);
					
					while (node != null) {
						arr.push(node.val);
						q.push(node.val);
						node = node.next;
					}
				}

				sorting S = new sorting();
				S.sort(arr);
				for (int i=0; i<arr.size(); i++) {
					res += " " + arr.get(i);
				}
			}
			return res;
		}
	}
}
