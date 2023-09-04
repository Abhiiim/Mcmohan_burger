
// Defining Node for Generic Tree

public class Node {
	
  	int currentId;
  	int level;
  	Node boss;
  	linkedList children;

  	Node(int x) {
  		currentId = x;
  		level = 1;
  		boss = null;
  		children = new linkedList();
  	}
}