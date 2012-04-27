package net.sf.xfresh.catering.util.index;

/**
 * Created by IntelliJ IDEA.
 * User: Vladislav Kononov
 * Date: 18.04.12
 * Time: 4:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class Transliterator {

    public static String transliteral(String s) {
        String tempo = "";
        for (int i = 0; i < s.length(); ++i) {
            switch (Character.toLowerCase(s.charAt(i))) {
                case 'а':
                    tempo += 'a';
                    break;
                case 'б':
                    tempo += 'b';
                    break;
                case 'в':
                    tempo += 'v';
                    break;
                case 'г':
                    tempo += 'g';
                    break;
                case 'д':
                    tempo += 'd';
                    break;
                case 'е':
                    tempo += 'e';
                    break;
                case 'ё':
                    tempo += 'e';
                    break;
                case 'ж':
                    tempo += "zh";
                    break;
                case 'з':
                    tempo += 'z';
                    break;
                case 'и':
                    tempo += 'i';
                    break;
                case 'й':
                    tempo += 'i';
                    break;
                case 'к':
                    tempo += 'k';
                    break;
                case 'л':
                    tempo += 'l';
                    break;
                case 'м':
                    tempo += 'm';
                    break;
                case 'н':
                    tempo += 'n';
                    break;
                case 'о':
                    tempo += 'o';
                    break;
                case 'п':
                    tempo += 'p';
                    break;
                case 'р':
                    tempo += 'r';
                    break;
                case 'с':
                    tempo += 's';
                    break;
                case 'т':
                    tempo += 't';
                    break;
                case 'у':
                    tempo += 'u';
                    break;
                case 'ф':
                    tempo += 'f';
                    break;
                case 'х':
                    tempo += 'h';
                    break;
                case 'ц':
                    tempo += 'c';
                    break;
                case 'ч':
                    tempo += "ch";
                    break;
                case 'ш':
                    tempo += "sh";
                    break;
                case 'щ':
                    tempo += "sh";
                    break;
                case 'ъ':
                    break;
                case 'ы':
                    tempo += 'i';
                    break;
                case 'ь':
                    break;
                case 'э':
                    tempo += 'e';
                    break;
                case 'ю':
                    tempo += 'u';
                    break;
                case 'я':
                    tempo += "ya";
                    break;
                default:
                    tempo += Character.toLowerCase(s.charAt(i));
            }
        }
        return tempo;
    }
}
