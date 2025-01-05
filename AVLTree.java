// AVL 樹的節點類

class AVLTreeNode {

    String data;
    int count;
    AVLTreeNode left, right;
    int height;

    public AVLTreeNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.count = 1;
        this.height = 1; // 樹高默認為 1
    }
}

public class AVLTree {

    private AVLTreeNode root;
    private int totalynodeCount = 0;// 總結點數
    private static int totalyWordsCount = 0;// 總字數

    // 取得節點的高度
    private int height(AVLTreeNode node) {
        return node == null ? 0 : node.height;
    }

    // 計算節點的平衡因子
    private int balanceFactor(AVLTreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // 右旋轉操作
    private AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新節點的高度
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // 返回新的根節點
        return x;
    }

    // 左旋轉操作
    private AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新節點的高度
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // 返回新的根節點
        return y;
    }

    // 插入節點並維持 AVL 樹的平衡
    public AVLTreeNode insert(AVLTreeNode node, String data) {
        // 1. 執行普通的二叉搜索樹插入
        if (node == null) {
            totalynodeCount++;// 總結點數加一
            return new AVLTreeNode(data);
        } // 使用 String 的比較方法進行插入
        else if (data.compareTo(node.data) < 0) {
            node.left = insert(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(node.right, data);
        } else {
            node.count++; // 插入重複元素時計數器加一
            return node;
        }

        // 2. 更新節點的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // 3. 計算平衡因子並進行相應的旋轉
        int balance = balanceFactor(node);

        // 左左情況：右旋
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // 右右情況：左旋
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // 左右情況：先左旋再右旋
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 右左情況：先右旋再左旋
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // 返回未改變的節點指針
    }

    // 輸出題目要求格式
    public void showProfile() {
        int i = 0;
        System.out.println("節點[數字/單字]\t\t出現次數");
        inorder(root);
    }

    // 插入元素到 AVL 樹
    public void insert(String data) {
        root = insert(root, data);
        totalyWordsCount++;
    }

    // 中序遍歷（升序輸出）
    public void inorder(AVLTreeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.printf("%-27s%d\n", node.data, node.count);// "-27"是右邊留27個space
            inorder(node.right);
        }
    }

    // 取得樹的高度
    public int getHeight() {
        return height(root);
    }

    // 取的樹的總字數
    public int getTotalWordCount() {
        return this.totalyWordsCount;
    }
	
	//取的樹的總節點數
	public int getTotalNodeCount(){
		return this.totalynodeCount;
	}
	
    // *中序取得輸出每個node的數字(備用)
    // public void inorderCount(AVLTreeNode node) {
    // if (node != null) {
    // inorderCount(node.left);
    // System.out.print(node.count + " ");
    // inorderCount(node.right);
    // }
    // }
    // *無輸入的版本，不用填root就可以輸出
    // public void inorderCount() {
    // inorderCount(root);
    // System.out.println("");
    // }

}
