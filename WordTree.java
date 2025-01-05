import java.io.*;
import java.util.*;

// 移除特殊符號與處理文字
public class WordTree {

    // 預處理文字：移除特殊符號 (,;:.?)\"/\n\t(包含空格)，用空格替代
    static String preprocessText(String text) {
        return text.replaceAll("[\\(\\),;:\\.\\?\\\\/\\n\\t\\ ]", " ");
    }

    // 動態增加字串到陣列的方法
    static String[] append(String oldArr[], String newContent) {
        // 創建一個新陣列，比原陣列多一格空間
        String newArr[] = new String[oldArr.length + 1];
        // 複製舊陣列內容到新陣列
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        // 將新內容放入新陣列的最後一個位置
        newArr[oldArr.length] = newContent;
        return newArr;
    }

    // 移除空字串或全是空白的字串，返回一個新的陣列
    public static String[] removeEmptyStrings(String[] array) {
        // 初始化一個空陣列，用於存放非空字串
        String tempArr[] = new String[0];
        // 遍歷輸入的陣列
        for (String text : array) {
            if (text != null && !text.trim().isEmpty()) { // 檢查字串是否為非空或非空白
                tempArr = append(tempArr, text); // 使用 append() 將字串加入新陣列
            }
        }
        return tempArr; // 返回處理後的陣列
    }
	
    // 讀取文件並將文件內容儲存為一個字串
    public static String readFile_toString(String filePath) { // filePath 是文件位置
        StringBuilder content = new StringBuilder(); // 用於累積整篇文件內容

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            // 一行一行讀取文件，直到文件結束
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line); // 累加每行文字到 content
            }
        } catch (IOException e) { // 捕捉讀取文件時的例外
            System.out.println("Error reading file: " + e.getMessage());
        }

        // 將文章內容轉為字串並返回
        return content.toString();
    }
	
    public static void main(String[] args) throws IOException {
        AVLTree tree = new AVLTree(); // 創建一棵 AVL 樹
        // 範例文件路徑
        String filePath = "BBCNews.txt";
        // 讀取文件內容為一個字串
        String text = readFile_toString(filePath);

        // 預處理文字：移除特殊符號，將字串切割成陣列
        String cleanText[] = preprocessText(text).split(" ");
        // 移除空字串與空白內容
        String words[] = removeEmptyStrings(cleanText);

        // 將處理後的單字插入 AVL 樹
        for (var p : words) { 
            tree.insert(p);
        }

        // 輸出 AVL 樹的統計結果
        tree.showProfile(); // 以題目要求的格式輸出節點與次數
        System.out.print("總共幾個單字:" + tree.getTotalWordCount()); // 總字數
        System.out.print(" 總節點:" + tree.getTotalNodeCount()); // 總節點數
        System.out.println(" 樹高度:" + tree.getHeight()); // AVL 樹的高度
    }
}
