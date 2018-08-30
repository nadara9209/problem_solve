import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		String str = in.readLine();
		
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		
		Tree tree = new Tree();
		for(int i = 0; i < N; ++i) {
			String data1 = scan.next();
			String data2 = scan.next();
			String data3 = scan.next();
			tree.add(data1.charAt(0), data2.charAt(0), data3.charAt(0));
		}
		
		tree.preorder(tree.root);
		System.out.println();
		tree.inorder(tree.root);
		System.out.println();
		tree.postorder(tree.root);
		System.out.println();
		
		scan.close();
	}
}

class Node {
	char data;
	Node left, right;
	
	public Node(char data) {
		this.data = data;
	}
}

class Tree {
	Node root; // root
	
	public Tree() {
		this.root = null;
	}
	
	public void add(char data, char leftData, char rightData) {
		if(this.root == null) { // 초기접근
			if(data != '.') {
				this.root = new Node(data);
			}
			if(leftData != '.') {
				this.root.left = new Node(leftData);
			}
			if(rightData != '.') {
				this.root.right = new Node(rightData);
			}
		}
		else { // after rooted
			search(this.root, data, leftData, rightData);
		}
	}
	
	private void search(Node root, char data, char leftData, char rightData) {
		// reached root is null(삽입위치를 못찾은 경우)
		if(root == null) {
			return;
		}
		else if(root.data == data) {
			if(leftData != '.') {
				root.left = new Node(leftData);
			}
			if(rightData != '.') {
				root.right = new Node(rightData);
			}
		}
		else {
			search(root.left, data, leftData, rightData); // left
			search(root.right, data, leftData, rightData); // right
		}
	}
	
	// 전위순회 중앙 좌 우
	public void preorder(Node root) {
		System.out.print(root.data);
		if(root.left != null) {
			preorder(root.left);
		}
		if(root.right != null) {
			preorder(root.right);
		}
	}
	// 중위순회 좌 중앙 우
	public void inorder(Node root) {
		if(root.left != null) {
			inorder(root.left);
		}
		System.out.print(root.data);
		if(root.right != null) {
			inorder(root.right);
		}
	}
	// 후위순회 좌 우 중앙
	public void postorder(Node root) {
		if(root.left != null) {
			postorder(root.left);
		}
		if(root.right != null) {
			postorder(root.right);
		}
		System.out.print(root.data);
	}
}