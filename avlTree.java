
public class avlTree {

	class TreeNode {
		int hgt;
		pair val;
		TreeNode left, right, parent;

		TreeNode (pair x) {
			val = x;
			hgt = 0;
			left = null;
			right = null;
			parent = null;
		}
	}

	TreeNode root;

	int height(TreeNode curr) {
		if (curr != null) {
			return curr.hgt;
		} else {
			return -1;
		}
	}

	TreeNode minNode(TreeNode a, TreeNode b) {
		if (a.val.first < b.val.first) return a;
		else return b;
	}

	TreeNode maxNode(TreeNode a, TreeNode b) {
		if (a.val.first > b.val.first) return a;
		else return b;
	}

	int max(int a, int b) {
		if (a > b) return a;
		else return b;
	}

	TreeNode insert(avlTree tree, pair p) {

		TreeNode temp = new TreeNode(p);      // Creating New Node

		// Insertion in AVLTree (like we do in BST)

		if (tree.root == null) {
			tree.root = temp;
			tree.root.parent = null;

		} else {

			TreeNode curr = tree.root;
			while (true) {
				if (curr.val.first == p.first){
					return tree.root;
				} else if (curr.val.first < p.first) {
					if (curr.right == null) {
						curr.right = temp;
						temp.parent = curr;
						break;
					} else {
						curr = curr.right;
					}
				} else {
					if (curr.left == null) {
						curr.left = temp;
						temp.parent = curr;
						break;
					} else {
						curr = curr.left;
					}
				}
			}

			curr = temp;				// Node where insertion happens
			TreeNode prev = null;
			boolean flag = true;

			// Checking whether tree is unbalance or not;
			// otherwise we assign the height upto root node

			while (curr.parent != null) {
				int d = height(curr.parent.left) - height(curr.parent.right);
				if (d > 1 || d < -1) {
					flag = false;
					break;
				} else {
					curr.parent.hgt = 1 + max(height(curr.parent.left), height(curr.parent.right));
					prev = curr;
					curr = curr.parent;
				}
			}

			if (flag) {
				return tree.root;  // No Need to assign height
			} else {
				TreeNode x;
				TreeNode y;
				TreeNode z;
				x = minNode(minNode(prev, curr), curr.parent);
				z = maxNode(maxNode(prev, curr), curr.parent);

				if (prev.val.first > x.val.first && prev.val.first < z.val.first) {
					y = prev;
				}
				else if (curr.val.first > x.val.first && curr.val.first < z.val.first) {
					y = curr;
				}
				else {
					y = curr.parent;
				}

				y.parent = curr.parent.parent;
				if (y.parent != null) {
					if (y.val.first > y.parent.val.first){
						y.parent.right = y;
					} else {
						y.parent.left = y;
					}
				}

				rotate(x, y, z);

				// Assigning Height
				x.hgt = 1 + max(height(x.left), height(x.right));
				z.hgt = 1 + max(height(z.left), height(z.right));
				y.hgt = 1 + max(height(y.left), height(y.right));

				TreeNode new_root = y;
				curr = y.parent;

				// Assigning new height of the nodes upto root node

				while (curr != null) {
					curr.hgt = 1 + max(height(curr.left), height(curr.right));
					new_root = curr;
					curr = curr.parent;
				}
				tree.root = new_root;
			}
		}
		return tree.root;
	}

