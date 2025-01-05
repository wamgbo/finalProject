// AVL 樹的節點類
class AVLTreeNode {

    String data; // 節點儲存的資料 (單字或數字)
    int count;   // 該節點的出現次數，用於統計重複插入的資料
    AVLTreeNode left, right; // 左子節點與右子節點
    int height;  // 該節點的高度

    // 節點的建構函式
    public AVLTreeNode(String data) {
        this.data = data;      // 初始化節點的資料
        this.left = null;      // 初始左子節點為空
        this.right = null;     // 初始右子節點為空
        this.count = 1;        // 該節點初次插入，計數器設為 1
        this.height = 1;       // 初始節點的高度為 1 (只有自身)
    }
}

public class AVLTree {

    private AVLTreeNode root;         // AVL 樹的根節點
    private int totalynodeCount = 0;  // 樹中總節點數
    private static int totalyWordsCount = 0; // 樹中總字數 (含重複資料)

    // 取得節點的高度
    private int height(AVLTreeNode node) {
        // 如果節點為空，返回高度 0，否則返回節點的高度
        return node == null ? 0 : node.height;
    }

    // 計算節點的平衡因子 (左子樹高度 - 右子樹高度)
    private int balanceFactor(AVLTreeNode node) {
        // 如果節點為空，平衡因子為 0，否則返回左右子樹高度差
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // 右旋轉操作：用於修正左子樹高度過高 (左左情況)
    private AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = y.left; // x 是 y 的左子節點
        AVLTreeNode T2 = x.right; // T2 是 x 的右子節點

        // 執行旋轉
        x.right = y; // y 成為 x 的右子節點
        y.left = T2; // T2 成為 y 的左子節點

        // 更新節點的高度
        y.height = Math.max(height(y.left), height(y.right)) + 1; // 更新 y 的高度
        x.height = Math.max(height(x.left), height(x.right)) + 1; // 更新 x 的高度

        // 返回新的根節點 (x 成為新的根節點)
        return x;
    }

    // 左旋轉操作：用於修正右子樹高度過高 (右右情況)
    private AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = x.right; // y 是 x 的右子節點
        AVLTreeNode T2 = y.left; // T2 是 y 的左子節點

        // 執行旋轉
        y.left = x; // x 成為 y 的左子節點
        x.right = T2; // T2 成為 x 的右子節點

        // 更新節點的高度
        x.height = Math.max(height(x.left), height(x.right)) + 1; // 更新 x 的高度
        y.height = Math.max(height(y.left), height(y.right)) + 1; // 更新 y 的高度

        // 返回新的根節點 (y 成為新的根節點)
        return y;
    }

    // 插入節點並維持 AVL 樹的平衡
    public AVLTreeNode insert(AVLTreeNode node, String data) {
        // 1. 普通的二叉搜尋樹插入操作
        if (node == null) {
            totalynodeCount++; // 插入新節點時，總節點數加一
            return new AVLTreeNode(data); // 建立新的節點
        } else if (data.compareTo(node.data) < 0) {
            // 如果插入的資料比當前節點小，遞迴插入到左子樹
            node.left = insert(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            // 如果插入的資料比當前節點大，遞迴插入到右子樹
            node.right = insert(node.right, data);
        } else {
            // 如果插入的資料與當前節點相同，計數器加一 (不創建新節點)
            node.count++;
            return node;
        }

        // 2. 更新節點的高度
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // 3. 計算平衡因子，並修正不平衡的情況
        int balance = balanceFactor(node);

        // 左左情況：右旋轉
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // 右右情況：左旋轉
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

        return node; // 返回未改變的節點
    }

    // 插入元素到 AVL 樹，並更新總字數
    public void insert(String data) {
        root = insert(root, data); // 使用根節點進行插入
        totalyWordsCount++; // 每次插入 (包括重複字) 總字數加一
    }

    // 中序遍歷 (升序輸出節點)
    int i = 0; // 計數器，標示節點編號
    public void inorder(AVLTreeNode node) {
        if (node != null) {
            inorder(node.left); // 遍歷左子樹
            System.out.printf("%-4d %-22s%d\n", i + 1, node.data, node.count); // 格式化輸出節點資料與出現次數
            i++;
            inorder(node.right); // 遍歷右子樹
        }
    }

    // 顯示樹的節點與出現次數 (題目格式要求)
    public void showProfile() {
        System.out.println("節點 數字/單字 \t\t出現次數");
        inorder(root); // 使用中序遍歷輸出
    }

    // 取得樹的總高度 (根節點的高度)
    public int getHeight() {
        return height(root);
    }

    // 取得樹的總字數 (包括重複字)
    public int getTotalWordCount() {
        return this.totalyWordsCount;
    }

    // 取得樹的總節點數 (不包括重複字)
    public int getTotalNodeCount() {
        return this.totalynodeCount;
    }
}
