/* Создай консольное приложение “Калькулятор”.
Приложение должно читать из консоли введенные пользователем строки, числа, арифметические операции проводимые между ними
и выводить в консоль результат их выполнения.
Реализуй класс Main с методом public static String calc(String input).
Метод должен принимать строку с арифметическим выражением между двумя числами и возвращать строку с результатом их выполнения.
Ты можешь добавлять свои импорты, классы и методы. Добавленные классы не должны иметь модификаторы доступа (public или другие)

1. Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.
2. Данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифметическая операция передаются с новой строки считаются неверными.
3. Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.
4. Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
5. Калькулятор умеет работать только с целыми числами.
6. Калькулятор умеет работать только с арабскими или римскими цифрами одновременно,
при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
7. При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
8. При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
9. При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
10. Результатом операции деления является целое число, остаток отбрасывается.
11. Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль.
12. Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение  */

import java.util.HashMap;
import java.util.Scanner;

public class Kata_Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);                                                                     // Создан объект класса Scanner
        System.out.println("Enter the numbers:");                                                                     // Строка Введи цифры:
        String num = scanner.nextLine();                                                                              // Создана переменная класса String, которая принимает ввод с клавиатуры
        System.out.println(calc(num));                                                                                // Вывод на экран результат метода calc
    }
    public static String calc(String input) {                                                                         // Создание метода calc, принимающий аргумент класса String
        var s = input.split(" ");                                                                                     // Создание массива класса String - разделяющий строку на элементы по пробелам
        int result = 0;                                                                                               // Создание переменной result типа int, принимающей результат
        int a = 0;                                                                                                    // Создание переменной типа int
        int b = 0;                                                                                                    // Создание переменной типа int

        if(s.length != 3)                                                                                             // Если длина массива не ровняется 3 элементам - Выбрасывается исключение и выходит из программы
            throw new RuntimeException("Operation does not satisfy the task-2 operands and 1 operator");
        boolean isSameType = Converter.isRoman(s[0]) == Converter.isRoman(s[2]);                                      // Создание флага - Одинаковые типы цифр
        if (!isSameType) throw new RuntimeException("Different type. ERROR");                                         // Если не одинаковые типы - Выбрасывается Исключение "Разные типы"

        if(Converter.isRoman(s[0])) {                                                                                 // Если поступающие числа на вход являются римскими
            a = Converter.romanToInt(s[0]);                                                                           // Конвертируем римское число под индексом 0 и помещаем в переменную типа int (a)
            b = Converter.romanToInt(s[2]);                                                                           // Конвертируем римское число под индексом 2 и помещаем в переменную типа int (b)
        } else {                                                                                                      // Если поступающие числа на вход являются арабскими
            a = Integer.parseInt(s[0]);                                                                               // Парсим арабское число под индексом 0 типа String в тип int (a)
            b = Integer.parseInt(s[2]);                                                                               // Парсим арабское число под индексом 2 типа String в тип int (b)
        }
        boolean areInRange = a >= 0 && a < 11 && b >= 0 && b < 11;                                                    // Создание флага - Цифры в диапазоне
        if (!areInRange) {                                                                                            // Если не в диапазоне
            throw new RuntimeException("Number must be greater than 0 and less than 11");                             // Выбрасывается исключение о диапазоне и выходит из программы
        }

        result = switch (s[1]) {                                                                                      // Проверка на switch-case какой из операторов будет использоваться
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new RuntimeException("Operator is not available");                                       // По дефолту выбрасываем Исключение "Оператор недоступен"
        };

        if(Converter.isRoman(s[0])){                                                                                  // Если числа на вход поступили римские - конвертируем конвертором.
            if(result <= 0){                                                                                          // Если в результате операции, римское число <= 0
                throw new RuntimeException("Roman numeral cannot be less than one");                                  // Выбрасываем исключение "Римское число не может быть меньше 1"
            } else                                                                                                    // В противном случае
                return Converter.intToRoman(result);                                                                  // Возвращаем сконвертированный результат
        } else {                                                                                                      // Если числа на вход поступили арабские
            return String.valueOf(result);                                                                            // Парсим результат из Integer в String
        }
    }


    static class Converter {                                                                                          // Создание статического класса Converter
        static HashMap<Character, Integer> romanOperands = new HashMap<>() {{                                         // Создание статического HashMap, который принимает в себя Ключ - Char и Значение - Integer
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
        }};


        static int romanToInt(String number) {                                                                       // Создание метода romanToInt, который возвращает целочисленный результат
            int result = 0;                                                                                          // Создание переменной result типа int
            for (int i = 0; i < number.length(); i++) {                                                              // Создание цикла for, который пробегается по длине Строки number
                if (i > 0 && romanOperands.get(number.charAt(i)) > romanOperands.get(number.charAt(i - 1))) {        // Если текущее число > предыдущего = производится корректировка. (XIV-14) --> (X-10)-(I-1)-(V-5)
                    result += romanOperands.get(number.charAt(i)) - 2 * romanOperands.get(number.charAt(i - 1));     // 1) (X) > 0 --> Result = (X). 2) (I) > (X) -- НЕТ. Result = (XI). 3) (V) > (I) -> Заходим в цикл -> (XI) + (V) - 2 * (I) = (XIV)
                } else {                                                                                             // Если нет
                    result += romanOperands.get(number.charAt(i));                                                   // то оно просто прибавляется
                }
            }
            return result;                                                                                           // Возвращаем целочисленный результат
        }
        static boolean isRoman(String number){                                                                       // Создание метода isRoman
            return romanOperands.containsKey(number.charAt(0));                                                      // Который возвращает true-false, если нужный элемент типа Char есть в HashMap romanOperators
        }

        static String intToRoman(int num) {                                                                          // Создание метода intToRoman, который возвращает значение типа String
            int[] values = {100,90,50,40,10,9,5,4,1};                                                                // Создание массива values типа int с цифрами
            String[] romanLetters = {"C","XC","L","XL","X","IX","V","IV","I"};                                       // Создание массива romanLetters типа String со строками (римскими цифрами)
            StringBuilder roman = new StringBuilder();                                                               // Создание объекта класса StringBuilder (изменяемый тип объекта)
            for(int i=0;i<values.length;i++)                                                                         // Создаем цикл for, который пробегается по длине массива values
            {                                                                                                        // Берем число 19 - XIX
                while(num >= values[i]) {                                                                            // Пока 19 > чисел в массиве values
                    num = num - values[i];                                                                           // 1) num = 19 - (10-X) = 9   2) num = 9.  9 - (9-IX) = 0.
                    roman.append(romanLetters[i]);                                                                   // 1) Объект roman ищет индекс со значением 10 в romanLetters и прибавляет к себе строку (X)
                }                                                                                                    // 2) Объект roman ищет индекс со значением 9 в romanLetters и прибавляет (IX). X + IX = XIX
            }
            return roman.toString();                                                                                 // Возвращаем строку в объекте класса StringBuilder в String
        }
    }
}
