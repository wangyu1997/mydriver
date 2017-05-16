package greenstudio.mydriver.util;

/**
 * Created by wangyu on 13/02/2017.
 */

public class AnswerCheckUtil {
    private static String[] answer = {"1", "2", "3", "4", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
    private static String[] key = {"A", "B", "C", "D", "AB", "AC", "AD", "BC", "BD", "CD", "ABC", "ABD", "ACD", "BCD", "ABCD"};

    public static boolean judgeAnswer(String answer_web, String answer_input) {
        int index = -1;
        for (int i = 0; i < answer.length; i++) {
            if (answer_web.equals(answer[i]))
                index = i;
        }if (index != -1) {
            if (answer_input.equals(key[index]))
                return true;
        }
        return false;
    }
}