	void rotate(TreeNode x, TreeNode y, TreeNode z) {
		TreeNode[] arr = new TreeNode[7];
		arr[0] = x.left;
		arr[1] = x;
		arr[3] = y;
		arr[5] = z;
		arr[6] = z.right;
		if (x.right == y || x.right == z) {
			arr[2] = y.left;
		} else {
			arr[2] = x.right;
		}
		if (z.left == y || z.left == x) {
			arr[4] = y.right;
		} else {
			arr[4] = z.left;
		}

		// Assigning children
		arr[3].left = arr[1];
		arr[3].right = arr[5];
		arr[1].left = arr[0];
		arr[1].right = arr[2];
		arr[5].left = arr[4];
		arr[5].right = arr[6];

		// Assigning parent
		if (arr[0] != null) arr[0].parent = arr[1];
		if (arr[2] != null) arr[2].parent = arr[1];
		arr[1].parent = arr[3];
		arr[5].parent = arr[3];
		if (arr[4] != null) arr[4].parent = arr[5];
		if (arr[6] != null) arr[6].parent = arr[5];

		// Assigning Height
		arr[1].hgt = 1 + max(height(arr[0]), height(arr[2]));
		arr[5].hgt = 1 + max(height(arr[4]), height(arr[6]));
		arr[3].hgt = 1 + max(height(arr[1]), height(arr[5]));

	}


	void delete(avlTree tree, int x) {

		// Finding the node we need to delete

		TreeNode curr = tree.root;
		while (curr != null) {
			if (curr.val.first == x) {
				break;
			} else if (curr.val.first < x) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
		}

		if (curr.left == null) {
			if (curr.parent == null) {
				curr = curr.right;
				curr.parent = null;
				tree.root = curr;
			} else if (curr.val.first < curr.parent.val.first) {
				curr.parent.left = curr.right;
				if (curr.right != null) curr.right.parent = curr.parent;
				curr = curr.parent;
			} else {
				curr.parent.right = curr.right;
				if (curr.right != null) curr.right.parent = curr.parent;
				curr = curr.parent;
			}

			curr.hgt = 1 + max(height(curr.left), height(curr.right));
			balanceTree(tree, curr);

		} else {

			TreeNode temp = curr.left;
			while (temp.right != null) {
				temp = temp.right;
			}
			pair swap = curr.val;
			curr.val = temp.val;
			temp.val = swap;       			

			if (temp == curr.left) {
				curr.left = temp.left;
				if (temp.left != null) temp.left.parent = curr;
				temp = temp.parent;
			} else {
				temp.parent.right = temp.left;
				if (temp.left != null) temp.left.parent = temp.parent;
				temp = temp.parent;
			}

			temp.hgt = 1 + max(height(temp.left), height(temp.right));
			balanceTree(tree, temp);
		}
	}


	void balanceTree(avlTree tree, TreeNode curr) {

		int d = height(curr.left) - height(curr.right);
		TreeNode prev, prev2;
		TreeNode x = null, y = null, z = null;

		while (curr != null) {

			d = height(curr.left) - height(curr.right);
			if (d < -1 || d > 1) {

				if (d > 1) {
					prev = curr.left;
				} else {
					prev = curr.right;
				}

				if (height(prev.left) >= height(prev.right)) prev2 = prev.left;
				else prev2 = prev.right;

				x = minNode(minNode(prev, curr), prev2);
				z = maxNode(maxNode(prev, curr), prev2);

				if (prev.val.first > x.val.first && prev.val.first < z.val.first) {
					y = prev;
				}
				else if (curr.val.first > x.val.first && curr.val.first < z.val.first) {
					y = curr;
				}
				else {
					y = prev2;
				}

				y.parent = curr.parent;
				if (y.parent != null) {
					if (y.val.first > y.parent.val.first){
						y.parent.right = y;
					} else {
						y.parent.left = y;
					}
				}

				rotate(x, y, z);
				curr = y;

				if (x == tree.root || y == tree.root || z == tree.root) {
					tree.root = y;
				}

			} else {
				curr.hgt = 1 + max(height(curr.left), height(curr.right));
				curr = curr.parent;
			}
		}
	}


	Node search(TreeNode node, int a) {
		if (node == null) {
			return null;
		}
		if (a == node.val.first) {
			return node.val.second;
		} else if (a < node.val.first) {
			return search(node.left, a);
		} else {
			return search(node.right, a);
		}
	}


	void preorder(TreeNode node) {
		if (node != null) {
			System.out.print(node.val.first + " ");
			preorder(node.left);
			preorder(node.right);
		}
	}
}