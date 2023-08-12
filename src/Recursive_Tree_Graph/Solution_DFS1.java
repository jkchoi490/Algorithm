package Recursive_Tree_Graph;

class Node1{
	int data;
	Node1 lt, rt;
	public Node1(int val) {
		data = val;
		lt=rt=null;
	}
}
public class Solution_DFS1 { // Tree 말단노드까지의 까장 짧은 경로(DFS)
	
	Node1 root;
	public int DFS(int L, Node1 root) {
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
		tree.root=new Node1(1); 
        tree.root.lt=new Node1(2); 
        tree.root.rt=new Node1(3); 
        tree.root.lt.lt=new Node1(4); 
        tree.root.lt.rt=new Node1(5); 
        System.out.println(tree.DFS(0, tree.root)); 

	}

}