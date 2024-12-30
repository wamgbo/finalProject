
import java.io.IOException;
import java.util.ArrayList;

// 移除特殊符號與處理文字
public class WordTree {

    static String preprocessText(String text) {
        // 移除 (,;:.?)\"/\n\t(包含空格) ，利用空格替代
        return text.replaceAll("[\\(\\),;:\\.\\?\\\\/\\n\\t ]", " ");
    }

    //將preprocessText()後的內容撇除"空格","null"放入array再回傳
    public static String[] removeEmptyStrings(String[] array) {
        ArrayList<String> list = new ArrayList<>();
        for (String text : array) {
            if (text != null && !text.trim().isEmpty()) { // 移除空白或空字串
                list.add(text);
            }
        }
        return list.toArray(new String[0]); // 轉回陣列
    }

    public static void main(String[] args) throws IOException {
        AVLTree tree = new AVLTree();
        // 範例文章
        // Path path = Path.of("BBCNews.txt");
        // String text = Files.readString(path);
        // 預處理文字
        String text = "1 5 10 A APPLE Abs Because Bridge Builder View-Controller abstracts accept you'll";//題目上的範例
        String cleanText[] = preprocessText(text).split(" ");//將字串移除特殊自元用空個代替,再切割空白放入陣列
        String words[] = removeEmptyStrings(cleanText);//移除控格放入陣列，(因為cleanText會有陣列位置是空or NUll,所以還需要處理一次)
        for (var p : words) {//將陣列內容插入tree
            tree.insert(p);
        }
        tree.showProfile();//輸出
        System.out.println("樹高度:" + tree.getHeight());//高度
    }
}
