package Recursive_Tree_Graph;

import java.util.LinkedList;
import java.util.Queue;

class Node{
	int data;
	Node lt, rt;
	public Node(int val) {
		data = val;
		lt=rt=null;
	}
}


public class Solution_BFS3 {
	static Node root; //루트 노드 
	
	public int BFS(Node root) {
		Queue<Node> q = new LinkedList<>(); //BFS를 위해 Node를 담는 큐 생성
		q.offer(root); //root노드 큐에 담기
		int L = 0; //트리 레벨 -> 0부터 시작
		
		while(!q.isEmpty()) { //큐가 빌때까지 진행
			int len = q.size(); 
			
			for(int i = 0; i<len; i++) { 
				Node now = q.poll(); //현재 위치 큐에서 꺼냄
				if(now.lt == null && now.rt == null) return L; //현재 노드가 말단 노드이면 레벨 반환
				if(now.lt != null) q.offer(now.lt); //왼쪽 자식노드가 있으면 큐에 넣는다
				if(now.rt != null) q.offer(now.rt); //오른쪽 자식노드가 있으면 큐에 넣는다
			}
			
			L++; //for문 끝나고 레벨 증가시킴
		}
		
		return 0;		
	}
	
	
	public static void main(String[] args) {
		//트리 생성
		Solution_BFS3 tree = new Solution_BFS3();
		tree.root=new Node(1); 
        tree.root.lt=new Node(2); 
        tree.root.rt=new Node(3); 
        tree.root.lt.lt=new Node(4); 
        tree.root.lt.rt=new Node(5); 
        System.out.println(tree.BFS(root)); 

	}

}