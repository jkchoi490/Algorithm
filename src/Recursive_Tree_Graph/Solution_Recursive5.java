package Recursive_Tree_Graph;

class Node{
	int data;
	Node lt, rt;
	public Node(int val) {
		data = val;
		lt=rt=null;
	}
}

public class Solution_Recursive5 {
	Node root;
	//부모가 기준, 부모가 어디있냐에 따라 전위, 중위, 후위 결정됨
	// 전위순회 : 부-> 왼-> 오 
	// 중위순회 : 왼 -> 부 -> 오
	// 후위순회: 왼 -> 오 -> 부            병합정렬 등에서 쓰임
	/*	
	 * 전위순회 : 부-> 왼-> 오 
	 * 		System.out.print(root.data+" ");
	 * 		DFS(root.lt);
			DFS(root.rt);
			
	 * 중위순회 : 왼 -> 부 -> 오
	 * 		DFS(root.lt);
	 * 		System.out.print(root.data+" ");
			DFS(root.rt);
	 * 
	 * 후위순회: 왼 -> 오 -> 부    
	 *  	DFS(root.lt);
			DFS(root.rt);
			System.out.print(root.data+" ");
	 * 
	 * 
	 */
	public static void DFS(Node root) {
		if(root == null) return;
		else {
			DFS(root.lt);
			System.out.print(root.data+" ");
			DFS(root.rt);
		}
		
	}
	public static void main(String[] args) {
		Solution_Recursive5 tree = new Solution_Recursive5();
		tree.root = new Node(1);
		tree.root.lt = new Node(2);
		tree.root.rt = new Node(3);
		tree.root.lt.lt = new Node(4);
		tree.root.lt.rt = new Node(5);
		tree.root.rt.lt = new Node(6);
		tree.root.rt.rt = new Node(7);
		DFS(tree.root);

	}
	
}
