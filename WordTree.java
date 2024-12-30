import java.io.IOException;
import java.util.ArrayList;

// 移除特殊符號與處理文字
public class WordTree {

    static String preprocessText(String text) {
        // 移除 (,;:.?)\"/\n\t(包含空格)
        return text.replaceAll("[\\(\\),;:\\.\\?\\\\/\\n\\t ]", " ");
    }

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
        String text = "1 5 10 A APPLE Abs Because Bridge Builder View-Controller abstracts accept you'll";
        String cleanText[] = preprocessText(text).split(" ");
        String words[] = removeEmptyStrings(cleanText);
        for (var p : words)
            tree.insert(p);
        tree.showProfile();
        System.err.println(tree.getHeight());
    }
}