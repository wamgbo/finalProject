
import java.io.*;
import java.util.*;
// 移除特殊符號與處理文字
public class WordTree {

    // 移除 (,;:.?)\"/\n\t(包含空格) ，利用空格替代
    static String preprocessText(String text) {
        return text.replaceAll("[\\(\\),;:\\.\\?\\\\/\\n\\t\\  ]", " ");
    }

    // 新增內容至字串
    static String[] append(String oldArr[], String newContent) {
        String newArr[] = new String[oldArr.length + 1];// 為了新增newContent需要比oldArr多一格空間
        // 將oldArr內容交接給newArr
        for (int i = 0; i < oldArr.length; i++) {
            newArr[i] = oldArr[i];
        }
        newArr[oldArr.length] = newContent;// 新增newContent
        return newArr;
    }

    // 將preprocessText()後的內容撇除"空格","null"放入temparr再回傳
    public static String[] removeEmptyStrings(String[] array) {
        String tempArr[] = new String[0];// 初始化專門用來放
        for (String text : array) {
            if (text != null && !text.trim().isEmpty()) { // 移除空白或空字串
                tempArr = append(tempArr,text);
            }
        }
        return tempArr; // 回傳陣列
    }
	
	//讀取文件並返回儲存文件內容的String
	public static String readFile_toString(String filePath){//filePath是文件位置
        StringBuilder content = new StringBuilder(); // 用於儲存整篇文章

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line); // 累加每行
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // 將文章內容儲存為一個字串
        String fullText = content.toString();

        // 顯示整篇文章
		return fullText;
	}
	
    public static void main(String[] args) throws IOException {
        AVLTree tree = new AVLTree();
        //範例文章
		String filePath = "BBCNews.txt";
		String text = readFile_toString(filePath);
        //預處理文字
        //String text = "1 1 5 10 A APPLE Abs Because Bridge Builder View-Controller abstracts accept you'll";// 題目上的範例
        String cleanText[] = preprocessText(text).split(" ");// 將字串移除特殊自元用空個代替,再切割空白放入陣列
        String words[] = removeEmptyStrings(cleanText);// 移除控格放入陣列，(因為cleanText會有陣列位置是空or NUll,所以還需要處理一次)
		for (var p : words) {// 將陣列內容插入tree
            tree.insert(p);
        }
        tree.showProfile();// 輸出
        System.out.println("總共幾個單字:" + tree.getTotalWordCount());//總字數
		System.out.println("總節點:"+tree.getTotalNodeCount());
        System.out.println("樹高度:" + tree.getHeight());// 高度

    }
}
