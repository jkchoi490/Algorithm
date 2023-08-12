package Recursive_Tree_Graph;

class Node{
	int data;
	Node lt, rt;
	public Node(int val) {
		data = val;
		lt=rt=null;
	}
}
public class Solution_DFS1 { // Tree 말단노드까지의 까장 짧은 경로(DFS)
	
	Node root;
	public int DFS(int L, Node root) {
		if(root.lt == null && root.rt == null) {//말단 노드인 경우
			return L; //L값 리턴
		}else { //말단노드가 아닐경우
			//L값을 증가시키면서 왼쪽 자식과 오른쪽 자식 값 중 최솟값 리턴
			return Math.min(DFS(L+1, root.lt), DFS(L+1, root.rt));
		} 			
	}
	
	public static void main(String[] args) {
		
		//트리 생성
		Solution_DFS1 tree = new Solution_DFS1();
		tree.root=new Node(1); 
        tree.root.lt=new Node(2); 
        tree.root.rt=new Node(3); 
        tree.root.lt.lt=new Node(4); 
        tree.root.lt.rt=new Node(5); 
        System.out.println(tree.DFS(0, tree.root)); 

	}

}