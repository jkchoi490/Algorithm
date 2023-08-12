package Recursive_Tree_Graph;

import java.util.LinkedList;
import java.util.Queue;

class Node{
	int data;
	Node lt, rt;
	public Node(int val) {
		data=val;
		lt=rt=null;
	}
}



public class Solution_Recursive7 { // 이진트리 레벨탐색(BFS : Breadth-First Search)

	Node root; // root 노드 생성
	public void BFS(Node root) {
		Queue<Node> q = new LinkedList<>(); //큐 생성
		q.add(root); //큐에 root 노드 삽입
		int L = 0;  //
		
		while(!q.isEmpty()) {
			int len = q.size(); 
			System.out.print(L+" : "); //레벨 출력
			for(int i = 0; i<len; i++) { //큐 사이즈만큼 돈다
				Node now = q.poll(); //큐에서 꺼냄
				System.out.print(now.data+" "); //현재 노드 출력
				//now의 왼쪽, 오른쪽 자식노드가 있으면 큐에 삽입
				if(now.lt != null) q.add(now.lt); 
				if(now.rt != null) q.add(now.rt);
			}
			L++; //레벨 증가
			System.out.println();
		}
	}
	
/*
	 L  값
	 0 : 1
	 1 : 2 3
	 2 : 4 5 6 7
	 
	 
*/
	
	public static void main(String[] args) {
		//트리 생성
		Solution_Recursive7 tree = new Solution_Recursive7();
		tree.root = new Node(1);
		tree.root.lt = new Node(2);
		tree.root.rt = new Node(3);
		tree.root.lt.lt = new Node(4);
		tree.root.lt.rt = new Node(5);
		tree.root.rt.lt = new Node(6);
		tree.root.rt.rt = new Node(7);
		tree.BFS(tree.root);
	}

}

