package Assign3;
public class Assignment3 {
	private static Queue<BTNode<dataNode>> q1 = new Queue<BTNode<dataNode>>();
	//private static boolean flag = true;
	
	public static Queue<BTNode<dataNode>> makeQueue(double[] a){
		// Each element of the given array a must be inserted into a BTNode, 
		// this method returns a queue of BTNodes, each node will contain a dataNode
		// the dataNode will have the value equal to the element of the array
		// count equal to the number of times that the element repeats
		// min and max must be equal to value.
		// the BTNode created must have its parent, right, and left references set to null
		
		Queue<BTNode<dataNode>> tempQ = new Queue<BTNode<dataNode>>();
		for(int i=0; i<a.length; i++){
			
			dataNode n1=new dataNode();
			n1.count=1;
			int j=i+1;
			for(;j<a.length;j++) {
				if(a[i]==a[j]) {
					n1.count++;
					i++;
				}
				else
					break;
			}
			
			n1.value=a[i];
			n1.min=a[i];
			n1.max=a[i];
			BTNode<dataNode> bnode = new BTNode<dataNode>(n1, null, null, null);
			tempQ.enqueue(bnode);
		}
		return tempQ;
	}
	
	public static Queue<BTNode<dataNode>> join(Queue<BTNode<dataNode>> myQueue){
		// For every two elements dequeued from myQueue create a new root element and
		// make the two dequeued elements be the left and right child of that root.
		// Each new root must contain the min value, obtained from the left subtree,
		// the max value, obtained from the right subtree, and the value should be
		// equal to the average of the maximum value of the left subtree and the
		// minimum value of the right subtree, count should be set equal to 0 (internal node)
		// Enqueue each new root node into another queue and return that queue.
		// In case that only one element is left in myQueue, just enqueue it 
		// in the queue that will be returned.
		//System.out.println("myQueue size is " + myQueue.size());
		if(myQueue.size()>0) {
			BTNode<dataNode> lNode, rNode;
			
			lNode = myQueue.dequeue();
			rNode = myQueue.dequeue();
			dataNode n1=new dataNode();
			BTNode<dataNode> tempRoot = new BTNode<dataNode>(n1, lNode, rNode, null);
			tempRoot.getData().count=0;
			double lTreeMin = findMin(tempRoot).getData().value;
			double rTreeMax = findMax(tempRoot).getData().value;
			double lTreeMax = findMax(tempRoot.getLeft()).getData().value;
			double rTreeMin = findMin(tempRoot.getRight()).getData().value;
			tempRoot.getData().value=(lTreeMax+rTreeMin)/2;
			tempRoot.getData().min=lTreeMin;
			tempRoot.getData().max=rTreeMax;
			//System.out.println("lTreeMin, lTreeMax, rTreeMin, rTreeMax = " + lTreeMin + " " + lTreeMax + " " + rTreeMin + " " + rTreeMax);
			tempRoot.setLeft(lNode);
			if(rNode!=null)
				tempRoot.setRight(rNode);
			else
				tempRoot.setRight(null);
			lNode.setParent(tempRoot);
			rNode.setParent(tempRoot);
			//System.out.println("tempRoot = " + tempRoot.toString());
			q1.enqueue(tempRoot);
			
			while(myQueue.size()>1) {
				
				q1=join(myQueue);
			}
		}
		else {
			q1.enqueue(myQueue.dequeue());
		}
		return q1;
	}
	
	private static BTNode<dataNode>  findMax(BTNode<dataNode> myRoot){
		if (myRoot!=null) {
			BTNode<dataNode> parent=myRoot.getParent();
			while (myRoot!=null) {
				parent=myRoot;
				myRoot=myRoot.getRight();
			}
			return parent;
		}
		return null;
	}

	private static BTNode<dataNode>  findMin(BTNode<dataNode> myRoot){
		if (myRoot!=null) {
			BTNode<dataNode> parent=myRoot.getParent();
			while (myRoot!=null) {
				parent=myRoot;
				myRoot=myRoot.getLeft();
			}
			return parent;
		}
		return null;
	}
	
	public static int search(BTNode<dataNode> root,double target) {
		// given a target value recursively search on the left or the right subtrees
		// by comparing the value in root to the target. You know that you got to a 
		// leaf when the value count of the root is not equal to 0.
		if (root==null)
			return 0;
		else {
			System.out.println(root.toString() +"  "+ target +"  "+root.getData().count);
			double rootValue = root.getData().value;
			if(rootValue==target && root.getData().count !=0)
				return root.getData().count;
			else if (target<rootValue) 
				return search(root.getLeft(),target);
			else 
				return search(root.getRight(),target);
		}
	}
	
	public static void main(String[] args) {
		// this is given to you and should work with your methods.
		// The expected output is:
//		(13.0,1) (12.5,1) (12.3,1) (12.1,1) (11.9,1) (10.3,2) (10.0,1) (9.2,1) (9.1,1) (8.0,1) (7.2,3) (5.8,2) (2.3,1) (1.0,1) 
//		(10.15,0)  7.2   0
//		(7.6,0)  7.2   0
//		(4.05,0)  7.2   0
//		(6.5,0)  7.2   0
//		(7.2,3)  7.2   3
//		3
		double[] a = {1,2.3,5.8,5.8,7.2,7.2,7.2,8,9.1,9.2,10,10.3,10.3,11.9,12.1,12.3,12.5,13,13.2,14};
		Queue<BTNode<dataNode>> myQueue=makeQueue(a);
		myQueue.traverse();
		System.out.println();
		while (myQueue.size()>1) {
			myQueue=join(myQueue);
		}

		BTNode<dataNode> root=myQueue.dequeue();
		System.out.println(search(root,7.2));
	}
}
